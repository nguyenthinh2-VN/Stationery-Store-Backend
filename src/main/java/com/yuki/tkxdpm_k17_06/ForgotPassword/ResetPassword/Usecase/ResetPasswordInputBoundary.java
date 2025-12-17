package com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.ResetPasswordInputData;

/**
 * Input Boundary for UC3 - Reset Password.
 * Controller calls this interface, not the UseCase directly.
 */
public interface ResetPasswordInputBoundary {
    void execute(ResetPasswordInputData input);
}
