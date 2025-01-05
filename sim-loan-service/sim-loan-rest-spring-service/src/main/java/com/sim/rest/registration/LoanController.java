package com.sim.rest.registration;

import an.awesome.pipelinr.Pipeline;
import com.sim.commons.dto.request.CreateLoanRequest;
import com.sim.commons.dto.request.GetLoanRequest;
import com.sim.commons.dto.request.UpdateLoanRequest;
import com.sim.commons.dto.response.CreateLoanResponse;
import com.sim.commons.dto.response.GetLoanResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.sim.rest.apis.LoanAPI.BASE_URL;
import static com.sim.rest.apis.LoanAPI.CREATE;
import static com.sim.rest.apis.LoanAPI.GET_LOAN;
import static com.sim.rest.apis.LoanAPI.UPDATE_LOAN_STATUS;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_URL)
@Tag(name = "Loan", description = "Loan Management")
public class LoanController {

    private final Pipeline pipeline;

    @PostMapping(CREATE)
    public CreateLoanResponse createLogin(@RequestBody CreateLoanRequest createLoanRequest) {
        return pipeline.send(createLoanRequest);
    }

    @GetMapping(GET_LOAN)
    public GetLoanResponse getLoan(@PathVariable UUID publicId) {
        return pipeline.send(new GetLoanRequest(publicId));
    }

    @PutMapping(UPDATE_LOAN_STATUS)
    public void getLoan(@RequestBody UpdateLoanRequest request) {
        pipeline.send(request);
    }

}
