package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity;

import java.time.LocalDateTime;

public class Otp {

    private Long id;
    private String email;
    private String code;
    private LocalDateTime expiredAt;
    private boolean used;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }

    public boolean canUse() {
        return !used && !isExpired();
    }

    public void markUsed() {
        this.used = true;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
