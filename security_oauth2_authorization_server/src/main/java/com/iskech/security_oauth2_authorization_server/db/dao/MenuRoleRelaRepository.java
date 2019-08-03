package com.iskech.security_oauth2_authorization_server.db.dao;

import com.iskech.security_oauth2_authorization_server.db.entity.RoleMenuRela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRoleRelaRepository extends JpaRepository<RoleMenuRela, Long> {

}
