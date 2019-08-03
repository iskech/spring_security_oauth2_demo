package com.iskech.gateway.filter;

import com.iskech.gateway.util.Oauth2Utils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//@Component
public class GatewayFilter extends ZuulFilter {

	private static final Logger log = LoggerFactory.getLogger(GatewayFilter.class);

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_TOKEN_TYPE = "Bearer";

	@Autowired
	@Qualifier("_oAuth2RestTemplate")
	OAuth2RestTemplate oAuth2RestTemplate;


	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		String accessToken=null;
		try {
			accessToken = getToken(request);
			OAuth2AccessToken oauth2AccessToken = Oauth2Utils.checkTokenInOauth2Client(accessToken);
			if (oauth2AccessToken != null || isIgnoreUrl(request)) {
				context.setSendZuulResponse(true);
				setWebSocketHeader(context);
				setAuthorizationHeader(context);
			}else {
				context.setSendZuulResponse(false);
				context.setResponseStatusCode(401);
			}
		} catch (Exception e) {
			log.error(null, e);
			context.setSendZuulResponse(false);
			context.setResponseStatusCode(401);
		}
       /* if (accessToken != null && !"".equals(accessToken)) {
		    try {
				UserOnlineResponse userInfo = (UserOnlineResponse) redisUtil.get("user_online:"+accessToken);
                setUserHeader(context, userInfo);
            }catch (Exception e){

            }
        }*/

		return null;
	}

	private boolean isIgnoreUrl(HttpServletRequest request) {
		if(request.getRequestURI().equals("/auth/oauth/token")) {
			return true;
		}
		if(request.getRequestURI().contains("/login")) {
			return true;
		}
		return false;
	}

	private String getToken(HttpServletRequest request) {
		String accessToken = request.getParameter("access_token");
		if (accessToken == null) {
			accessToken = request.getHeader("Authorization");
			if (accessToken != null && accessToken.contains("Bearer ")) {
				accessToken = accessToken.substring("Bearer ".length());
			}
		}
		return accessToken;
	}

	private void setAuthorizationHeader(RequestContext context) {
		context.addZuulRequestHeader(AUTHORIZATION_HEADER,
				String.format("%s %s", BEARER_TOKEN_TYPE, oAuth2RestTemplate.getAccessToken().toString()));
	}

	private void setWebSocketHeader(RequestContext context) {
		HttpServletRequest request = context.getRequest();

		String upgradeHeader = request.getHeader("Upgrade");

		if (null == upgradeHeader) {
			upgradeHeader = request.getHeader("upgrade");
		}

		if (null != upgradeHeader && "websocket".equalsIgnoreCase(upgradeHeader)) {
			context.addZuulRequestHeader("Connection", "Upgrade");
		}
	}

	private void setUserHeader(RequestContext context, UserOnlineResponse userInfo) throws UnsupportedEncodingException {
		context.addZuulRequestHeader("user",URLEncoder.encode(JSONUtil.toJson(userInfo),"UTF-8"));
	}

}
