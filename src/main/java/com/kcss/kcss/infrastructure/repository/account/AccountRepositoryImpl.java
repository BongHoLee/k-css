package com.kcss.kcss.infrastructure.repository.account;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.repository.account.AccountRepository;
import com.kcss.kcss.global.error.BusinessException;
import com.kcss.kcss.infrastructure.entity.account.AccountEntity;
import com.kcss.kcss.infrastructure.entity.error.InfrastructureErrorCode;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository jpaRepository;

    public AccountRepositoryImpl(JpaAccountRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Account save(Account account) {
        if (jpaRepository.findById(account.getId()).isPresent()) {
            log.error("duplicate id exception occur");
            throw new BusinessException(InfrastructureErrorCode.DUPLICATE_ID);
        }
        return jpaRepository.save(AccountEntity.from(account)).convert();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return jpaRepository.findById(id).map(AccountEntity::convert);
    }
}
