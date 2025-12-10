package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase;

public class AuthResult {
    public final boolean success;
    public final String message;
    public final String token;

    private AuthResult(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }

    public static AuthResult success(String msg, String token) {
        return new AuthResult(true, msg, token);
    }

    public static AuthResult fail(String msg) {
        return new AuthResult(false, msg, null);
    }
}

