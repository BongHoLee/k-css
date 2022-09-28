package com.kcss.kcss.domain.repository.account;

import com.kcss.kcss.domain.model.account.Account;
import java.util.Optional;

public interface AccountRepository {
    void save(Account account);
    Optional<Account> findById(Long id);
}
