package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.StatusAwareResponse;
import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class DataAccessResponseHandler<T extends StatusAwareResponse> {

    private final Mono<T> response;

    public DataAccessResponseHandler(Mono<T> response) {
        this.response = response;
    }

    public Mono<ResponseEntity<T>> handleResponse(HttpStatus httpStatus) {
        Status status = Status.createStatus(httpStatus);

        // Switch statement to handle different status codes
        return switch (httpStatus.value()) {
            case 200 -> // OK
                    response.map(dataAccess -> {
                        dataAccess.setStatus(status);
                        return new ResponseEntity<>(dataAccess, httpStatus);
                    });            case 201 -> // CREATED
                    Mono.just(new ResponseEntity<>(HttpStatus.CREATED));
            case 202 -> // ACCEPTED
                    Mono.just(new ResponseEntity<>(HttpStatus.ACCEPTED));
            case 204 -> // NO_CONTENT
                    Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT));
            case 400 -> // BAD_REQUEST
                    Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
            case 401 -> // UNAUTHORIZED
                    Mono.just(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
            case 403 -> // FORBIDDEN
                    Mono.just(new ResponseEntity<>(HttpStatus.FORBIDDEN));
            case 404 -> // NOT_FOUND
                    Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            case 409 -> // CONFLICT
                    Mono.just(new ResponseEntity<>(HttpStatus.CONFLICT));
            case 422 -> // UNPROCESSABLE_ENTITY
                    Mono.just(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
            case 500 -> // INTERNAL_SERVER_ERROR
                    Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
            default -> Mono.error(new RuntimeException("Unexpected HTTP status code: " + httpStatus.value()));
        };
    }




}



