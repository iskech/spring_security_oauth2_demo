package com.iskech.security_oauth2_authorization_server.db.dao;

import com.iskech.security_oauth2_authorization_server.db.entity.RoleUserRela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleUserRelaRepository extends JpaRepository<RoleUserRela, Long> {
	List<RoleUserRela> findByuserUuid(Long userUuid);
}
