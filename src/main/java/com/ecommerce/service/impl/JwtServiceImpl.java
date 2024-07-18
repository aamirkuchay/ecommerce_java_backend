package com.ecommerce.service.impl;

import com.ecommerce.service.JwtService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;


import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();
    }

  
    @Override
	  public String extractUsername(String token){
      return extractClaims(token,Claims::getSubject);
  }

    private Key getSiginKey() {
       byte[] key = Decoders.BASE64.decode("413F4428472B4B35783G8993H684HD6894H4563H0UKR3TYU3");
       return Keys.hmacShaKeyFor(key);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final  Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
//        return Jwts.parserBuilder().setSigningKey(getSiginKey()).build()
//                .parseClaimsJwt(token).getBody();
    	 return Jwts.parserBuilder()
                 .setSigningKey(getSiginKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
    }



    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }



    private boolean isTokenExpired(String token) {
       return extractClaims(token,Claims::getExpiration).before(new Date());
    }


	


}
