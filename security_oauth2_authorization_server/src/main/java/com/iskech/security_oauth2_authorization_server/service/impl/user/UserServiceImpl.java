package com.iskech.security_oauth2_authorization_server.service.impl.user;

import com.iskech.security_oauth2_authorization_server.db.dao.RoleRepository;
import com.iskech.security_oauth2_authorization_server.db.dao.RoleUserRelaRepository;
import com.iskech.security_oauth2_authorization_server.db.dao.UserRepository;
import com.iskech.security_oauth2_authorization_server.db.entity.CustomRole;
import com.iskech.security_oauth2_authorization_server.db.entity.CustomUser;
import com.iskech.security_oauth2_authorization_server.db.entity.RoleUserRela;
import com.iskech.security_oauth2_authorization_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

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
    RoleRepository roleRepository;
    @Autowired
    RoleUserRelaRepository roleUserRelaRepository;
    @Autowired
    TokenStore tokenStore;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    @Override
    public CustomUser initUser() {
        CustomUser iskechOld = userRepository.findByusername("iskech");
        if (iskechOld == null) {
            CustomUser iskech = userRepository.save(new CustomUser("iskech", passwordEncoder.encode("123")));
            CustomRole admin = roleRepository.save(new CustomRole("ADMIN"));
            roleUserRelaRepository.save(new RoleUserRela(iskech.getUuid(), admin.getUuid()));
            return iskech;
        }
        return null;
    }

    @Override
    public Map<String, Object> parseUserInfoFromToken(String token) {
        //tokenStore 可以直接读取jwt/token信息
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        Map<String, Object> value = oAuth2AccessToken.getAdditionalInformation();
        //jwt 也可以使用jwts 进行jwt的解析
        return value;
    }
}
