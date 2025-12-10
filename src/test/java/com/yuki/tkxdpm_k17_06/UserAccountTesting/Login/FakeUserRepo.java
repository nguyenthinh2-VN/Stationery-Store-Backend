package com.yuki.tkxdpm_k17_06.UserAccountTesting.Login;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Role;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

class FakeUserRepo implements UserRepository {

    @Override
    public UserAccount findByEmail(String email) {
        return new UserAccount(1L, "Test", "username", email, "$2a123", Role.USER);
    }

    @Override
    public UserAccount findByUsername(String username) {
        return null;
    }

    @Override
    public UserAccount findById(Long id) {
        return null;
    }

    @Override
    public UserAccount save(UserAccount user) {
        return user;
    }
}

