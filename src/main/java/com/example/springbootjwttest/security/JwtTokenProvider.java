package com.example.springbootjwttest.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.springbootjwttest.entity.Role;
import com.example.springbootjwttest.response.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	private static final String BEARER = "Bearer ";

	@Value("${security.jwt.token.secret-key}")
	private String secretKey;
	
//	1 h = 3600 s = 3600000 ms
	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliSeconds;
	
	@Autowired
	private MyUserDetails myUserDetails;
	
//	encode secret key to base64
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		System.out.println("Secret key encode: " + secretKey);
	}

	public String createToken(String username, List<Role> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("auth", roles.stream().map(s -> 
			new SimpleGrantedAuthority(s.getAuthority())
		).filter(Objects::nonNull).collect(Collectors.toList()));

		final Date now = new Date();
		final Date expired = new Date(now.getTime() + validityInMilliSeconds);
//		LocalDateTime expired = now.plus
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expired)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
		System.out.println("Token: " + token);
		return token;
	}
	
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if(bearerToken != null && bearerToken.startsWith(BEARER)) {
			return bearerToken.substring(BEARER.length());
		}
		return null;
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		}catch(JwtException | IllegalArgumentException e) {
			throw new CustomException("Expired or invalid JWT token", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
