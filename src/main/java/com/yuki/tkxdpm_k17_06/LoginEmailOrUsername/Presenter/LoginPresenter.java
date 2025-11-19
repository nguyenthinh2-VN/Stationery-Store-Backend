package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Presenter;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.LoginOutputBoundary;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.LoginOutputData;
import lombok.Getter;


@Getter
public class LoginPresenter implements LoginOutputBoundary {
    private LoginViewModel viewModel;

    @Override
    public void present(LoginOutputData outputData) {

        // Chuyển đổi OutputData → ViewModel
        this.viewModel = new LoginViewModel(
                outputData.isSuccess(),
                outputData.getMessage(),
                outputData.getToken()
        );
    }

    public LoginViewModel getViewModel() {
        return viewModel;
    }
}
