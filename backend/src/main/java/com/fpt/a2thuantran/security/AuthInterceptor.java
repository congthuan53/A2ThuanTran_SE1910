package com.fpt.a2thuantran.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fpt.a2thuantran.exception.UnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This interceptor runs before every protected API request.
 * It reads the "Authorization: Bearer <token>" header, checks the token,
 * then stores the account id and role into the request so the controller can read them.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String ACCOUNT_ID_ATTRIBUTE = "accountId";
    public static final String ACCOUNT_ROLE_ATTRIBUTE = "accountRole";

    private final JwtUtil jwtUtil;

    public AuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Ban chua dang nhap hoac token bi thieu");
        }

        String token = authorizationHeader.substring("Bearer ".length());

        if (!jwtUtil.isTokenValid(token)) {
            throw new UnauthorizedException("Token khong hop le hoac da het han");
        }

        Integer accountId = jwtUtil.getAccountIdFromToken(token);
        Integer accountRole = jwtUtil.getAccountRoleFromToken(token);

        request.setAttribute(ACCOUNT_ID_ATTRIBUTE, accountId);
        request.setAttribute(ACCOUNT_ROLE_ATTRIBUTE, accountRole);

        return true;
    }
}
