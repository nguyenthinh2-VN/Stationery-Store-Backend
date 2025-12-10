package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase;

public class LoginInputData {
    private final LoginMethod method; // NEW
    private final String identifier;
    private final String password;
    private final String oauthToken;

    // Constructor cho credential
    public LoginInputData(String identifier, String password) {
        this.method = LoginMethod.CREDENTIAL;
        this.identifier = identifier;
        this.password = password;
        this.oauthToken = null;
    }

    // Constructor cho google
    public LoginInputData(String oauthToken) {
        this.method = LoginMethod.GOOGLE;
        this.identifier = null;
        this.password = null;
        this.oauthToken = oauthToken;
    }

    public LoginMethod getMethod() {
        return method;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    public String getOauthToken() {
        return oauthToken;
    }
}