import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.dto.request.GetSimFintechUserRequest;
import com.sim.commons.dto.response.GetSimFintechUsersResponse;
import com.sim.commons.enumeration.Gender;
import com.sim.commons.enumeration.LoanStatus;
import com.sim.commons.enumeration.Role;
import com.sim.commons.exception.RecordNotFoundException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.user.domain.SimUserStorage;
import com.sim.core.user.usecase.GetSimFintechUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GetSimFintechUserUseCase.class)
class GetSimFintechUserUseCaseTest {

    @MockBean
    private SimUserStorage simUserStorage;

    @MockBean
    private MessageSourceService messageSourceService;

    @InjectMocks
    private GetSimFintechUserUseCase getSimFintechUserUseCase;

    @BeforeEach
    void setup() {
        this.getSimFintechUserUseCase = new GetSimFintechUserUseCase(simUserStorage, messageSourceService);
    }

    @Test
    void handle_getSimFintechUser_success() {
        UUID userId = UUID.randomUUID();
        SimFintechUserDto existingUser = new SimFintechUserDto(userId, "Jane", "Doe", Gender.F, null,
                "jane.doe@example.com", "07060482184", "password", UUID.randomUUID(), Role.USER,
                LocalDate.of(1990, 1, 1), LoanStatus.ACTIVE);

        when(simUserStorage.findUserByPublicId(userId)).thenReturn(Optional.of(existingUser));

        GetSimFintechUserRequest request = new GetSimFintechUserRequest(userId);
        GetSimFintechUsersResponse response = getSimFintechUserUseCase.handle(request);

        assertNotNull(response);
        assertEquals(existingUser.firstName(), response.firstName());
        assertEquals(existingUser.lastName(), response.lastName());
        assertEquals(String.valueOf(existingUser.gender()), response.gender());
        assertEquals(existingUser.email(), response.email());
        assertEquals(existingUser.mobile(), response.mobile());
        assertEquals(existingUser.publicId(), response.publicId());
        assertEquals(String.valueOf(existingUser.role()), response.role());
        assertEquals(existingUser.dateOfBirth(), response.dateOfBrith());

        verify(simUserStorage, times(1)).findUserByPublicId(userId);
    }

    @Test
    void handle_getSimFintechUser_userNotFound() {
        UUID userId = UUID.randomUUID();

        when(simUserStorage.findUserByPublicId(userId)).thenReturn(Optional.empty());

        GetSimFintechUserRequest request = new GetSimFintechUserRequest(userId);

        assertThrows(RecordNotFoundException.class, () -> {
            getSimFintechUserUseCase.handle(request);
        });

        verify(simUserStorage, times(1)).findUserByPublicId(userId);
    }
}

