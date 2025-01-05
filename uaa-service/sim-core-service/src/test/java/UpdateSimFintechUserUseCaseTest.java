import an.awesome.pipelinr.Voidy;
import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.dto.request.UpdateSimFintechRequest;
import com.sim.commons.enumeration.Gender;
import com.sim.commons.enumeration.LoanStatus;
import com.sim.commons.enumeration.Role;
import com.sim.commons.exception.RecordNotFoundException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.user.domain.SimUserStorage;
import com.sim.core.user.usecase.UpdateSimFintechUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UpdateSimFintechUserUseCase.class)
class UpdateSimFintechUserUseCaseTest {

    @MockBean
    private SimUserStorage simUserStorage;

    @MockBean
    private MessageSourceService messageSourceService;

    @InjectMocks
    private UpdateSimFintechUserUseCase updateSimFintechUserUseCase;

    @BeforeEach
    void setup() {
        this.updateSimFintechUserUseCase = new UpdateSimFintechUserUseCase(simUserStorage, messageSourceService);
    }

    @Test
    void handle_updateSimFintechUser_success() {
        UUID userId = UUID.randomUUID();
        String firstName = "UpdatedFirstName";
        String lastName = "UpdatedLastName";
        String gender = "M"; 
        String phoneNumber = "07060482190";
        String role = "USER";
        LocalDate dateOfBirth = LocalDate.of(1992, 5, 10);

        SimFintechUserDto existingUser = new SimFintechUserDto(userId, "John", "Doe", Gender.M,
                "oldPassword", "john.doe@example.com", "07060482111", "", UUID.randomUUID(),
                Role.USER, LocalDate.of(1990, 1, 1), LoanStatus.ACTIVE);

        when(simUserStorage.findUserByPublicId(userId)).thenReturn(Optional.of(existingUser));

        doNothing().when(simUserStorage).updateSimUser(any(SimFintechUserDto.class));

        UpdateSimFintechRequest request = new UpdateSimFintechRequest( firstName, lastName, gender, phoneNumber,dateOfBirth,
                userId, role);

        Voidy result = updateSimFintechUserUseCase.handle(request);

        assertNotNull(result);

        verify(simUserStorage, times(1)).findUserByPublicId(userId);
        verify(simUserStorage, times(1)).updateSimUser(any(SimFintechUserDto.class));

        ArgumentCaptor<SimFintechUserDto> captor = ArgumentCaptor.forClass(SimFintechUserDto.class);
        verify(simUserStorage).updateSimUser(captor.capture());
        
        SimFintechUserDto updatedUser = captor.getValue();

        assertEquals(firstName, updatedUser.firstName());
        assertEquals(lastName, updatedUser.lastName());
        assertEquals(Gender.M, updatedUser.gender());
        assertEquals(phoneNumber, updatedUser.mobile());
        assertEquals(Role.USER, updatedUser.role());
        assertEquals(dateOfBirth, updatedUser.dateOfBirth());
    }

    @Test
    void handle_updateSimFintechUser_userNotFound() {
        UUID userId = UUID.randomUUID();

        when(simUserStorage.findUserByPublicId(userId)).thenReturn(Optional.empty());

        UpdateSimFintechRequest request = new UpdateSimFintechRequest("UpdatedFirstName",
                "UpdatedLastName", "M", "07060482190",
                LocalDate.of(1992, 5, 10), userId, Role.USER.name());

        assertThrows(RecordNotFoundException.class, () -> {
            updateSimFintechUserUseCase.handle(request);
        });

        verify(simUserStorage, times(1)).findUserByPublicId(userId);

        verify(simUserStorage, times(0)).updateSimUser(any(SimFintechUserDto.class));
    }
}
