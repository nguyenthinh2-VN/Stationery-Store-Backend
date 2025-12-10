package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Presenter;

public class LoginViewModel {

    private boolean success;
    private String message;
    private String token;


    public LoginViewModel(boolean success, String message, String token) {
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
