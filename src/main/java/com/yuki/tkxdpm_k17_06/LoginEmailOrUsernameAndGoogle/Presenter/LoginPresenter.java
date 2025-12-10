package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Presenter;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.LoginOutputBoundary;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.LoginOutputData;
import lombok.Getter;


@Getter
public class LoginPresenter implements LoginOutputBoundary {

    private LoginViewModel viewModel;

    @Override
    public void present(LoginOutputData outputData) {
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
