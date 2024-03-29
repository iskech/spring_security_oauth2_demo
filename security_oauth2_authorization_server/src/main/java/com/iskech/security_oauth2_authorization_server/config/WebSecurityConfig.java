package com.iskech.security_oauth2_authorization_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.iskech.security_oauth2_authorization_server.service.impl.user.CustomUserDetails;

/**
 * @author ：liujx
 * @date ：Created in 2019/7/30 22:19
 * @description：security 相关配置类
 * @modified By：
 * @version: V1.0
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired private BCryptPasswordEncoder passwordEncoder;
  @Autowired private CustomUserDetails userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // 配置加载用户的方式 (内存，jdbc,ldap)
    auth.authenticationProvider(daoAuthenticationProvider());

    /*auth.inMemoryAuthentication()
    .withUser("iskech")
    .password(passwordEncoder.encode("123"))
    .roles("admin", "user")
    .and()
    .withUser("ljs")
    .password("123")
    .roles("user");*/
  }

  @Bean
  public AuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    // 用户未找到异常隐藏禁用
    daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
    // 解码
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    return daoAuthenticationProvider;
  }

  @Override
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return super.userDetailsServiceBean();
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    // @formatter:off
    http.authorizeRequests()
        .antMatchers("/login")
        .permitAll()
        .antMatchers("/users/**")
        .permitAll()
        .antMatchers("/tokens/**")
        .permitAll()
        .antMatchers("/swagger*/**")
        .permitAll()
        .antMatchers("/v2/api-docs/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .permitAll()
        .and()
        .csrf()
        .disable();
    // @formatter:on
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
