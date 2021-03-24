package com.hobbyhub.models.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtils {

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  @Value("${jwt.maxAge}")
  private int tokenMaxAge;

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  public String createToken(Map<String, Object> claims, String subject) {
    Date now = new Date(System.currentTimeMillis());
    Date until = new Date(System.currentTimeMillis() + tokenMaxAge);
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(until)
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaim(token);
    return claimsResolver.apply(claims);
  }

  public Claims extractAllClaim(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
}
