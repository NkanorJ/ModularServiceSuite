package com.sim.core.user.usecase;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.dto.request.UpdateSimFintechRequest;
import com.sim.commons.enumeration.Gender;
import com.sim.commons.enumeration.Role;
import com.sim.commons.exception.RecordNotFoundException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.user.domain.SimUserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateSimFintechUserUseCase implements Command.Handler<UpdateSimFintechRequest, Voidy>, Command<Voidy> {

    private final SimUserStorage simUserStorage;
    private final MessageSourceService messageSourceService;

    @Override
    public Voidy handle(UpdateSimFintechRequest updateSimFintechRequest) {

        SimFintechUserDto user = simUserStorage.findUserByPublicId(updateSimFintechRequest.publicId())
                .orElseThrow(() -> new RecordNotFoundException(messageSourceService.getMessage("record.not.found"), false));

        simUserStorage.updateSimUser(new SimFintechUserDto(user.publicId(), updateSimFintechRequest.firstName(), updateSimFintechRequest.lastName(),
                Gender.valueOf(updateSimFintechRequest.gender()), user.password(), user.email(), updateSimFintechRequest.phoneNumber(),
                user.secretKey(), user.publicId(), Role.valueOf(updateSimFintechRequest.role()), updateSimFintechRequest.dateOfBirth(), user.loanStatus()));

        return new Voidy();
    }
}
