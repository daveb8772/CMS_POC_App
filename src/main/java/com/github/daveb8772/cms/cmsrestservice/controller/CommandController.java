package com.github.daveb8772.cms.cmsrestservice.controller;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.CommandRequest;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.CommandResponse;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.TariffDataResponse;
import com.github.daveb8772.cms.cmsrestservice.service.CMSEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/cms/commands")
public class CommandController {

    private final CMSEndpointService CMSEndpointService;

    @Autowired
    public CommandController(CMSEndpointService CMSEndpointService) {
        this.CMSEndpointService = CMSEndpointService;
    }

    @GetMapping("/getCommands")
    public Mono<ResponseEntity<List<CommandResponse>>> getCommands() {
        return CMSEndpointService.getCommands()
                .flatMap(commands -> commands.isEmpty()
                        ? Mono.just(ResponseEntity.notFound().build())
                        : Mono.just(ResponseEntity.ok(commands)));
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


