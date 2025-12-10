package com.yuki.tkxdpm_k17_06;

public interface PasswordEncryptor {
    String hash(String rawPassword);
    boolean verify(String rawPassword, String hashed);
}