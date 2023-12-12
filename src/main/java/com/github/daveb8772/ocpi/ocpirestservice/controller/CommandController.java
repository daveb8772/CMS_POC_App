package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels.CommandRequest;
import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.CommandResponse;
import com.github.daveb8772.ocpi.ocpirestservice.service.OCPIEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ocpi/commands")
public class CommandController {

    private final OCPIEndpointService ocpiEndpointService;

    @Autowired
    public CommandController(OCPIEndpointService ocpiEndpointService) {
        this.ocpiEndpointService = ocpiEndpointService;
    }

    @PostMapping("/{command}")
    public Mono<ResponseEntity<CommandResponse>> handleCommand(
            @PathVariable String command,
            @RequestBody CommandRequest request) {
        return ocpiEndpointService.handleCommand(command, request)
                .flatMap(commandResponse -> {
                    if (commandResponse == null) {
                        return Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT));
                    } else {
                        DataAccessResponseHandler<CommandResponse> responseHandler = new DataAccessResponseHandler<>();
                        return responseHandler.handleResponse(Mono.just(commandResponse), HttpStatus.OK);
                    }
                });
    }
}


