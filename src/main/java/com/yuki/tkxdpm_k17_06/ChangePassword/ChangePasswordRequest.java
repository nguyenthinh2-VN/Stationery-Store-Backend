package com.yuki.tkxdpm_k17_06.ChangePassword;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {}
