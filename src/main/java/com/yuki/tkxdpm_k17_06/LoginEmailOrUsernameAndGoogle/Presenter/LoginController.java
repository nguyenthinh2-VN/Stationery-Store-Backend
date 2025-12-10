package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Presenter;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.LoginInputBoundary;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.LoginInputData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginInputBoundary interactor;
    private final LoginPresenter presenter;

    public LoginController(LoginInputBoundary interactor, LoginPresenter presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    @PostMapping("/login")
    public LoginViewModel login(@RequestBody LoginRequest req) {

        LoginInputData input = new LoginInputData(
                req.identifier(),
                req.password()
        );

        interactor.execute(input);

        return presenter.getViewModel();
    }

    @PostMapping("/login/google")
    public LoginViewModel loginWithGoogle(@RequestBody GoogleLoginRequest req) {

        LoginInputData input = new LoginInputData(
                req.googleToken()
        );

        interactor.execute(input);

        return presenter.getViewModel();
    }

}
