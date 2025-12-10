package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase;


import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public interface UserRepository {
    UserAccount save(UserAccount user);
    UserAccount findByEmail(String email);
    UserAccount findByUsername(String username);
    UserAccount findById(Long id);
}
