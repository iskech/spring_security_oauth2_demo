package com.iskech.security_oauth2_authorization_server.config;

import com.iskech.security_oauth2_authorization_server.service.impl.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.Arrays;

/**
 * @author ：liujx
 * @date ：Created in 2019/7/30 22:02
 * @description：oauth2配置类
 * @modified By：
 * @version: V1.0
 */
@Configuration
public class Oauth2OConfig extends AuthorizationServerConfigurerAdapter {
  @Autowired private CustomUserDetails userDetailsService;

  @Value("${JWT_SECRET}")
  private String secret;

  @Override
  public void configure(ClientDetailsServiceConfigurer client) throws Exception {
    // 配置客户端，即指定可以通过验证的服务端
    client
        .inMemory()
        .withClient("myClientId")
        .secret(passwordEncoder().encode("iskech"))
        .authorizedGrantTypes("password", "client_credentials","refresh_token")
        .scopes("webClient", "mobileClient");
  }

  /** 注入authenticationManager 来支持 password grant type */
  @Autowired private AuthenticationManager authenticationManager;

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    // 授权端点相关配置
    final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer()));
    endpoints
        .tokenStore(tokenStore())
      //  .accessTokenConverter(accessTokenConverter())
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
    // oauth认证端点需要开放权限否则，资源服务请求认证无法访问端点而报403错误码
    oauthServer
        // 开启/oauth/token_key验证端口无权限访问
        .tokenKeyAccess("permitAll()")
        // 开启/oauth/check_token验证端口认证权限访问
        .checkTokenAccess("isAuthenticated()");
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

 /* @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    // 对称加密jwt
    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(secret);
    // final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new
    // ClassPathResource("mytest.jks"), "mypass".toCharArray());
    // converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
    return converter;
  }*/

  @Bean
  public TokenEnhancer tokenEnhancer() {
    return new CustomTokenEnhancer();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // redis token store configuration

  @Autowired
  private RedisConnectionFactory connectionFactory;
  @Bean
  public TokenStore tokenStore() {
      return new RedisTokenStore(connectionFactory);
  }

}
