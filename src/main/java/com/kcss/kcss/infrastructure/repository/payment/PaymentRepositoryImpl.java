package com.kcss.kcss.infrastructure.repository.payment;

import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.repository.payment.PaymentRepository;
import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository jpaRepository;

    public PaymentRepositoryImpl(JpaPaymentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Payment save(Payment payment) {
        return jpaRepository.save(PaymentEntity.from(payment)).convert();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return jpaRepository.findById(id).map(PaymentEntity::convert);
    }
}
