package com.sim.rest.apis;

public interface LoanAPI {

    String BASE_URL = "/api/sim-financial-service/v1";

    String CREATE ="/loan-creation";
    String GET_LOAN ="/get-loan/{publicId}";
    String UPDATE_LOAN_STATUS ="/update-loan-status";

    String HANDLE_REQUEST="/handle-request";
    String GET_LOGIN ="/{publicId}";


}
