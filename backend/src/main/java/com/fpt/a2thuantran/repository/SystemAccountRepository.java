package com.fpt.a2thuantran.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpt.a2thuantran.entity.SystemAccount;

/**
 * Repository layer for SystemAccount.
 * Spring Data JPA will generate the implementation of this interface automatically.
 */
public interface SystemAccountRepository extends JpaRepository<SystemAccount, Integer> {

    Optional<SystemAccount> findByAccountEmail(String accountEmail);

    boolean existsByAccountEmail(String accountEmail);

    // used when searching accounts by name or email on the admin screen
    List<SystemAccount> findByAccountNameContainingIgnoreCaseOrAccountEmailContainingIgnoreCase(
            String name, String email);
}
