package com.yuki.tkxdpm_k17_06.ForgotPassword.Infractructure;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Policy.ResendEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResendEmailServiceImpl implements ResendEmailService {

    private final Resend resend;
    private final String fromEmail;

        public ResendEmailServiceImpl(
            @Value("${resend.api-key}") String apiKey,
            @Value("${resend.from-email}") String fromEmail) {
        this.resend = new Resend(apiKey);
        this.fromEmail = fromEmail;
    }

    @Override
    public void sendOtp(String toEmail, String otpCode) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(toEmail)
                .subject("Mã OTP đặt lại mật khẩu")
                .text("Mã OTP của bạn là: " + otpCode + "\n\nMã này sẽ hết hạn sau 5 phút.")
                .build();

        try {
            CreateEmailResponse response = resend.emails().send(params);
            System.out.println("Email sent with ID: " + response.getId());
        } catch (ResendException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
}
