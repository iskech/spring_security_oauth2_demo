package com.iskech.security_oauth2_authorization_server.util;

import com.iskech.security_oauth2_authorization_server.db.entity.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.function.Function;

@Component
@PropertySource("classpath:oauth2-authorization-service.yml")
public class JwtTokenUtils implements Serializable {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "iat";
    private static final long serialVersionUID = -3301605591108950415L;
   // @SuppressFBWarnings(value = "SE_BAD_FIELD", justification = "It's okay here")
    private Clock clock = DefaultClock.INSTANCE;

  @Value("${JWT_SECRET}")
  private String secret;

    //@Value("${jwt.expiration}")
    private Long expiration=10000L;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Collection<LinkedHashMap<String,String>> getRolesFromToken(String token) {
        Claims allClaimsFromToken = getAllClaimsFromToken(token);
        Collection<LinkedHashMap<String,String>> roles = (Collection<LinkedHashMap<String,String>>) allClaimsFromToken.get("authorities");
        return roles;
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;
    public Claims getAllClaimsFromToken(String token) {
          //解析jwt
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return claims;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken2(claims, userDetails);
    }
    private String doGenerateToken2(Map<String, Object> claims, UserDetails userDetails) {
        //将登录用户角色放进map中生成jwt
        LinkedList<String> roles = new LinkedList<String>();
        Collection<SimpleGrantedAuthority> authorities =  (Collection<SimpleGrantedAuthority>)userDetails.getAuthorities();

        claims.put("authorities",authorities);
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
            && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        CustomUser user = (CustomUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
            username.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created,new Date())
        );
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
