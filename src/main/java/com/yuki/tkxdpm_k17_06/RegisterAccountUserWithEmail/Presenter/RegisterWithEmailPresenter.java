package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Presenter;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Adapter.RegisterWithEmailOutputBoundary;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.RegisterWithEmailOutputData;
import org.springframework.stereotype.Component;

@Component
public class RegisterWithEmailPresenter implements RegisterWithEmailOutputBoundary {
    private RegisterWithEmailResponse response;

    @Override
    public void present(RegisterWithEmailOutputData outputData) {
        this.response = new RegisterWithEmailResponse(
                outputData.success(),
                outputData.message()
        );
    }

    public RegisterWithEmailResponse getResponse() {
        return response;
    }
}
