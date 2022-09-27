package com.kcss.kcss.infrastructure.repository;

import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>{
}
