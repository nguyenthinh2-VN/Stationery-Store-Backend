package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity;

public class AdminAccount extends Account{
    private String permissionLevel;

    public AdminAccount(String name, String username, String email, String rawPassword, String permissionLevel) {
        super(name, username, email, rawPassword);
        this.permissionLevel = permissionLevel;
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }
}
