package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Presenter;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Adapter.RegisterWithEmailInputBoundary;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.RegisterWithEmailInputData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class RegisterWithEmailController {

    private final RegisterWithEmailInputBoundary interactor;
    private final RegisterWithEmailPresenter presenter;

    public RegisterWithEmailController(RegisterWithEmailInputBoundary interactor,
                                       RegisterWithEmailPresenter presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    @PostMapping("/register")
    public RegisterWithEmailResponse register(@RequestBody RegisterRequest req) {

        RegisterWithEmailInputData input = new RegisterWithEmailInputData(
                req.name(),
                req.username(),
                req.email(),
                req.password()
        );

        interactor.execute(input);

        // Controller chỉ return Response (không return OutputData)
        return presenter.getResponse();
    }
}
