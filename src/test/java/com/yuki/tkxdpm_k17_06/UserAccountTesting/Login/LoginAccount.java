package com.yuki.tkxdpm_k17_06.UserAccountTesting.Login;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Infrastructure.BCryptPasswordEncryptor;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.PasswordEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class LoginAccount {
    @Test
    void testBCryptEncryptAndVerify() {
        PasswordEncryptor encryptor = new BCryptPasswordEncryptor();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String raw = "admin123";
        String hashed = encoder.encode(raw); // tự hash để test verify

        System.out.println("Hashed password = " + hashed);

        assertNotEquals(raw, hashed);
        assertTrue(encryptor.verify(raw, hashed)); // PASS nếu BCrypt hoạt động đúng
    }

    @Test
    void testWrongPassword() {
        PasswordEncryptor encryptor = new BCryptPasswordEncryptor();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String raw = "admin123";
        String hashed = encoder.encode(raw); // tự hash để test verify

        assertFalse(encryptor.verify("wrongpass", hashed));
    }
}
