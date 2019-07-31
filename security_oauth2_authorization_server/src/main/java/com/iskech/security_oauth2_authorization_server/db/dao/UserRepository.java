package com.iskech.security_oauth2_authorization_server.db.dao;

import com.iskech.security_oauth2_authorization_server.db.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
	
	/**
	 * 根据用户名查找 注意方法命名需要遵从jpa规范
	 *
	 * @param username
	 * @return
	 */
	CustomUser findByusername(String username);
}
