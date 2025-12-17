package com.yuki.tkxdpm_k17_06.ForgotPassword;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.OtpRepository;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.VerifyOtpInputData;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.VerifyOtpUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC2 - Xác thực mã OTP
 * 
 * Business Rules:
 * - BR5: OTP chỉ được xác thực 1 lần
 * - BR6: OTP có số lần nhập tối đa (vd: 5 lần) - chưa implement
 * - BR7: OTP hết hạn → tự vô hiệu
 * 
 * Flow of event:
 * 1. User nhập email + OTP
 * 2. Hệ thống tìm OTP theo email
 * 3. Hệ thống kiểm tra:
 * 3.1 OTP có tồn tại không?
 * 3.2 OTP còn hạn không?
 * 3.3 OTP có khớp không?
 * 4. Phản hồi cho User: OTP Hợp lệ / Không hợp lệ
 * 5. User thấy thông báo Hợp lệ / Không hợp lệ
 * 
 * Test Cases:
 * - KB1: OTP hợp lệ → Thành công (flow: 1,2,3.1,3.2,3.3,4,5)
 * - KB2: OTP không hợp lệ (không tồn tại/hết hạn/sai code) → Lỗi
 */
class VerifyOtpUseCaseTest {

    private OtpRepository mockOtpRepo;
    private VerifyOtpUseCase useCase;

    // Để track saved OTPs
    private List<Otp> savedOtps;
    private Otp validOtp;
    private Otp expiredOtp;

    @BeforeEach
    void setUp() {
        savedOtps = new ArrayList<>();

        // Tạo valid OTP
        validOtp = new Otp();
        validOtp.setId(1L);
        validOtp.setEmail("user@example.com");
        validOtp.setCode("123456");
        validOtp.setExpiredAt(LocalDateTime.now().plusMinutes(5)); // Còn hạn
        validOtp.setUsed(false);

        // Tạo expired OTP
        expiredOtp = new Otp();
        expiredOtp.setId(2L);
        expiredOtp.setEmail("expired@example.com");
        expiredOtp.setCode("654321");
        expiredOtp.setExpiredAt(LocalDateTime.now().minusMinutes(1)); // Đã hết hạn
        expiredOtp.setUsed(false);

        // Mock OtpRepository
        mockOtpRepo = new OtpRepository() {
            @Override
            public void save(Otp otp) {
                savedOtps.add(otp);
            }

            @Override
            public Otp findValidOtp(String email, String code) {
                if (email.equals("user@example.com") && code.equals("123456")) {
                    return validOtp;
                }
                if (email.equals("expired@example.com") && code.equals("654321")) {
                    return expiredOtp;
                }
                return null; // OTP không tồn tại
            }

            @Override
            public void invalidateAllByEmail(String email) {
            }

            @Override
            public Otp findUsedOtpByEmail(String email) {
                return null;
            }
        };

        useCase = new VerifyOtpUseCase(mockOtpRepo);
    }

    @Test
    @DisplayName("KB1: OTP hợp lệ - Xác thực thành công")
    void testVerifyOtp_ValidOtp_ShouldMarkAsUsed() {
        // Given: OTP hợp lệ (tồn tại, còn hạn, đúng code)
        VerifyOtpInputData input = new VerifyOtpInputData("user@example.com", "123456");

        // When: Thực hiện verify OTP
        assertDoesNotThrow(() -> useCase.execute(input));

        // Then:
        // 1. OTP phải được đánh dấu là đã sử dụng (BR5: OTP chỉ được xác thực 1 lần)
        assertTrue(validOtp.isUsed(), "OTP phải được đánh dấu là đã sử dụng");

        // 2. OTP phải được save
        assertEquals(1, savedOtps.size(), "OTP phải được save sau khi mark used");
    }

    @Test
    @DisplayName("KB2: OTP không tồn tại - Xác thực thất bại")
    void testVerifyOtp_OtpNotExist_ShouldThrowException() {
        // Given: Email/OTP không tồn tại trong hệ thống
        VerifyOtpInputData input = new VerifyOtpInputData("notexist@example.com", "000000");

        // When & Then: Phải throw exception
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> useCase.execute(input));

        assertEquals("OTP không hợp lệ hoặc đã hết hạn", exception.getMessage());
    }

    @Test
    @DisplayName("KB2: OTP đã hết hạn - Xác thực thất bại (BR7)")
    void testVerifyOtp_ExpiredOtp_ShouldThrowException() {
        // Given: OTP đã hết hạn
        VerifyOtpInputData input = new VerifyOtpInputData("expired@example.com", "654321");

        // When & Then: Phải throw exception vì OTP hết hạn
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> useCase.execute(input));

        assertEquals("OTP không hợp lệ hoặc đã hết hạn", exception.getMessage());
    }

    @Test
    @DisplayName("KB2: OTP sai code - Xác thực thất bại")
    void testVerifyOtp_WrongCode_ShouldThrowException() {
        // Given: Email đúng nhưng OTP code sai
        VerifyOtpInputData input = new VerifyOtpInputData("user@example.com", "999999");

        // When & Then: Phải throw exception
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> useCase.execute(input));

        assertEquals("OTP không hợp lệ hoặc đã hết hạn", exception.getMessage());
    }
}
