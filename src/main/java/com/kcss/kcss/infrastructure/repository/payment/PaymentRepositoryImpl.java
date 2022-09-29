package com.kcss.kcss.infrastructure.repository.payment;

import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.repository.payment.PaymentRepository;
import com.kcss.kcss.global.error.BusinessException;
import com.kcss.kcss.infrastructure.entity.error.InfrastructureErrorCode;
import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository jpaRepository;

    public PaymentRepositoryImpl(JpaPaymentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Payment save(Payment payment) {
        if (jpaRepository.findById(payment.getId()).isPresent()) {
            log.error("duplicate id exception occur");
            throw new BusinessException(InfrastructureErrorCode.DUPLICATE_ID);
        }
        return jpaRepository.save(PaymentEntity.from(payment)).convert();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return jpaRepository.findById(id).map(PaymentEntity::convert);
    }
}
