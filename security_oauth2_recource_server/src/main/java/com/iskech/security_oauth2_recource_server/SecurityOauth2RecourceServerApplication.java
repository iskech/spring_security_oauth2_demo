package com.iskech.security_oauth2_recource_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class SecurityOauth2RecourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityOauth2RecourceServerApplication.class, args);
    }

}
