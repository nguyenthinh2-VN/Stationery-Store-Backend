package com.yuki.tkxdpm_k17_06.ForgotPassword.Infractructure;

import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.OtpJpaRepository;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.OtpRepository;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OtpRepositoryImpl implements OtpRepository {

    private final OtpJpaRepository jpa;

    public OtpRepositoryImpl(OtpJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void save(Otp otp) {
        OtpEntity e = new OtpEntity();
        e.setEmail(otp.getEmail());
        e.setCode(otp.getCode());
        e.setExpiredAt(otp.getExpiredAt());
        e.setUsed(otp.isUsed());
        jpa.save(e);
    }

    @Override
    public Otp findValidOtp(String email, String code) {
        OtpEntity e = jpa.findByEmailAndCodeAndUsedFalse(email, code);
        if (e == null)
            return null;

        Otp otp = new Otp();
        otp.setId(e.getId());
        otp.setEmail(e.getEmail());
        otp.setCode(e.getCode());
        otp.setExpiredAt(e.getExpiredAt());
        otp.setUsed(e.isUsed());
        return otp;
    }

    @Override
    @Transactional
    public void invalidateAllByEmail(String email) {
        jpa.invalidateAllByEmail(email);
    }

    @Override
    public Otp findUsedOtpByEmail(String email) {
        OtpEntity e = jpa.findTopByEmailAndUsedTrueOrderByIdDesc(email);
        if (e == null)
            return null;

        Otp otp = new Otp();
        otp.setId(e.getId());
        otp.setEmail(e.getEmail());
        otp.setCode(e.getCode());
        otp.setExpiredAt(e.getExpiredAt());
        otp.setUsed(e.isUsed());
        return otp;
    }
}
