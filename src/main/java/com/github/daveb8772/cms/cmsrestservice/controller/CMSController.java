package com.github.daveb8772.cms.cmsrestservice.controller;

import com.github.daveb8772.cms.cmsrestservice.service.CMSEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// In CMSController

@RestController
@RequestMapping("/cms")
public class CMSController {

    @Autowired
    private CMSEndpointService CMSEndpointService;

    @GetMapping("/test")
    public String testEndpoint() {
        return "Service is running!";
    }

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @GetMapping("/listEndpoints")
    public List<String> listAllEndpoints() {
        return requestMappingHandlerMapping.getHandlerMethods().keySet().stream()
                .flatMap(mappingInfo -> getPatternsFromMappingInfo(mappingInfo).stream())
                .collect(Collectors.toList());
    }

    private Set<String> getPatternsFromMappingInfo(RequestMappingInfo mappingInfo) {
        if (mappingInfo.getPatternsCondition() != null) {
            return mappingInfo.getPatternsCondition().getPatterns();
        } else if (mappingInfo.getPathPatternsCondition() != null) {
            return mappingInfo.getPathPatternsCondition().getPatterns().stream()
                    .map(pathPattern -> pathPattern.getPatternString())
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

}

