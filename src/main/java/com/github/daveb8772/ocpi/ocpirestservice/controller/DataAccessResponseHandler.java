package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ResponseModels.ResponseStatus;
import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ResponseModels.StatusAwareResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DataAccessResponseHandler<T extends StatusAwareResponse> {

    public Mono<ResponseEntity<T>> handleResponse(Mono<T> response, HttpStatus httpStatus) {
        ResponseStatus responseStatus = ResponseStatus.createStatus(httpStatus);

        return response.flatMap(dataAccess -> {
            dataAccess.setStatus(responseStatus);
            return Mono.just(new ResponseEntity<>(dataAccess, httpStatus));
        }).switchIfEmpty(Mono.defer(() -> getEmptyResponse(httpStatus)));
    }

    private Mono<ResponseEntity<T>> getEmptyResponse(HttpStatus httpStatus) {
        return switch (httpStatus.value()) {
            case 200, 201, 202, 204, 400, 401, 403, 404, 409, 422, 500 ->
                    Mono.just(new ResponseEntity<>(httpStatus));
            default -> Mono.error(new RuntimeException("Unexpected HTTP status code: " + httpStatus.value()));
        };
    }

}



