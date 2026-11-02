package com.fpt.a2thuantran.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.a2thuantran.dto.AccountResponse;
import com.fpt.a2thuantran.dto.ProfileUpdateRequest;
import com.fpt.a2thuantran.security.AuthUtil;
import com.fpt.a2thuantran.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * This controller lets a logged-in Staff view and edit their own profile.
 * The account id always comes from the login token, never from the request body,
 * so a staff can never edit someone else's profile.
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final AccountService accountService;

    public ProfileController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<AccountResponse> getMyProfile(HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        Integer currentAccountId = AuthUtil.getCurrentAccountId(httpRequest);
        return ResponseEntity.ok(accountService.getProfile(currentAccountId));
    }

    @PutMapping
    public ResponseEntity<AccountResponse> updateMyProfile(@Valid @RequestBody ProfileUpdateRequest request,
                                                             HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        Integer currentAccountId = AuthUtil.getCurrentAccountId(httpRequest);
        return ResponseEntity.ok(accountService.updateProfile(currentAccountId, request));
    }
}
