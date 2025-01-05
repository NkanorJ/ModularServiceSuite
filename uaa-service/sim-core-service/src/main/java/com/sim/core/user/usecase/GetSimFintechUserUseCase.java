package com.sim.core.user.usecase;

import an.awesome.pipelinr.Command;
import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.dto.request.GetSimFintechUserRequest;
import com.sim.commons.dto.response.GetSimFintechUsersResponse;
import com.sim.commons.exception.RecordNotFoundException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.user.domain.SimUserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetSimFintechUserUseCase implements Command.Handler<GetSimFintechUserRequest, GetSimFintechUsersResponse>, Command<GetSimFintechUsersResponse> {

    private final SimUserStorage simUserStorage;
    private final MessageSourceService messageSourceService;


    @Override
    public GetSimFintechUsersResponse handle(GetSimFintechUserRequest getSimFintechUserRequest) {

        SimFintechUserDto user = simUserStorage.findUserByPublicId(getSimFintechUserRequest.publicId())
                .orElseThrow(() -> new RecordNotFoundException(messageSourceService.getMessage("record.not.found"), false));

        return new GetSimFintechUsersResponse(user.firstName(), user.lastName(), String.valueOf(user.gender()), user.email(),
                user.mobile(), user.publicId(), String.valueOf(user.role()), user.dateOfBirth(), String.valueOf(user.loanStatus()));
    }
}
