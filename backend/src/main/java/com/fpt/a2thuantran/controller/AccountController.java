package com.fpt.a2thuantran.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.a2thuantran.dto.AccountRequest;
import com.fpt.a2thuantran.dto.AccountResponse;
import com.fpt.a2thuantran.security.AuthUtil;
import com.fpt.a2thuantran.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * Only an Admin account is allowed to call these APIs.
 * Every method starts by calling AuthUtil.requireAdminRole(httpRequest) to check that.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts(
            @RequestParam(required = false) String keyword,
            HttpServletRequest httpRequest) {
        AuthUtil.requireAdminRole(httpRequest);
        return ResponseEntity.ok(accountService.getAllAccounts(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable("id") Integer accountId,
                                                            HttpServletRequest httpRequest) {
        AuthUtil.requireAdminRole(httpRequest);
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request,
                                                           HttpServletRequest httpRequest) {
        AuthUtil.requireAdminRole(httpRequest);
        AccountResponse createdAccount = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable("id") Integer accountId,
                                                           @Valid @RequestBody AccountRequest request,
                                                           HttpServletRequest httpRequest) {
        AuthUtil.requireAdminRole(httpRequest);
        return ResponseEntity.ok(accountService.updateAccount(accountId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Integer accountId,
                                               HttpServletRequest httpRequest) {
        AuthUtil.requireAdminRole(httpRequest);
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
