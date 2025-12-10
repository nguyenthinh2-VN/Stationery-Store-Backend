package com.yuki.tkxdpm_k17_06.UserAccountTesting.Login;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.LoginIdentifier;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.LoginIdentifierType;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.*;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Role;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CredentialAuthPolicyTest {



    @Test
    void test_login_user_not_found() {

        IdentifierParserPolicy parser = id ->
                new LoginIdentifier(LoginIdentifierType.USERNAME, id);

        UserRepository repo = new UserRepository(){
            @Override public UserAccount findByUsername(String username) { return null; }
            @Override public UserAccount findByEmail(String email) { return null; }
            @Override public UserAccount save(UserAccount user) { return user; }
            @Override public  UserAccount findById(Long id) { return null; }
        };

        PasswordEncryptor encryptor = Mockito.mock(PasswordEncryptor.class);
        TokenGenerationPolicy tokenGen = u -> "TOKEN";

        CredentialAuthPolicy policy =
                new CredentialAuthPolicy(parser, encryptor, repo, tokenGen);

        LoginInputData input = new LoginInputData("thinh", "admin123");

        AuthResult res = policy.authenticate(input);

        assertFalse(res.success);
        assertEquals("User không tồn tại", res.message);
    }

    @Test
    void test_login_success() {

        IdentifierParserPolicy parser = id ->
                new LoginIdentifier(LoginIdentifierType.USERNAME, id);

        UserAccount fakeUser =
                new UserAccount(1L, "Thinh", "thinh", "thinh@gmail.com", "hashed", Role.USER);

        UserRepository repo = new UserRepository() {
            @Override public UserAccount findByUsername(String username) { return fakeUser; }
            @Override public UserAccount findByEmail(String email) { return null; }
            @Override public UserAccount save(UserAccount user) { return user; }
            @Override public   UserAccount findById(Long id) { return null; }
        };

        PasswordEncryptor encryptor = new PasswordEncryptor() {
            @Override
            public String hash(String raw) { return raw; }

            @Override
            public boolean verify(String raw, String hashed) {
                return raw.equals("123456");
            }
        };

        TokenGenerationPolicy tokenGen = u -> "JWT_TOKEN";

        CredentialAuthPolicy policy =
                new CredentialAuthPolicy(parser, encryptor, repo, tokenGen);

        LoginInputData input = new LoginInputData("thinh", "123456");

        AuthResult res = policy.authenticate(input);

        assertTrue(res.success);
        assertEquals("Đăng nhập thành công", res.message);
        assertEquals("JWT_TOKEN", res.token);
    }


}
