package com.iskech.security_oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author ：liujx
 * @date ：Created in 2019/7/30 22:19
 * @description：security 相关配置类
 * @modified By：
 * @version: V1.0
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // 配置加载用户的方式 (内存，jdbc,ldap)
    auth.inMemoryAuthentication()
        .withUser("iskech")
        .password(passwordEncoder.encode("123"))
        .roles("admin", "user")
        .and()
        .withUser("ljs")
        .password("123")
        .roles("user");
    
  }
  
  @Override
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return super.userDetailsServiceBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/users")
        .permitAll()
        .antMatchers("/oauth/token")
        .permitAll();
  
  
  }
  
  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
