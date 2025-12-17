package com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.RequestResetPasswordOutputData;

/**
 * Output Boundary for UC1 - Request Reset Password.
 * Interactor calls this interface to present results.
 */
public interface RequestResetPasswordOutputBoundary {
    void present(RequestResetPasswordOutputData output);
}
