package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control;

public interface PasswordEncryptor {
    String hash(String rawPassword);
    boolean verify(String rawPassword, String hashed);
}
