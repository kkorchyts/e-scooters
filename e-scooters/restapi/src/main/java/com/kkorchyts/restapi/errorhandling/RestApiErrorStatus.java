package com.kkorchyts.restapi.errorhandling;

import org.springframework.http.HttpStatus;

public class RestApiErrorStatus {
    private HttpStatus httpStatus;
    private String errorMessage;
    private String errorMessageDump;

    public RestApiErrorStatus() {
    }

    public RestApiErrorStatus(HttpStatus httpStatus, String errorMessage, String errorMessageDump) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.errorMessageDump = errorMessageDump;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessageDump() {
        return errorMessageDump;
    }

    public void setErrorMessageDump(String errorMessageDump) {
        this.errorMessageDump = errorMessageDump;
    }
}
