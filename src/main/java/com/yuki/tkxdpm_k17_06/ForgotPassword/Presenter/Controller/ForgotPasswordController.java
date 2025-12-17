package com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.Controller;

import com.yuki.tkxdpm_k17_06.ForgotPassword.DTO.ForgotPasswordRequest;
import com.yuki.tkxdpm_k17_06.ForgotPassword.DTO.ResetPasswordRequest;
import com.yuki.tkxdpm_k17_06.ForgotPassword.DTO.VerifyOtpRequest;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.RequestResetPasswordPresenter;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.ResetPasswordPresenter;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.VerifyOtpPresenter;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.ViewModel.RequestResetPasswordViewModel;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.ViewModel.ResetPasswordViewModel;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.ViewModel.VerifyOtpViewModel;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.Usecase.RequestResetPasswordInputBoundary;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.RequestResetPasswordInputData;
import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.Usecase.ResetPasswordInputBoundary;
import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.ResetPasswordInputData;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.VerifyOtpInputBoundary;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.VerifyOtpInputData;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Forgot Password feature.
 * Calls InputBoundary interfaces, not UseCases directly (Clean Architecture).
 */
@RestController
@RequestMapping("/auth")
public class ForgotPasswordController {

    private final RequestResetPasswordInputBoundary requestResetPasswordInteractor;
    private final VerifyOtpInputBoundary verifyOtpInteractor;
    private final ResetPasswordInputBoundary resetPasswordInteractor;

    private final RequestResetPasswordPresenter requestResetPasswordPresenter;
    private final VerifyOtpPresenter verifyOtpPresenter;
    private final ResetPasswordPresenter resetPasswordPresenter;

    public ForgotPasswordController(
            RequestResetPasswordInputBoundary requestResetPasswordInteractor,
            VerifyOtpInputBoundary verifyOtpInteractor,
            ResetPasswordInputBoundary resetPasswordInteractor,
            RequestResetPasswordPresenter requestResetPasswordPresenter,
            VerifyOtpPresenter verifyOtpPresenter,
            ResetPasswordPresenter resetPasswordPresenter) {
        this.requestResetPasswordInteractor = requestResetPasswordInteractor;
        this.verifyOtpInteractor = verifyOtpInteractor;
        this.resetPasswordInteractor = resetPasswordInteractor;
        this.requestResetPasswordPresenter = requestResetPasswordPresenter;
        this.verifyOtpPresenter = verifyOtpPresenter;
        this.resetPasswordPresenter = resetPasswordPresenter;
    }

    /**
     * UC1: Request reset password - sends OTP to email
     */
    @PostMapping("/forgot-password")
    public RequestResetPasswordViewModel requestResetPassword(@RequestBody ForgotPasswordRequest request) {
        requestResetPasswordInteractor.execute(new RequestResetPasswordInputData(request.email()));
        return requestResetPasswordPresenter.getViewModel();
    }

    /**
     * UC2: Verify OTP
     */
    @PostMapping("/verify-otp")
    public VerifyOtpViewModel verifyOtp(@RequestBody VerifyOtpRequest request) {
        verifyOtpInteractor.execute(new VerifyOtpInputData(request.email(), request.otp()));
        return verifyOtpPresenter.getViewModel();
    }

    /**
     * UC3: Reset password (requires OTP verification first)
     */
    @PostMapping("/reset-password")
    public ResetPasswordViewModel resetPassword(@RequestBody ResetPasswordRequest request) {
        resetPasswordInteractor.execute(new ResetPasswordInputData(request.email(), request.newPassword()));
        return resetPasswordPresenter.getViewModel();
    }
}

