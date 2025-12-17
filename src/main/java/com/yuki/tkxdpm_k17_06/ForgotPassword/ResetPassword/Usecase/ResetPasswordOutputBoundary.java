package com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.ResetPasswordOutputData;

/**
 * Output Boundary for UC3 - Reset Password.
 * Interactor calls this interface to present results.
 */
public interface ResetPasswordOutputBoundary {
    void present(ResetPasswordOutputData output);
}
