package com.kcss.kcss.infrastructure.repository.payment;

import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.repository.payment.PaymentRepository;
import com.kcss.kcss.global.error.BusinessException;
import com.kcss.kcss.infrastructure.entity.error.InfrastructureErrorCode;
import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import com.kcss.kcss.infrastructure.repository.account.JpaAccountRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository paymentRepository;
    private final JpaAccountRepository accountRepository;

    public PaymentRepositoryImpl(JpaPaymentRepository paymentRepository, JpaAccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Payment save(Payment payment) {
        if (paymentRepository.findById(payment.getId()).isPresent()) {
            log.error("duplicate id exception occur");
            throw new BusinessException(InfrastructureErrorCode.DUPLICATE_ID);
        }

        if (accountRepository.findById(payment.getAccount().getId()).isEmpty()) {
            log.error("not valid account id exception occur");
            throw new BusinessException("Account ID is not exists", InfrastructureErrorCode.NOT_VALID_ID);
        }
        return paymentRepository.save(PaymentEntity.from(payment)).convert();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id).map(PaymentEntity::convert);
    }
}
