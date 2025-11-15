package com.yuki.tkxdpm_k17_06.UserAccountTesting;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {

    @Test
    void shouldThrowException_WhenEmailInvalid() {
        // email không chứa @
        assertThrows(IllegalArgumentException.class, () ->
                new UserAccount("Thinh", "thinh123", "invalid-email", "123")
        );
    }

    @Test
    void shouldEncryptPassword_WhenEncryptPasswordCalled() {
        UserAccount user = new UserAccount(
                "Thinh", "thinh123", "thinh@gmail.com", "mypassword"
        );

        FakeEncryptor encryptor = new FakeEncryptor();
        user.encryptPassword(encryptor);

        assertEquals("hashed-mypassword", user.getPasswordHash());
    }
    //Test khong duoc luu mk raw vao DB, chi ma da ma hoa
    @Test
    void shouldNotKeepPlaintextPassword_AfterEncrypt() {
        UserAccount user = new UserAccount(
                "Thinh", "thinh123", "thinh@gmail.com", "mypassword"
        );

        FakeEncryptor encryptor = new FakeEncryptor();
        user.encryptPassword(encryptor);

        assertNotEquals("mypassword", user.getPasswordHash());
    }

}
