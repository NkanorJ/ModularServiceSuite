package com.sim.core.user.usecase;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.dto.request.DeleteSimFintechRequest;
import com.sim.commons.exception.RecordNotFoundException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.user.domain.SimUserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteSimFintechUserUseCase implements Command.Handler<DeleteSimFintechRequest, Voidy>, Command<Voidy> {

    private final SimUserStorage simUserStorage;
    private final MessageSourceService messageSourceService;

    @Override
    public Voidy handle(DeleteSimFintechRequest deleteSimFintechRequest) {

        SimFintechUserDto user = simUserStorage.findUserByPublicId(deleteSimFintechRequest.publicId())
                .orElseThrow(() -> new RecordNotFoundException(messageSourceService.getMessage("record.not.found"), false));

        simUserStorage.deleteSimUser(user.publicId());

        return new Voidy();
    }
}
