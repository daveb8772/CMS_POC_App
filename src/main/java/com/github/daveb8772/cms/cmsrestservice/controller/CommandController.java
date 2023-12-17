package com.github.daveb8772.cms.cmsrestservice.controller;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.CommandRequest;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.CommandResponse;
import com.github.daveb8772.cms.cmsrestservice.service.CMSEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ocpi/commands")
public class CommandController {

    private final CMSEndpointService CMSEndpointService;

    @Autowired
    public CommandController(CMSEndpointService CMSEndpointService) {
        this.CMSEndpointService = CMSEndpointService;
    }

    @PostMapping("/{command}")
    public Mono<ResponseEntity<CommandResponse>> handleCommand(
            @PathVariable String command,
            @RequestBody CommandRequest request) {
        return CMSEndpointService.handleCommand(command, request)
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


