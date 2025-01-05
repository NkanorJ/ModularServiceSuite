package com.sim.rest.apis;

public interface UserAPI {

    String BASE_URL = "/api/sim-financial-service/v1";
    String INTERNAL_BASE_URL = "/internal";

    String CREATE ="/reg";
    String LOGIN ="/login";
    String GET_USER ="/getUser/{publicId}";
    String UPDATE_USER ="/update";
    String DELETE_USER ="delete/{publicId}";


}
