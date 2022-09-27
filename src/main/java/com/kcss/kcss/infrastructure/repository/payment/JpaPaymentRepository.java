package com.kcss.kcss.infrastructure.repository.payment;

import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
