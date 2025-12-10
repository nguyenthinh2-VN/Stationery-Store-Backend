package com.yuki.tkxdpm_k17_06.ChangePassword;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ChangePasswordController {

    private final ChangePasswordInputBoundary interactor;
    private final ChangePasswordPresenter presenter;

    public ChangePasswordController(
            ChangePasswordInputBoundary interactor,
            ChangePasswordPresenter presenter
    ) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    @PostMapping("/change-password")
    public ChangePasswordViewModel changePassword(
            @RequestBody ChangePasswordRequest req,
            @AuthenticationPrincipal JwtUserDetails user
    ) {
        ChangePasswordInputData input = new ChangePasswordInputData(
                user.getUserId(),
                req.oldPassword(),
                req.newPassword()
        );

        interactor.execute(input);
        return presenter.getViewModel();
    }
}


