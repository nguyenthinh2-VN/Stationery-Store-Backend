package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase;


import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public interface UserRepository {
    UserAccount findByEmail(String email);
    UserAccount findByUsername(String username);
}
