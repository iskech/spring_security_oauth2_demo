package com.iskech.security_oauth2_authorization_server.db.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author ：liujx
 * @date ：Created in 2019/7/31 21:04
 * @description：自定义用户
 * @modified By：
 * @version: V1.0
 */
@Entity
@Table(name = "user")
public class CustomUser implements UserDetails {
	@Id
	@GeneratedValue
	private Long                                   uuid;
	@Column
	private String                                 password;
	@Column
	private String                                 username;
	@Transient
	private Collection<? extends GrantedAuthority> authorities;
	
	public CustomUser(String iskech, String encode) {
		this.username = iskech;
		this.password = encode;
	}
	
	public CustomUser() {
	
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public Long getUuid() {
		return uuid;
	}
	
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
}
