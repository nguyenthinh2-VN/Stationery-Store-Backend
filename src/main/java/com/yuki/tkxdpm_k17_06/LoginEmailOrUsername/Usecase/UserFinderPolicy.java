package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.LoginIdentifier;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public interface UserFinderPolicy {
    UserAccount find(LoginIdentifier identifier);
}
