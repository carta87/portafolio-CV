package com.monolith.platform.learning.util.jwt;

import com.monolith.platform.learning.persistence.entity.auth.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jw.secret.key}")
    private String secretKey;

    @Value("${jw.time.expiration}")
    private String timeExpiration;

    public String getToken(UserEntity userEntity) {
        return generateAccesToken(new HashMap<>(), userEntity);

    }

    private String generateAccesToken(HashMap<String, Object> extraClaims, UserEntity userEntity) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration))) // 10 minutes
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decodifica la clave en Base64
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }


    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private Claims getAllClaims(String token){
        return Jwts.parserBuilder()
               .setSigningKey(getKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
    }

    public boolean validateToken(String token, UserDetails userEntity) {
        return !isTokenExpired(token) &&
                getUsernameFromToken(token).equals(userEntity.getUsername());
    }

    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }
}
