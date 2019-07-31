package com.iskech.security_oauth2_recource_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tests")
public class TestResourceController {
    @GetMapping
    public String test() {
        return "hello oauth2 resource server";
    }
}
