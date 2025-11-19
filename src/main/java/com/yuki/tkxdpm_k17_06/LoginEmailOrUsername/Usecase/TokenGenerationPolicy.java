package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public interface TokenGenerationPolicy {
    String createToken(UserAccount user);
}
