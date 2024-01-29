package com.hyperface.employeemanagementsystem.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.function.Function;


@Service
class JwtService {

    private String SECRET_KEY="R2d3w5ThHJ8p/u/sxyv3Q2F2AYqddKuA5FA/wThsIys=";
    String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    boolean isTokenValid(String token,UserDetails userDetails){
        return extractUserName(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractTokenExpiration(token).before(new Date());
    }

    private Date extractTokenExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts.
                builder()
                .addClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
