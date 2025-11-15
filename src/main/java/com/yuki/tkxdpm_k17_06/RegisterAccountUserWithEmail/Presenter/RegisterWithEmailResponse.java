package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Presenter;

public class RegisterWithEmailResponse {
    private boolean success;
    private String message;

    public RegisterWithEmailResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
