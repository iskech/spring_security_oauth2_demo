package com.iskech.security_oauth2.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：liujx
 * @date ：Created in 2019/7/30 22:28
 * @description：用户前端控制器
 * @modified By：
 * @version: V1.0
 */
@RestController
@RequestMapping("/users")
public class UserController {
  @GetMapping
  public Map<String, Object> user(OAuth2Authentication oAuth2Authentication) {
    Map<String, Object> map = new HashMap<>();
    map.put("user", oAuth2Authentication.getUserAuthentication().getPrincipal());
    map.put("authorities", oAuth2Authentication.getUserAuthentication().getAuthorities());
    return map;
  }
}
