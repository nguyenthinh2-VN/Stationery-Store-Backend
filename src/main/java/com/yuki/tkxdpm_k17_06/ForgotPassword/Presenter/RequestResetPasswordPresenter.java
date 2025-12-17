package com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter;

import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.ViewModel.RequestResetPasswordViewModel;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.Usecase.RequestResetPasswordOutputBoundary;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.RequestResetPasswordOutputData;
import lombok.Getter;

/**
 * Presenter for UC1 - Request Reset Password.
 * Implements OutputBoundary, transforms output to ViewModel.
 */
@Getter
public class RequestResetPasswordPresenter implements RequestResetPasswordOutputBoundary {

    private RequestResetPasswordViewModel viewModel;

    @Override
    public void present(RequestResetPasswordOutputData output) {
        viewModel = new RequestResetPasswordViewModel(output.success(), output.message());
    }


}

