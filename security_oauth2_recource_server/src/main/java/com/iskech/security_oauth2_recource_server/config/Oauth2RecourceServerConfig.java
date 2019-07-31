package com.iskech.security_oauth2_recource_server.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
public class Oauth2RecourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties oAuth2ClientProperties;

    @Autowired
    private AuthorizationServerProperties authorizationServerProperties;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests().anyRequest().permitAll().antMatchers("/oauth/**")
                .permitAll();
    }



    /**
     * 令牌解析（通过访问授权服务器解析令牌-适用 JDBC、内存存储）
     * 请求资源服务器下受保护的接口，资源服务器会主动根解析请求头中token并
     * 访问认证服务认证该token
     * @return
     */
    @Bean
    public ResourceServerTokenServices tokenServices() {
        //读取yml配置的autht2端点及密钥，客户端id等
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(authorizationServerProperties.getCheckTokenAccess());
        remoteTokenServices.setClientId(oAuth2ClientProperties.getClientId());
        remoteTokenServices.setClientSecret(oAuth2ClientProperties.getClientSecret());
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
        return remoteTokenServices;
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }

    @Bean
    public AuthorizationServerProperties authorizationServerProperties() throws Exception {
        return new AuthorizationServerProperties();
    }

}
