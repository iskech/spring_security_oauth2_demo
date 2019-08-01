package com.iskech.security_oauth2_authorization_server.controller;

import com.iskech.security_oauth2_authorization_server.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    TokenStore tokenStore;
    @Autowired
    RedisUtils redisUtils;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";


    @GetMapping
    public Map<String, Object> getExtraInfo(HttpServletRequest request) {
        String token = getToken(request);
        Map<String, Object> map = new HashMap<>();
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        map.put(token, oAuth2AccessToken);
        return map;
    }

    private String getToken(HttpServletRequest request) {
        String accessToken = request.getParameter("access_token");
        if (accessToken == null) {
            accessToken = request.getHeader("Authorization");
            if (accessToken != null && accessToken.contains("Bearer ")) {
                accessToken = accessToken.substring("Bearer ".length());
            }
        }
        return accessToken;
    }
}
