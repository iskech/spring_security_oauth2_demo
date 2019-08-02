package com.iskech.security_oauth2_authorization_server.service;

import com.iskech.security_oauth2_authorization_server.db.entity.CustomUser;

import java.util.Map;

public interface UserService {
	/**
	 * 初始化用户数据iskech
	 *
	 * @return
	 */
	CustomUser initUser();

	/**
	 * 解析token
	 * @param token
	 * @return
	 */
    Map<String,Object> parseUserInfoFromToken(String token);
}
