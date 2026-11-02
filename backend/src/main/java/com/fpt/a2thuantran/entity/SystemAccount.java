package com.fpt.a2thuantran.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This class represents one row in the SystemAccount table.
 * An account can be an Admin (role = 1) or a Staff (role = 2).
 */
@Entity
@Table(name = "SystemAccount")
public class SystemAccount {

    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_STAFF = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private Integer accountId;

    @Column(name = "AccountName", nullable = false, length = 100)
    private String accountName;

    @Column(name = "AccountEmail", nullable = false, unique = true, length = 100)
    private String accountEmail;

    @Column(name = "AccountRole", nullable = false)
    private Integer accountRole;

    @Column(name = "AccountPassword", nullable = false, length = 255)
    private String accountPassword;

    public SystemAccount() {
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

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

    public Integer getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(Integer accountRole) {
        this.accountRole = accountRole;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }
}
