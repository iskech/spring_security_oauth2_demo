package com.iskech.security_oauth2_authorization_server.service.impl.user;

import com.iskech.security_oauth2_authorization_server.db.dao.UserRepository;
import com.iskech.security_oauth2_authorization_server.db.entity.CustomUser;
import com.iskech.security_oauth2_authorization_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author ：liujx
 * @date ：Created in 2019/7/31 21:09
 * @description：用户业务实现实体
 * @modified By：
 * @version: V1.0
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostConstruct
	@Override
	public CustomUser initUser() {
		CustomUser iskechOld = userRepository.findByusername("iskech");
		if (iskechOld == null) {
			CustomUser iskech = userRepository.save(new CustomUser("iskech", passwordEncoder.encode("123")));
			return iskech;
		}
		return null;
	}
}
