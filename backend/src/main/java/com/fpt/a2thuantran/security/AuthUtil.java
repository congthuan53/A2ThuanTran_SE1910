package com.fpt.a2thuantran.security;

import com.fpt.a2thuantran.entity.SystemAccount;
import com.fpt.a2thuantran.exception.ForbiddenException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Small helper class used inside controllers to read the current logged-in
 * account (set earlier by AuthInterceptor) and check its role.
 */
public class AuthUtil {

    private AuthUtil() {
    }

    public static Integer getCurrentAccountId(HttpServletRequest request) {
        return (Integer) request.getAttribute(AuthInterceptor.ACCOUNT_ID_ATTRIBUTE);
    }

    public static Integer getCurrentAccountRole(HttpServletRequest request) {
        return (Integer) request.getAttribute(AuthInterceptor.ACCOUNT_ROLE_ATTRIBUTE);
    }

    // call this at the start of a controller method that only Admin is allowed to use
    public static void requireAdminRole(HttpServletRequest request) {
        Integer currentRole = getCurrentAccountRole(request);
        if (currentRole == null || currentRole != SystemAccount.ROLE_ADMIN) {
            throw new ForbiddenException("Chi Admin moi duoc phep thuc hien chuc nang nay");
        }
    }

    // call this at the start of a controller method that only Staff is allowed to use
    public static void requireStaffRole(HttpServletRequest request) {
        Integer currentRole = getCurrentAccountRole(request);
        if (currentRole == null || currentRole != SystemAccount.ROLE_STAFF) {
            throw new ForbiddenException("Chi Staff moi duoc phep thuc hien chuc nang nay");
        }
    }
}
