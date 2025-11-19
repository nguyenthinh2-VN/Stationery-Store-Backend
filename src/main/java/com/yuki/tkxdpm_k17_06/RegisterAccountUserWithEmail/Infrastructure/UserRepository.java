package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Infrastructure;


import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public interface UserRepository {
    boolean existsByEmail(String email);
    void save(UserAccount user);
}