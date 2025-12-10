package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Infrastructure;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.TokenGenerationPolicy;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenGenerationPolicy implements TokenGenerationPolicy {
    private final SecretKey key;
    private final long expirationMs;

    public JwtTokenGenerationPolicy(SecretKey key, long expirationMs) {
        this.key = key;
        this.expirationMs = expirationMs;
    }

    @Override
    public String createToken(UserAccount user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(user.getEmail()) // Email as subject
                .claim("userId", user.getId()) // Add userId claim
                .claim("username", user.getUsername())
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(exp)
                .signWith(key) // SignatureAlgorithm is deprecated in JJWT 0.12.x
                .compact();
    }
}
