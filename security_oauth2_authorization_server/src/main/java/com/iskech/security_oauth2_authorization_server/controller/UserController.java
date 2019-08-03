package com.iskech.security_oauth2_authorization_server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iskech.security_oauth2_authorization_server.db.dao.MenuRepository;
import com.iskech.security_oauth2_authorization_server.db.dao.MenuRoleRelaRepository;
import com.iskech.security_oauth2_authorization_server.db.entity.CustomMenu;
import com.iskech.security_oauth2_authorization_server.service.UserService;
import com.iskech.security_oauth2_authorization_server.util.TokenUtils;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author ：liujx
 * @date ：Created in 2019/7/30 22:28
 * @description：用户前端控制器
 * @modified By：
 * @version: V1.0
 */
@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired private UserService userService;

  @Resource(name = "tokenServices")
  private ConsumerTokenServices tokenServices;

  /**
   * 获取用户信息
   *
   * @param request
   * @return
   */
  @ApiOperation(value = "根据token解析用户信息", notes = "")
  @GetMapping
  public Map<String, Object> getUserInfo(HttpServletRequest request) {
    String token = TokenUtils.getToken(request);
    return userService.parseUserInfoFromToken(token);
  }

  /**
   * 销毁token,但仅针对token持久化的情况（jwt token是无法销毁或者失效）
   *
   * @param request
   * @return
   */
  @ApiOperation(value = "销毁token", notes = "")
  @DeleteMapping
  public boolean revokeToken(HttpServletRequest request) {
    String token = TokenUtils.getToken(request);
    return tokenServices.revokeToken(token);
  }

  @Autowired private RestTemplate restTemplate;
  @Autowired private EurekaClient eurekaClient;
  @Autowired private MenuRoleRelaRepository menuRoleRelaRepository;
  @Autowired private MenuRepository menuRepository;

  @RequestMapping(
      value = "/services",
      method = RequestMethod.GET,
      produces = {"application/json;charset=UTF-8"})
  public String testController1() {
    StringBuilder sb = new StringBuilder();
    Applications applications = eurekaClient.getApplications();

    for (Application application : applications.getRegisteredApplications()) {
      String applicationName = application.getName();
      try {
        // 通过swagger查询服务接口的接口来获取该服务节点下的所有服务接口明细
        String rsp =
            restTemplate
                .getForEntity("http://" + application.getName() + "/v2/api-docs", String.class)
                .getBody();
        // 使用fastjson进行解析
        JSONObject apiObject = JSON.parseObject(rsp);
        JSONObject pathsObject = apiObject.getJSONObject("paths");
        for (Object o : pathsObject.values()) {}

        for (Map.Entry<String, Object> path : pathsObject.entrySet()) {
          // 先初始化父节点
          CustomMenu parentOlder  =  menuRepository.findByname(applicationName+"模块");
          if(parentOlder==null){
            CustomMenu customMenu =
                    builderCreateMenu(
                            path.getKey(), applicationName, null, null, null);
            CustomMenu save1 = menuRepository.save(customMenu);
            parentOlder=save1;
          }

          JSONObject value = (JSONObject) path.getValue();
          for (Map.Entry<String, Object> stringObjectEntry : value.entrySet()) {
            JSONObject value1 = (JSONObject) stringObjectEntry.getValue();
            CustomMenu customMenup = new CustomMenu();
            CustomMenu customMenu1 =
                builderCreateMenu(
                    path.getKey(),
                    applicationName,
                    parentOlder.getUuid(),
                    stringObjectEntry.getKey(),
                    (String) value1.get("summary"));
            CustomMenu save = menuRepository.save(customMenu1);
          }
          System.out.println(path.getKey());
        }
        sb.append(rsp);
      } catch (Exception ex) {
        // 这里需要注意对于没有使用swagger的服务是无法调用swagger接口的，会抛出异常，需要对异常捕获后继续执行
        continue;
      }
    }
    return sb.toString();
  }

  private CustomMenu builderCreateMenu(
      String path, String applicationName, Long parentId, String type, String summary) {
    CustomMenu customMenu = new CustomMenu();
    customMenu.setUrl(path);
    customMenu.setType(type);
    customMenu.setSystemCode(applicationName);
    customMenu.setName(summary == null ? applicationName + "模块" : summary);
    customMenu.setParentId(parentId);
    customMenu.setCreateTime(new Timestamp(System.currentTimeMillis()));
    customMenu.setUpdateTime(customMenu.getCreateTime());
    return customMenu;
  }
}
