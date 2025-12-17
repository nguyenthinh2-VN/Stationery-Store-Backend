package com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.RequestResetPasswordInputData;

/**
 * Input Boundary for UC1 - Request Reset Password.
 * Controller calls this interface, not the UseCase directly.
 */
public interface RequestResetPasswordInputBoundary {
    void execute(RequestResetPasswordInputData input);
}
