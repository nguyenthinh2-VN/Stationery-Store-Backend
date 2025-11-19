package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.PasswordEncryptor;

public abstract class Account {
    protected Long id;
    protected String name;
    protected String username;
    protected String passwordHash;
    protected String email;

    // Constructor dùng cho REGISTER
    protected Account(String name,
                      String username,
                      String email,
                      String rawPassword)
    {
        this.id = null;              // đăng ký mới → chưa có ID
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = rawPassword;
    }

    // Constructor dùng cho LOGIN
    protected Account(Long id, String name, String username, String email, String rawPassword)
    {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = rawPassword; // Encrypt later
    }

    protected boolean isValidPassword() {
        return passwordHash != null && passwordHash.length() >= 8;
    }

    protected boolean isValidEmail() {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }


    //Buiness Rule For Login Account
    public boolean checkPassword(String rawPassword, PasswordEncryptor encryptor) {
        return encryptor.verify(rawPassword, passwordHash);
    }

    public abstract Role getRole();

    // Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }
}
