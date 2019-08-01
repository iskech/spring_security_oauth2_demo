package com.iskech.security_oauth2_authorization_server.db.entity;

import javax.persistence.*;

/**
 * @author ：liujx
 * @date ：Created in 2019/8/1 21:43
 * @description：自定义角色
 * @modified By：
 * @version: V1.0
 */
@Entity
@Table(name = "role_user_rela")
public class RoleUserRela {
	@Id
	@GeneratedValue
	private Long   uuid;
	@Column(name = "user_uuid")
	private Long   userUuid;
	@Column(name = "role_uuid")
	private Long   roleUuid;
	
	public Long getUuid() {
		return uuid;
	}
	
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	
	public Long getUserUuid() {
		return userUuid;
	}
	
	public void setUserUuid(Long userUuid) {
		this.userUuid = userUuid;
	}
	
	public Long getRoleUuid() {
		return roleUuid;
	}
	
	public void setRoleUuid(Long roleUuid) {
		this.roleUuid = roleUuid;
	}
	
	public RoleUserRela(Long userUuid, Long roleUuid) {
		this.userUuid = userUuid;
		this.roleUuid = roleUuid;
	}
	
	public RoleUserRela() {
	}
}
