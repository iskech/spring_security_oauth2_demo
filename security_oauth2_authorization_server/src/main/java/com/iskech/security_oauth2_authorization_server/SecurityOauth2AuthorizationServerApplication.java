package com.iskech.security_oauth2_authorization_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
@EnableRedisRepositories
@EnableDiscoveryClient
public class SecurityOauth2AuthorizationServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityOauth2AuthorizationServerApplication.class, args);
	}
	
}
