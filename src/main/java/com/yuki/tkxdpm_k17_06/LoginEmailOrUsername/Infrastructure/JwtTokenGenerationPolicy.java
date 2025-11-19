package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Infrastructure;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.TokenGenerationPolicy;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
                .claim("username", user.getUsername())
                .claim("role", user.getRole().name())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
