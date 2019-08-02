package com.iskech.security_oauth2_authorization_server.controller;

import com.iskech.security_oauth2_authorization_server.service.UserService;
import com.iskech.security_oauth2_authorization_server.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
  @Autowired private UserService userService;

  /**
   * 获取用户信息
   *
   * @param request
   * @return
   */
  @GetMapping
  public Map<String, Object> getUserInfo(HttpServletRequest request) {
    String token = TokenUtils.getToken(request);
    return userService.parseUserInfoFromToken(token);
  }
}
