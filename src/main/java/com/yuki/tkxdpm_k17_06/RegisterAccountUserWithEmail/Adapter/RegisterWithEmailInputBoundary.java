package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Adapter;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.RegisterWithEmailInputData;
import org.springframework.stereotype.Component;

@Component
public interface RegisterWithEmailInputBoundary {
    void execute(RegisterWithEmailInputData inputData);
}
