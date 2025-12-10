package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase;

public class LoginOutputData {
    private final boolean success;
    private final String message;
    private final String token;

    public LoginOutputData(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
