package com.iskech.security_oauth2_authorization_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author ：liujx
 * @date ：Created in 2019/7/30 22:02
 * @description：oauth2配置类
 * @modified By：
 * @version: V1.0
 */
@Configuration
public class Oauth2OConfig extends AuthorizationServerConfigurerAdapter {
    //@Autowired private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer client) throws Exception {
        // 配置客户端，即指定可以通过验证的服务端
        client
                .inMemory()
                .withClient("myClientId")
                .secret(passwordEncoder().encode("iskech"))
                .authorizedGrantTypes("password", "client_credentials")
                .scopes("webClient", "mobileClient");
    }

    /**
     * 注入authenticationManager
     * 来支持 password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        //oauth认证端点需要开放权限否则，资源服务请求认证无法访问端点而报403错误码
        oauthServer
                // 开启/oauth/token_key验证端口无权限访问
                .tokenKeyAccess("permitAll()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}