package com.yuki.tkxdpm_k17_06.ChangePassword;

public record ChangePasswordInputData (
        Long userId,
        String oldPassword,
        String newPassword){
}
