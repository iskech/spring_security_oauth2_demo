package com.iskech.security_oauth2_authorization_server.db.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：liujx
 * @date ：Created in 2019/8/1 21:43
 * @description：自定义角色
 * @modified By：
 * @version: V1.0
 */
@Entity
@Table(name = "role")
public class CustomRole {
	@Id
	@GeneratedValue
	private Long   uuid;
	@Column
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public CustomRole(String name) {
		this.name = name;
	}

	public CustomRole() {
	}

	public static List<GrantedAuthority> mapToGrantedAuthorities(List<CustomRole> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
	}
}
