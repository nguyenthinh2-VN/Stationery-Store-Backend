package com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter;

import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.ViewModel.ResetPasswordViewModel;
import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.Usecase.ResetPasswordOutputBoundary;
import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.ResetPasswordOutputData;

/**
 * Presenter for UC3 - Reset Password.
 * Implements OutputBoundary, transforms output to ViewModel.
 */
public class ResetPasswordPresenter implements ResetPasswordOutputBoundary {

    private ResetPasswordViewModel viewModel;

    @Override
    public void present(ResetPasswordOutputData output) {
        viewModel = new ResetPasswordViewModel(output.success(), output.message());
    }

    public ResetPasswordViewModel getViewModel() {
        return viewModel;
    }
}

