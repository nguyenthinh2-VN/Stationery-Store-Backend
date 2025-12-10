package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase;

public interface AuthPolicy {
    AuthResult authenticate(LoginInputData input);
}
