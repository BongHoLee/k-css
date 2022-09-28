package com.kcss.kcss.domain.service.account;

import com.kcss.kcss.domain.model.account.Account;

public interface AccountService {
    Account register(Account account);
    Account findAccountOf(Long id);
}
