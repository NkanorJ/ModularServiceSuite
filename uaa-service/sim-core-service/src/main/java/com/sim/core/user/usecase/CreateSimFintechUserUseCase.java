package com.sim.core.user.usecase;

import an.awesome.pipelinr.Command;
import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.dto.request.CreateSimFintechRequest;
import com.sim.commons.dto.registrationdto.SecretKey;
import com.sim.commons.enumeration.Gender;
import com.sim.commons.enumeration.Role;
import com.sim.commons.exception.PhoneNumberException;
import com.sim.commons.exception.RateLimitException;
import com.sim.commons.exception.UserRegistrationException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.ratelimit.RateLimitingService;
import com.sim.core.user.domain.SimUserStorage;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.UUID;

import static com.sim.commons.user.validation.PhoneNumberValidator.convertToInternationalFormat;

@Component
@AllArgsConstructor
public class CreateSimFintechUserUseCase implements Command.Handler<CreateSimFintechRequest, SecretKey>, Command<SecretKey> {

    private final RateLimitingService rateLimitingService;
    private final SimUserStorage simUserStorage;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MessageSourceService messageSourceService;


    @Override
    public SecretKey handle(CreateSimFintechRequest createSimFintechRequest) {

        String formattedMobile = convertToInternationalFormat(createSimFintechRequest.phoneNumber());

        if (simUserStorage.findByPhoneNumber(createSimFintechRequest.phoneNumber()).isPresent())
            throw new PhoneNumberException(messageSourceService.getMessage(
                    "a.user.already.exists.with.that.phone.number.please.login"), false);

        if (simUserStorage.findByEmail(createSimFintechRequest.email()).isPresent())
            throw new UserRegistrationException(String.format(
                    messageSourceService.getMessage("a.user.already.exists.with.that.email.address.please.login"),
                    createSimFintechRequest.email()), false);

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Bucket bucket = rateLimitingService.resolveIPAddress(httpServletRequest.getRemoteAddr());
        if (!bucket.tryConsume(1)) {
            throw new RateLimitException(messageSourceService.getMessage("rate.trial.exceeded"), false);
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(createSimFintechRequest.password());

        String[] secretKey = getUserSecretKey(createSimFintechRequest.email());
        SecretKey response = simUserStorage.createSimUser(new SimFintechUserDto(UUID.randomUUID(), createSimFintechRequest.firstName(),
                createSimFintechRequest.lastName(), Gender.valueOf(createSimFintechRequest.gender()), encryptedPassword,
                createSimFintechRequest.email(), formattedMobile, secretKey[1], UUID.randomUUID(),
                Role.valueOf(createSimFintechRequest.role()), createSimFintechRequest.dateOfBrith(), null));

        return new SecretKey(createSimFintechRequest.email(), secretKey[0], "Client Secret", response.publicId());

    }

    private String[] getUserSecretKey(String email) {
        String[] secretKey = new String[2];
        secretKey[0] = DigestUtils.sha512Hex(String.format("%s-%s-%s", RandomStringUtils.randomAlphabetic(6), System.nanoTime(), email));
        secretKey[0] = StringUtils.substring(secretKey[0], 0, 60);
        secretKey[1] = bCryptPasswordEncoder.encode(secretKey[0]);
        return secretKey;
    }


}
