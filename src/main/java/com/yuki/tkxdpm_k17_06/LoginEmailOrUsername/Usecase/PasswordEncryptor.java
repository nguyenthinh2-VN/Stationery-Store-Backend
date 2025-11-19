package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase;



public interface PasswordEncryptor {

    boolean verify(String rawPassword, String hashed);
}
