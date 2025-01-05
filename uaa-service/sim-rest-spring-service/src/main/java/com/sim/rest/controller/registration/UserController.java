package com.sim.rest.controller.registration;

import an.awesome.pipelinr.Pipeline;
import com.sim.commons.dto.request.CreateSimFintechRequest;
import com.sim.commons.dto.request.DeleteSimFintechRequest;
import com.sim.commons.dto.request.GetSimFintechUserRequest;
import com.sim.commons.dto.request.LoginSimFintechUserRequest;
import com.sim.commons.dto.request.UpdateSimFintechRequest;
import com.sim.commons.dto.response.GetSimFintechUsersResponse;
import com.sim.commons.dto.response.LoginSimFintechResponse;
import com.sim.commons.dto.registrationdto.SecretKey;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.sim.rest.apis.UserAPI.BASE_URL;
import static com.sim.rest.apis.UserAPI.CREATE;
import static com.sim.rest.apis.UserAPI.DELETE_USER;
import static com.sim.rest.apis.UserAPI.GET_USER;
import static com.sim.rest.apis.UserAPI.LOGIN;
import static com.sim.rest.apis.UserAPI.UPDATE_USER;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_URL)
@Tag(name = "Registration", description = "User Registration")
public class UserController {

    private final Pipeline pipeline;

    @PostMapping(CREATE)
    public SecretKey register(@RequestBody CreateSimFintechRequest createSimFintechRequest) {
        return pipeline.send(createSimFintechRequest);
    }

    @PostMapping(LOGIN)
    public LoginSimFintechResponse login(@RequestBody LoginSimFintechUserRequest loginSimFintechUserRequest) {
        return pipeline.send(loginSimFintechUserRequest);
    }

    @GetMapping(GET_USER)
    public GetSimFintechUsersResponse getUser(@PathVariable UUID publicId) {
        return pipeline.send(new GetSimFintechUserRequest(publicId));
    }

    @PutMapping(UPDATE_USER)
    public void updateUser(@RequestBody UpdateSimFintechRequest updateRequest) {
        pipeline.send(updateRequest);

    }

    @DeleteMapping(DELETE_USER)
    public void deleteUser(@PathVariable UUID publicId) {
        pipeline.send(new DeleteSimFintechRequest(publicId));
    }

}
