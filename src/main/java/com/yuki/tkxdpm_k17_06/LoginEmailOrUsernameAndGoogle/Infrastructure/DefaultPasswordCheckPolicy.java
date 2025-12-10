package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Infrastructure;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.PasswordCheckPolicy;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.PasswordEncryptor;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public class DefaultPasswordCheckPolicy implements PasswordCheckPolicy {
    private final PasswordEncryptor encryptor;

    public DefaultPasswordCheckPolicy(PasswordEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Override
    public boolean check(UserAccount user, String rawPassword) {
        return encryptor.verify(rawPassword, user.getPasswordHash());
    }
}
