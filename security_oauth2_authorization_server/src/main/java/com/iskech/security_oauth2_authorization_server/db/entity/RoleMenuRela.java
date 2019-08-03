package com.iskech.security_oauth2_authorization_server.db.entity;

import javax.persistence.*;

/**
 * @author ：liujx
 * @date ：Created in 2019/8/1 21:43
 * @description：角色菜单关联实体
 * @modified By：
 * @version: V1.0
 */
@Entity
@Table(name = "role_menu_rela")
public class RoleMenuRela {
  @Id @GeneratedValue private Long uuid;
  @Column private Long roleUuid;
  @Column private Long menuUuid;

  public Long getUuid() {
    return uuid;
  }

  public void setUuid(Long uuid) {
    this.uuid = uuid;
  }

  public Long getRoleUuid() {
    return roleUuid;
  }

  public void setRoleUuid(Long roleUuid) {
    this.roleUuid = roleUuid;
  }

  public Long getMenuUuid() {
    return menuUuid;
  }

  public void setMenuUuid(Long menuUuid) {
    this.menuUuid = menuUuid;
  }

  public RoleMenuRela(Long roleUuid, Long menuUuid) {
    this.roleUuid = roleUuid;
    this.menuUuid = menuUuid;
  }

  public RoleMenuRela() {}
}
