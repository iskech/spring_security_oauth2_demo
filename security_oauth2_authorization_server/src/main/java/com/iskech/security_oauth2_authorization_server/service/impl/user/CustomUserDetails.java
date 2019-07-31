package com.iskech.security_oauth2_authorization_server.service.impl.user;

import com.iskech.security_oauth2_authorization_server.db.dao.UserRepository;
import com.iskech.security_oauth2_authorization_server.db.entity.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ：liujx
 * @date ：Created in 2019/7/31 20:58
 * @description：自定义用户校验service
 * @modified By：
 * @version: V1.0
 */
@Service
public class CustomUserDetails implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		CustomUser byName = userRepository.findByusername(s);
		return byName;
	}
}
