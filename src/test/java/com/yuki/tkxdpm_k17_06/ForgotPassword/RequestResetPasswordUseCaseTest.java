package com.yuki.tkxdpm_k17_06.ForgotPassword;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Role;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Policy.OtpGeneratorPolicy;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.Usecase.RequestResetPasswordControl;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.RequestResetPasswordInputData;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.Usecase.RequestResetPasswordOutputBoundary;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.RequestResetPasswordOutputData;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Policy.ResendEmailService;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.OtpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC1 - Gửi mã xác thực (OTP)
 * 
 * Test Cases:
 * - KB1: Email không tồn tại → Luôn trả success (security)
 * - KB2: Email hợp lệ → Gửi OTP thành công
 * - KB3: Full flow: invalidate OTP cũ, gen OTP mới, lưu, gửi email
 */
class RequestResetPasswordUseCaseTest {

    private UserRepository mockUserRepo;
    private OtpRepository mockOtpRepo;
    private OtpGeneratorPolicy mockOtpGenerator;
    private ResendEmailService mockEmailService;
    private RequestResetPasswordOutputBoundary mockPresenter;
    private RequestResetPasswordControl useCase;

    // Track operations
    private List<String> invalidatedEmails;
    private List<Otp> savedOtps;
    private List<String> sentEmails;
    private List<String> sentCodes;
    private RequestResetPasswordOutputData lastOutput;

    @BeforeEach
    void setUp() {
        invalidatedEmails = new ArrayList<>();
        savedOtps = new ArrayList<>();
        sentEmails = new ArrayList<>();
        sentCodes = new ArrayList<>();

        // Mock UserRepository - chỉ có "existing@example.com" tồn tại
        mockUserRepo = new UserRepository() {
            @Override
            public UserAccount save(UserAccount user) {
                return user;
            }

            @Override
            public UserAccount findByEmail(String email) {
                if ("existing@example.com".equals(email)) {
                    // Use the constructor: UserAccount(String email, String name)
                    return new UserAccount(email, "Test User");
                }
                return null;
            }

            @Override
            public UserAccount findByUsername(String username) {
                return null;
            }

            @Override
            public UserAccount findById(Long id) {
                return null;
            }
        };

        // Mock OtpRepository
        mockOtpRepo = new OtpRepository() {
            @Override
            public void save(Otp otp) {
                savedOtps.add(otp);
            }

            @Override
            public Otp findValidOtp(String email, String code) {
                return null;
            }

            @Override
            public void invalidateAllByEmail(String email) {
                invalidatedEmails.add(email);
            }

            @Override
            public Otp findUsedOtpByEmail(String email) {
                return null;
            }
        };

        // Mock OtpGeneratorPolicy
        mockOtpGenerator = new OtpGeneratorPolicy() {
            @Override
            public Otp generate(String email) {
                Otp otp = new Otp();
                otp.setEmail(email);
                otp.setCode("123456");
                otp.setExpiredAt(LocalDateTime.now().plusMinutes(5));
                otp.setUsed(false);
                return otp;
            }
        };

        // Mock ResendEmailService
        mockEmailService = new ResendEmailService() {
            @Override
            public void sendOtp(String toEmail, String otpCode) {
                sentEmails.add(toEmail);
                sentCodes.add(otpCode);
            }
        };

        // Mock Presenter - use anonymous class instead of lambda
        mockPresenter = new RequestResetPasswordOutputBoundary() {
            @Override
            public void present(RequestResetPasswordOutputData output) {
                lastOutput = output;
            }
        };

        useCase = new RequestResetPasswordControl(
                mockUserRepo, mockOtpRepo, mockOtpGenerator, mockEmailService, mockPresenter);
    }

    @Test
    @DisplayName("KB1: Email không tồn tại - Vẫn trả success (security)")
    void testRequestResetPassword_EmailNotExist_ShouldReturnSuccessButNotSendOtp() {
        // Given: Email không tồn tại
        String email = "notexist@example.com";
        RequestResetPasswordInputData input = new RequestResetPasswordInputData(email);

        // When
        useCase.execute(input);

        // Then: Trả success nhưng không gửi OTP (security)
        assertTrue(lastOutput.success(), "Phải trả success dù email không tồn tại");
        assertEquals(0, sentEmails.size(), "Không được gửi OTP nếu email không tồn tại");
    }

    @Test
    @DisplayName("KB2: Email hợp lệ - Gửi OTP thành công")
    void testRequestResetPassword_ValidEmail_ShouldSendOtp() {
        // Given: Email hợp lệ (tồn tại trong hệ thống)
        String email = "existing@example.com";
        RequestResetPasswordInputData input = new RequestResetPasswordInputData(email);

        // When
        useCase.execute(input);

        // Then: OTP phải được gửi qua email
        assertTrue(lastOutput.success());
        assertEquals(1, sentEmails.size(), "Email phải được gửi");
        assertEquals(email, sentEmails.get(0), "Email nhận phải đúng");
        assertEquals("123456", sentCodes.get(0), "OTP code phải được gửi");
    }

    @Test
    @DisplayName("KB3: Full flow - Invalidate OTP cũ, Gen OTP mới, Lưu, Gửi email")
    void testRequestResetPassword_FullFlow_ShouldInvalidateOldAndSendNew() {
        // Given: Email đã có OTP cũ
        String email = "existing@example.com";
        RequestResetPasswordInputData input = new RequestResetPasswordInputData(email);

        // When
        useCase.execute(input);

        // Then:
        // 1. OTP cũ phải được invalidate
        assertEquals(1, invalidatedEmails.size());
        assertEquals(email, invalidatedEmails.get(0));

        // 2. OTP mới phải được lưu
        assertEquals(1, savedOtps.size());
        Otp savedOtp = savedOtps.get(0);
        assertEquals(email, savedOtp.getEmail());
        assertFalse(savedOtp.isUsed());

        // 3. Email phải được gửi
        assertEquals(1, sentEmails.size());
    }
}
