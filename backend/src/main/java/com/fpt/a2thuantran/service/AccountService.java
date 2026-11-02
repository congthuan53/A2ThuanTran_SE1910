package com.fpt.a2thuantran.service;

import java.util.List;

import com.fpt.a2thuantran.dto.AccountRequest;
import com.fpt.a2thuantran.dto.AccountResponse;
import com.fpt.a2thuantran.dto.ProfileUpdateRequest;

public interface AccountService {

    // ----- used by Admin to manage all accounts -----
    List<AccountResponse> getAllAccounts(String keyword);

    AccountResponse getAccountById(Integer accountId);

    AccountResponse createAccount(AccountRequest request);

    AccountResponse updateAccount(Integer accountId, AccountRequest request);

    void deleteAccount(Integer accountId);

    // ----- used by Staff to manage their own profile -----
    AccountResponse getProfile(Integer accountId);

    AccountResponse updateProfile(Integer accountId, ProfileUpdateRequest request);
}
