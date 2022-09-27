package com.kcss.kcss.infrastructure.repository.account;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.repository.account.AccountRepository;
import com.kcss.kcss.infrastructure.entity.account.AccountEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final JpaAccountRepository jpaRepository;


    public AccountRepositoryImpl(JpaAccountRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Account save(Account account) {
        return jpaRepository.save(AccountEntity.from(account)).convert();
    }

    @Override
    public Optional<Account> findById(Long id) {
        Optional<AccountEntity> findEntity = jpaRepository.findById(id);
        return findEntity.map(AccountEntity::convert);
    }
}
