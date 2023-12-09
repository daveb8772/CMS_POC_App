package com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels;

import org.springframework.http.HttpStatus;

public class Status {

    public Status(int statusCode, String description) {
    }

    public static Status createStatus(HttpStatus httpStatus) {
        if (httpStatus == HttpStatus.OK) {
            return new Status(httpStatus.value(), "Success");
        } else if (httpStatus == HttpStatus.BAD_REQUEST) {
            return new Status(httpStatus.value(), "Invalid request");
        } else if (httpStatus == HttpStatus.NOT_FOUND) {
            return new Status(httpStatus.value(), "Charging point not found");
        } else {
            return new Status(httpStatus.value(), "Unexpected error");
        }
    }

}