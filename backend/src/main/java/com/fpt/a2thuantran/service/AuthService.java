package com.fpt.a2thuantran.service;

import com.fpt.a2thuantran.dto.LoginRequest;
import com.fpt.a2thuantran.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
