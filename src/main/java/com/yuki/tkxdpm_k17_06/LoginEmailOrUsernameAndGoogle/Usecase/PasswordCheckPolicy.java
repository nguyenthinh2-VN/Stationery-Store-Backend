package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public interface PasswordCheckPolicy {
    boolean check(UserAccount user, String rawPassword);
}
