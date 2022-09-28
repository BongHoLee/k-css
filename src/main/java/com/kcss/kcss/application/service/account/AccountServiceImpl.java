package com.kcss.kcss.application.service.account;

import com.kcss.kcss.application.error.ApplicationErrorCode;
import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.repository.account.AccountRepository;
import com.kcss.kcss.domain.service.account.AccountService;
import com.kcss.kcss.global.error.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account register(Account account) {
        return repository.save(account);
    }

    @Override
    public Account findAccountOf(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ApplicationErrorCode.NO_CONTENT));
    }
}
