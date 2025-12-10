package com.yuki.tkxdpm_k17_06.UserAccountTesting.Login;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.PasswordEncryptor;

class FakeEncryptor implements PasswordEncryptor {
    @Override
    public String hash(String rawPassword) {
        return rawPassword;
    }

    @Override
    public boolean verify(String raw, String hashed) {
        return true;
    }
}

