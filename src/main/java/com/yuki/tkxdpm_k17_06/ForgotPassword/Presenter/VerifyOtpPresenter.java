package com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter;

import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.ViewModel.VerifyOtpViewModel;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.VerifyOtpOutputBoundary;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.VerifyOtpOutputData;
import lombok.Getter;

/**
 * Presenter for UC2 - Verify OTP.
 * Implements OutputBoundary, transforms output to ViewModel.
 */
@Getter
public class VerifyOtpPresenter implements VerifyOtpOutputBoundary {

    private VerifyOtpViewModel viewModel;

    @Override
    public void present(VerifyOtpOutputData output) {
        viewModel = new VerifyOtpViewModel(output.success(), output.message());
    }

}


