import an.awesome.pipelinr.Voidy;
import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.dto.request.DeleteSimFintechRequest;
import com.sim.commons.enumeration.Gender;
import com.sim.commons.enumeration.LoanStatus;
import com.sim.commons.enumeration.Role;
import com.sim.commons.exception.RecordNotFoundException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.user.domain.SimUserStorage;
import com.sim.core.user.usecase.DeleteSimFintechUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DeleteSimFintechUserUseCase.class)
class DeleteSimFintechUserUseCaseTest {

    @MockBean
    private SimUserStorage simUserStorage;

    @MockBean
    private MessageSourceService messageSourceService;

    @InjectMocks
    private DeleteSimFintechUserUseCase deleteSimFintechUserUseCase;

    @BeforeEach
    void setup() {
        this.deleteSimFintechUserUseCase = new DeleteSimFintechUserUseCase(simUserStorage, messageSourceService);
    }

    @Test
    void handle_deleteSimFintechUser_success() {

        UUID userId = UUID.randomUUID();

        SimFintechUserDto existingUser = new SimFintechUserDto(userId, "Jane", "Doe", Gender.F, null,
                "jane.doe@example.com", "07060482184", "password", UUID.randomUUID(), Role.USER,
                LocalDate.of(1990, 1, 1), LoanStatus.ACTIVE);

        when(simUserStorage.findUserByPublicId(userId)).thenReturn(Optional.of(existingUser));

        doNothing().when(simUserStorage).deleteSimUser(userId);

        DeleteSimFintechRequest request = new DeleteSimFintechRequest(userId);

        Voidy result = deleteSimFintechUserUseCase.handle(request);

        assertNotNull(result);

        verify(simUserStorage, times(1)).findUserByPublicId(userId);
    }

    @Test
    void handle_deleteSimFintechUser_userNotFound() {
        UUID userId = UUID.randomUUID();

        when(simUserStorage.findUserByPublicId(userId)).thenReturn(Optional.empty());

        DeleteSimFintechRequest request = new DeleteSimFintechRequest(userId);

        assertThrows(RecordNotFoundException.class, () -> {
            deleteSimFintechUserUseCase.handle(request);
        });

        verify(simUserStorage, times(1)).findUserByPublicId(userId);
        verify(simUserStorage, times(0)).deleteSimUser(userId);
    }
}