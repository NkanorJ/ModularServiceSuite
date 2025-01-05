import com.sim.auth.service.JWTService;
import com.sim.commons.dto.request.LoginSimFintechUserRequest;
import com.sim.commons.dto.response.LoginSimFintechResponse;
import com.sim.commons.exception.RateLimitException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.ratelimit.RateLimitingService;
import com.sim.core.user.usecase.LoginSimFintechUseCase;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = LoginSimFintechUseCase.class)
class LoginSimFintechUseCaseTest {

    @MockBean
    private RateLimitingService rateLimitingService;

    @MockBean
    private MessageSourceService messageSourceService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JWTService jwtService;

    @InjectMocks
    private LoginSimFintechUseCase loginSimFintechUseCase;

    @BeforeEach
    void setup() {
        this.loginSimFintechUseCase = new LoginSimFintechUseCase(rateLimitingService, authenticationManager, jwtService,
                messageSourceService);
    }

    @Test
    void handle_loginSimFintechUser_invalidCredentials() {

        String email = "jane.doe@example.com";
        String password = "wrongPassword";

        Bucket bucket = mock(Bucket.class);
        when(rateLimitingService.resolveBucket(email)).thenReturn(bucket);
        when(bucket.tryConsume(1)).thenReturn(true);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        LoginSimFintechUserRequest request = new LoginSimFintechUserRequest(email, password);

        LoginSimFintechResponse response = loginSimFintechUseCase.handle(request);

        assertNotNull(response);
        assertNull(response.token());
        assertEquals(null, response.message());

        verify(rateLimitingService, times(1)).resolveBucket(email);
        verify(bucket, times(1)).tryConsume(1);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void handle_loginSimFintechUser_rateLimitExceeded() {
        String email = "jane.doe@example.com";
        String password = "correctPassword";

        Bucket bucket = mock(Bucket.class);
        when(rateLimitingService.resolveBucket(email)).thenReturn(bucket);
        when(bucket.tryConsume(1)).thenReturn(false);

        LoginSimFintechUserRequest request = new LoginSimFintechUserRequest(email, password);

        assertThrows(RateLimitException.class, () -> {
            loginSimFintechUseCase.handle(request);
        });

        verify(rateLimitingService, times(1)).resolveBucket(email);
        verify(bucket, times(1)).tryConsume(1);
        verify(authenticationManager, times(0)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}