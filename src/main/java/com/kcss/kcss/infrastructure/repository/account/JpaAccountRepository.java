package com.kcss.kcss.infrastructure.repository.account;

import com.kcss.kcss.infrastructure.entity.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, Long> { }
