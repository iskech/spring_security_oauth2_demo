package com.iskech.security_oauth2_authorization_server.service.impl.user;

import com.iskech.security_oauth2_authorization_server.db.dao.RoleRepository;
import com.iskech.security_oauth2_authorization_server.db.dao.RoleUserRelaRepository;
import com.iskech.security_oauth2_authorization_server.db.dao.UserRepository;
import com.iskech.security_oauth2_authorization_server.db.entity.CustomRole;
import com.iskech.security_oauth2_authorization_server.db.entity.CustomUser;
import com.iskech.security_oauth2_authorization_server.db.entity.RoleUserRela;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	UserRepository         userRepository;
	@Autowired
	RoleUserRelaRepository roleUserRelaRepository;
	@Autowired
	RoleRepository         roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		CustomUser byName = userRepository.findByusername(s);
		List<RoleUserRela> byuserUuid = roleUserRelaRepository.findByuserUuid(byName.getUuid());
		ArrayList<CustomRole> roles = new ArrayList<CustomRole>();
		for (RoleUserRela roleUserRela : byuserUuid) {
			Optional<CustomRole> byId = roleRepository.findById(roleUserRela.getRoleUuid());
			if (byId != null) {
				roles.add(byId.get());
			}
		}
		
		List<GrantedAuthority> grantedAuthorities = CustomRole.mapToGrantedAuthorities(roles);
		byName.setAuthorities(grantedAuthorities);
		return byName;
	}
}
