package com.fpt.a2thuantran.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fpt.a2thuantran.dto.AccountRequest;
import com.fpt.a2thuantran.dto.AccountResponse;
import com.fpt.a2thuantran.dto.ProfileUpdateRequest;
import com.fpt.a2thuantran.entity.SystemAccount;
import com.fpt.a2thuantran.exception.BadRequestException;
import com.fpt.a2thuantran.exception.ResourceNotFoundException;
import com.fpt.a2thuantran.repository.NewsArticleRepository;
import com.fpt.a2thuantran.repository.SystemAccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final SystemAccountRepository accountRepository;
    private final NewsArticleRepository newsArticleRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(SystemAccountRepository accountRepository,
                               NewsArticleRepository newsArticleRepository,
                               PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.newsArticleRepository = newsArticleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<AccountResponse> getAllAccounts(String keyword) {
        List<SystemAccount> accounts;

        if (StringUtils.hasText(keyword)) {
            accounts = accountRepository
                    .findByAccountNameContainingIgnoreCaseOrAccountEmailContainingIgnoreCase(keyword, keyword);
        } else {
            accounts = accountRepository.findAll();
        }

        return accounts.stream()
                .map(this::toAccountResponse)
                .toList();
    }

    @Override
    public AccountResponse getAccountById(Integer accountId) {
        SystemAccount account = findAccountOrThrow(accountId);
        return toAccountResponse(account);
    }

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        if (accountRepository.existsByAccountEmail(request.getAccountEmail())) {
            throw new BadRequestException("Email nay da duoc su dung");
        }

        if (!StringUtils.hasText(request.getAccountPassword())) {
            throw new BadRequestException("Mat khau khong duoc de trong khi tao tai khoan moi");
        }

        SystemAccount account = new SystemAccount();
        account.setAccountName(request.getAccountName());
        account.setAccountEmail(request.getAccountEmail());
        account.setAccountRole(request.getAccountRole());
        account.setAccountPassword(passwordEncoder.encode(request.getAccountPassword()));

        SystemAccount savedAccount = accountRepository.save(account);
        return toAccountResponse(savedAccount);
    }

    @Override
    public AccountResponse updateAccount(Integer accountId, AccountRequest request) {
        SystemAccount account = findAccountOrThrow(accountId);

        account.setAccountName(request.getAccountName());
        account.setAccountEmail(request.getAccountEmail());
        account.setAccountRole(request.getAccountRole());

        // only change the password if the admin typed a new one
        if (StringUtils.hasText(request.getAccountPassword())) {
            account.setAccountPassword(passwordEncoder.encode(request.getAccountPassword()));
        }

        SystemAccount updatedAccount = accountRepository.save(account);
        return toAccountResponse(updatedAccount);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        SystemAccount account = findAccountOrThrow(accountId);

        long newsCreatedByThisAccount = newsArticleRepository.countByCreatedBy_AccountId(accountId);
        if (newsCreatedByThisAccount > 0) {
            throw new BadRequestException("Khong the xoa tai khoan nay vi da tao bai viet tin tuc");
        }

        accountRepository.delete(account);
    }

    @Override
    public AccountResponse getProfile(Integer accountId) {
        SystemAccount account = findAccountOrThrow(accountId);
        return toAccountResponse(account);
    }

    @Override
    public AccountResponse updateProfile(Integer accountId, ProfileUpdateRequest request) {
        SystemAccount account = findAccountOrThrow(accountId);

        account.setAccountName(request.getAccountName());

        if (StringUtils.hasText(request.getAccountPassword())) {
            account.setAccountPassword(passwordEncoder.encode(request.getAccountPassword()));
        }

        SystemAccount updatedAccount = accountRepository.save(account);
        return toAccountResponse(updatedAccount);
    }

    private SystemAccount findAccountOrThrow(Integer accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay tai khoan voi id = " + accountId));
    }

    private AccountResponse toAccountResponse(SystemAccount account) {
        return new AccountResponse(
                account.getAccountId(),
                account.getAccountName(),
                account.getAccountEmail(),
                account.getAccountRole()
        );
    }
}
