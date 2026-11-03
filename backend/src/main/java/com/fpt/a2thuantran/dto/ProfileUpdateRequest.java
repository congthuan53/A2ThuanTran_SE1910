package com.fpt.a2thuantran.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data a staff sends to update their own profile.
 * "accountPassword" can be left empty if the staff does not want to change the password.
 */
public class ProfileUpdateRequest {

    @NotBlank(message = "Tên tài khoản không được để trống")
    private String accountName;

    private String accountPassword;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }
}
