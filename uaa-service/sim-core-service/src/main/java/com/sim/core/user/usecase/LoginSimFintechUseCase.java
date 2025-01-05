package com.sim.core.user.usecase;

import an.awesome.pipelinr.Command;
import com.sim.auth.service.JWTService;
import com.sim.commons.dto.request.LoginSimFintechUserRequest;
import com.sim.commons.dto.response.LoginSimFintechResponse;
import com.sim.commons.exception.RateLimitException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.ratelimit.RateLimitingService;
import io.github.bucket4j.Bucket;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginSimFintechUseCase implements Command.Handler<LoginSimFintechUserRequest, LoginSimFintechResponse>, Command<LoginSimFintechResponse> {

    private final RateLimitingService rateLimitingService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final MessageSourceService messageSourceService;


    @Override
    public LoginSimFintechResponse handle(LoginSimFintechUserRequest loginSimFintechUserRequest) {
        Bucket bucket = rateLimitingService.resolveBucket(loginSimFintechUserRequest.email());

        if (!bucket.tryConsume(1))
            throw new RateLimitException(messageSourceService.getMessage("rate.trial.exceeded"), false);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginSimFintechUserRequest.email(), loginSimFintechUserRequest.password())
            );
            String token = jwtService.generateToken(authentication.getName());

            return new LoginSimFintechResponse(token, "Login successful");
        } catch (BadCredentialsException e) {
            return new LoginSimFintechResponse(null, messageSourceService.getMessage("invalid.credentials.provided"));
        }
    }

}
