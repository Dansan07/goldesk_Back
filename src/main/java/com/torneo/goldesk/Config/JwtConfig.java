package com.torneo.goldesk.Config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.util.Base64;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public Key getSigningKey(){
        return Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(secret)
        );
    }

    public long getExpiration() {
        return expiration;
    }
}
