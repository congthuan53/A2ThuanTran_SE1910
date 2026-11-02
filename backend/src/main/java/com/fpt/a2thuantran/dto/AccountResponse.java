package com.fpt.a2thuantran.dto;

/**
 * Data the server sends back for an account.
 * Notice that there is no password field here, we never send the password back to the client.
 */
public class AccountResponse {

    private Integer accountId;
    private String accountName;
    private String accountEmail;
    private Integer accountRole;

    public AccountResponse(Integer accountId, String accountName, String accountEmail, Integer accountRole) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountEmail = accountEmail;
        this.accountRole = accountRole;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public Integer getAccountRole() {
        return accountRole;
    }
}
