package com.member.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtility implements Serializable {

    private static final long serialVersionUID = 12345L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60l;
    private String secretKey = "Samiran";

    // to retrieve UserName from JWT token
    public String getUserNameFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // For retrieving we need information from the token we'll watch the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    // method to check if token is generated
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // to retrieve the expiration date from token
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    // generate token for user
        public String generateToken(UserDetails userDetails) {
            Map<String, Object> claims = new HashMap<>();

            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
    }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
