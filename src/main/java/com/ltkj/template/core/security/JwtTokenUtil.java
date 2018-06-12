package com.ltkj.template.core.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT工具类
 * 
 * @author zwy
 *
 */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -3301605591108950415L;

	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_CREATED = "created";	

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * 从token中取得username
	 * @param token 
	 * @return username
	 */
	public String getUsernameFromToken(String token) {

		final Claims claims = getClaimsFromToken(token);
		return claims.getSubject();
	}

	/**
	 * 从token中取得生成时间
	 * @param token
	 * @return
	 */
	public Date getCreatedDateFromToken(String token) {
		final Claims claims = getClaimsFromToken(token);
		return new Date((Long) claims.get(CLAIM_KEY_CREATED));
	}

	/**
	 * 获取token的过期时间
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token) {
		final Claims claims = getClaimsFromToken(token);
		return claims.getExpiration();
	}

	/**
	 * 获取token声明信息
	 * @param token
	 * @return
	 */
	private Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * 生产过期时间（24小时）
	 * @return
	 */
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * 验证token是否过期
	 * @param token
	 * @return
	 */
	private Boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
	}

	/**
	 * 验证toenk生成后是否修改过密码
	 * @param created
	 * @param lastPasswordReset
	 * @return
	 */
	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return lastPasswordReset != null && created.before(lastPasswordReset);
	}

	/**
	 * 生成token
	 * @param userDetails
	 * @return
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	/**
	 * 生成token
	 * @param claims
	 * @return
	 */
	String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * 验证是否token可以刷新
	 * @param token
	 * @param lastPasswordReset
	 * @return
	 */
	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getCreatedDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && !isTokenExpired(token);
	}

	/**
	 * 重新生产token
	 * @param token
	 * @return
	 */
	public String refreshToken(String token) {

		final Claims claims = getClaimsFromToken(token);
		claims.put(CLAIM_KEY_CREATED, new Date());

		return generateToken(claims);
	}

	/**
	 * 校验token是否正确
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		JwtUser user = (JwtUser) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getCreatedDateFromToken(token);
		return username.equals(user.getUsername()) && !isTokenExpired(token)
				&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());
	}
}
