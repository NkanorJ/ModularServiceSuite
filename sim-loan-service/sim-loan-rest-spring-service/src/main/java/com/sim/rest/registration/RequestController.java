package com.sim.rest.registration;

import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Voidy;
import com.sim.commons.dto.request.PendingRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sim.rest.apis.LoanAPI.BASE_URL;
import static com.sim.rest.apis.LoanAPI.HANDLE_REQUEST;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_URL)
@Tag(name = "Request", description = "Request Management")
public class RequestController {

    private final Pipeline pipeline;

    @PostMapping(HANDLE_REQUEST)
    public Voidy createLogin(@RequestBody PendingRequest pendingRequest) {

        return pipeline.send(pendingRequest);

    }

}
