package com.fpt.a2thuantran.dto;

/**
 * Data the server sends back after a successful login.
 * The client should save the token and send it in the "Authorization" header
 * of every request that needs login, in the form: "Bearer <token>".
 */
public class LoginResponse {

    private String token;
    private Integer accountId;
    private String accountName;
    private String accountEmail;
    private Integer accountRole;

    public LoginResponse(String token, Integer accountId, String accountName, String accountEmail, Integer accountRole) {
        this.token = token;
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountEmail = accountEmail;
        this.accountRole = accountRole;
    }

    public String getToken() {
        return token;
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
