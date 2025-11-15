package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.PasswordEncryptor;

public abstract class Account {
    protected String name;
    protected String username;
    protected String passwordHash;
    protected String email;

    protected Account(String name, String username, String email, String rawPassword) {
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
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
}
