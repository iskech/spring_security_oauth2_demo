package com.iskech.security_oauth2_authorization_server.util;

import javax.servlet.http.HttpServletRequest;

/**
 *token 工具类
 */
public class TokenUtils {
    /**
     * 解析请求中的token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
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
