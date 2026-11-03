package com.fpt.a2thuantran.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fpt.a2thuantran.dto.LoginRequest;
import com.fpt.a2thuantran.dto.LoginResponse;
import com.fpt.a2thuantran.entity.SystemAccount;
import com.fpt.a2thuantran.exception.UnauthorizedException;
import com.fpt.a2thuantran.repository.SystemAccountRepository;
import com.fpt.a2thuantran.security.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    private final SystemAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(SystemAccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        SystemAccount account = accountRepository.findByAccountEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Email hoặc mật khẩu không đúng"));

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), account.getAccountPassword());
        if (!passwordMatches) {
            throw new UnauthorizedException("Email hoặc mật khẩu không đúng");
        }

        String token = jwtUtil.generateToken(account.getAccountId(), account.getAccountRole());

        return new LoginResponse(
                token,
                account.getAccountId(),
                account.getAccountName(),
                account.getAccountEmail(),
                account.getAccountRole()
        );
    }
}
