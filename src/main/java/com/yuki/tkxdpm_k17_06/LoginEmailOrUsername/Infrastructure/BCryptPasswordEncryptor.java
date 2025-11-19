package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Infrastructure;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.PasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncryptor implements PasswordEncryptor {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public boolean verify(String rawPassword, String hashed) {
        return encoder.matches(rawPassword, hashed);
    }

}
