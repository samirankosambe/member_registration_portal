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

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private String secretKey = "Samiran";

    // to retrieve UserName from JWT token
    public String getUserNameFromToken(String Token) {
        return getClaimsFromToken(Token, Claims::getSubject);
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // For retrieving we need information from the token we'll watch the secret key
    private Claims getAllClaimsFromToken(String Token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(Token).getBody();
    }

    // method to check if token is generated
    public Boolean isTokenExpired(String Token) {
        final Date expiration = getExpirationDateFromToken(Token);
        return expiration.before(new Date());
    }

    // to retrieve the expiration date from token
    public Date getExpirationDateFromToken(String Token) {
        return getClaimsFromToken(Token, Claims::getExpiration);
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
    public Boolean validateToken(String Token, UserDetails userDetails) {
        final String userName = getUserNameFromToken(Token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(Token));
    }

}
