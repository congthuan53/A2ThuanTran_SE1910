package com.fpt.a2thuantran.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data the client sends to create or update an account.
 * When updating, "accountPassword" can be left empty to keep the old password.
 */
public class AccountRequest {

    @NotBlank(message = "Ten tai khoan khong duoc de trong")
    private String accountName;

    @NotBlank(message = "Email khong duoc de trong")
    @Email(message = "Email khong dung dinh dang")
    private String accountEmail;

    // only required when creating a new account, checked manually in the service
    private String accountPassword;

    @NotNull(message = "Vai tro khong duoc de trong")
    private Integer accountRole;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public Integer getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(Integer accountRole) {
        this.accountRole = accountRole;
    }
}
