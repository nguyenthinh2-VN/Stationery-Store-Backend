package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase;

import java.util.Map;

public class LoginControl implements LoginInputBoundary{
    private final Map<LoginMethod, AuthPolicy> policies;
    private final LoginOutputBoundary presenter;

    public LoginControl(Map<LoginMethod, AuthPolicy> policies,
                        LoginOutputBoundary presenter) {
        this.policies = policies;
        this.presenter = presenter;
    }

    @Override
    public void execute(LoginInputData input) {

        AuthPolicy policy = policies.get(input.getMethod());
        if (policy == null) {
            presenter.present(
                    new LoginOutputData(false, "Phương thức không hỗ trợ", null)
            );
            return;
        }

        AuthResult result = policy.authenticate(input);

        presenter.present(
                new LoginOutputData(result.success, result.message, result.token)
        );
    }
}
