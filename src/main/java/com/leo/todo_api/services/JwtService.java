package com.leo.todo_api.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;
import javax.crypto.SecretKey; // ✅ This is the correct one

@Service
public class JwtService {

  private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private final long accessExpiration = 1000 * 60 * 15; // 15 minutes
  private final long refreshExpiration = 1000 * 60 * 60 * 24; // 24 hours

  public String generateAccessToken(String email) {
    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
            .signWith(key)
            .compact();
  }

  public String generateRefreshToken(String email) {
    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
            .signWith(key)
            .compact();
  }

  public String extractEmail(String token) {
    return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();

    /*Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .forEach((k, v) -> System.out.println(k + ": " + v));*/

  }

  public boolean isTokenValid(String token) {
    try {
      //Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}

