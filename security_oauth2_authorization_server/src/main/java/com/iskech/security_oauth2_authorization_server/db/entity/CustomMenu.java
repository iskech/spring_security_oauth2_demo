package com.iskech.security_oauth2_authorization_server.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author ：liujx
 * @date ：Created in 2019/8/1 21:43
 * @description：自定义菜单
 * @modified By：
 * @version: V1.0
 */
@Entity
@Table(name = "menu")
public class CustomMenu {
  @Id @GeneratedValue private Long uuid;
  @Column private String name;
  @Column private String url;
  @Column private String systemCode;
  @Column private String type;
  @Column private String description;
  @Column private Long parentId;
  @Column private Timestamp createTime;
  @Column private Timestamp updateTime;

  public Long getUuid() {
    return uuid;
  }

  public void setUuid(Long uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getSystemCode() {
    return systemCode;
  }

  public void setSystemCode(String systemCode) {
    this.systemCode = systemCode;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  public CustomMenu(
      String name,
      String url,
      String systemCode,
      String type,
      String description,
      Long parentId,
      Timestamp createTime,
      Timestamp updateTime) {
    this.name = name;
    this.url = url;
    this.systemCode = systemCode;
    this.type = type;
    this.description = description;
    this.parentId = parentId;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

    public CustomMenu() {
    }
}
