package com.yuki.tkxdpm_k17_06.ChangePassword;

public class ChangePasswordPresenter implements ChangePasswordOutputBoundary {

    private ChangePasswordViewModel vm;

    @Override
    public void present(ChangePasswordOutputData output) {
        vm = new ChangePasswordViewModel(output.success(), output.message());
    }

    public ChangePasswordViewModel getViewModel() {
        return vm;
    }
}
