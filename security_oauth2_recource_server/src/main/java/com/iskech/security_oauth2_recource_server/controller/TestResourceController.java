package com.iskech.security_oauth2_recource_server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tests")
public class TestResourceController {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String test() {
        return "you have granted admin role ,hello oauth2 resource server";
    }
}
