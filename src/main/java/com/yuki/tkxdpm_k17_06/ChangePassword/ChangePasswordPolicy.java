package com.yuki.tkxdpm_k17_06.ChangePassword;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.PasswordEncryptor;

public class ChangePasswordPolicy {
    private final PasswordEncryptor encryptor;

    public ChangePasswordPolicy(PasswordEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public boolean verifyOldPassword(String rawOld, String storedHash) {
        return encryptor.verify(rawOld, storedHash);
    }

    public boolean isValidNewPassword(String newPassword, String oldPassword) {
        if (newPassword.length() < 8) return false;
        if (newPassword.equals(oldPassword)) return false;
        // Có thể thêm rule: chứa số, chữ hoa, chữ thường…
        return true;
    }

    public String hashNewPassword(String newPassword) {
        return encryptor.hash(newPassword);
    }

}
