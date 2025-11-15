package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity;


import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.PasswordEncryptor;

public class UserAccount extends Account {


    public UserAccount(String name, String username, String email, String rawPassword) {
        super(name, username, email, rawPassword);

        if (!isValidEmail()) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }

        if (!isValidPassword()) {
            throw new IllegalArgumentException("Mật khẩu phải từ 8 kí tự");
        }
    }

    public void encryptPassword(PasswordEncryptor encryptor) {
        this.passwordHash = encryptor.hash(this.passwordHash);
        //this.passwordHash = this.passwordHash; //-----(Testing)
    }

    @Override
    public Role getRole() {
        return Role.USER;
    }
}
