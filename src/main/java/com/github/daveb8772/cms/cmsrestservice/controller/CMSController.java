package com.github.daveb8772.cms.cmsrestservice.controller;

import com.github.daveb8772.cms.cmsrestservice.service.CMSEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// In CMSController

@RestController
@RequestMapping("/cms")
public class CMSController {

    @Autowired
    private CMSEndpointService CMSEndpointService;

}

