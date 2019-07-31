package com.iskech.security_oauth2_authorization_server.service;

import com.iskech.security_oauth2_authorization_server.db.entity.CustomUser;

public interface UserService {
	/**
	 * 初始化用户数据iskech
	 *
	 * @return
	 */
	CustomUser initUser();
}
