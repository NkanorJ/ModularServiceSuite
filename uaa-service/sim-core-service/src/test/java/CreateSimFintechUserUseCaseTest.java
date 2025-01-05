import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.dto.request.CreateSimFintechRequest;
import com.sim.commons.dto.registrationdto.SecretKey;
import com.sim.commons.enumeration.Gender;
import com.sim.commons.enumeration.LoanStatus;
import com.sim.commons.enumeration.Role;
import com.sim.commons.exception.PhoneNumberException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.ratelimit.RateLimitingService;
import com.sim.core.user.domain.SimUserStorage;
import com.sim.core.user.usecase.CreateSimFintechUserUseCase;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CreateSimFintechUserUseCaseTest.class)
class CreateSimFintechUserUseCaseTest {

    @MockBean
    private RateLimitingService rateLimitingService;

    @MockBean
    private SimUserStorage simUserStorage;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private MessageSourceService messageSourceService;

    @InjectMocks
    private CreateSimFintechUserUseCase createSimFintechUserUseCase;


    @BeforeEach
    void setup() {
        this.createSimFintechUserUseCase = new CreateSimFintechUserUseCase(rateLimitingService, simUserStorage,
                bCryptPasswordEncoder, messageSourceService);
    }

    @Test
    void handle_createSimFintechUser_success() {
        UUID expectedPublicId = UUID.randomUUID();
        SecretKey expectedSecretKey = new SecretKey("janeetim248@gmail.com", "secretKey",
                "Client Secret", expectedPublicId);

        Bucket mockBucket = mock(Bucket.class);
        when(rateLimitingService.resolveIPAddress(any())).thenReturn(mockBucket);
        when(mockBucket.tryConsume(1)).thenReturn(true);
        when(simUserStorage.createSimUser(any())).thenReturn(expectedSecretKey);

        CreateSimFintechRequest request = new CreateSimFintechRequest("Jane", "janeetim248@gmail.com",
                "Jane", "password", "F",
                "07060482184", "USER", LocalDate.of(1990, 1, 1));

        SecretKey result = createSimFintechUserUseCase.handle(request);

        assertEquals(expectedSecretKey.email(), result.email());
        assertEquals(expectedSecretKey.secretName(), result.secretName());
        assertEquals(expectedSecretKey.publicId(), result.publicId());

        verify(simUserStorage, times(1)).createSimUser(any());
        verify(rateLimitingService, times(1)).resolveIPAddress(any());
        verify(mockBucket, times(1)).tryConsume(1);
    }



    @Test
    void handle_createSimFintechUser_alreadyExists_phoneNumber() {
        when(simUserStorage.findByPhoneNumber("07060482184"))
                .thenReturn(Optional.of(new SimFintechUserDto(UUID.randomUUID(), "Jane", "etim", Gender.valueOf("F"),
                        null, "janeeim248@gmail.com", "07060482184", "testing", UUID.randomUUID(),
                        Role.USER, LocalDate.of(1990, 1, 1), LoanStatus.ACTIVE)));

        assertThrows(PhoneNumberException.class, () -> {
            createSimFintechUserUseCase.handle(new CreateSimFintechRequest("Jane", "Doe", "jane.etim248@gmail.com",
                    "password", "07060482184", "F", "USER", LocalDate.of(1990, 1, 1)));
        });
    }
}
