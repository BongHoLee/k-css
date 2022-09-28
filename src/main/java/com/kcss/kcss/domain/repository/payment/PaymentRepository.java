package com.kcss.kcss.domain.repository.payment;

import com.kcss.kcss.domain.model.payment.Payment;
import java.util.Optional;

public interface PaymentRepository {
    void save(Payment payment);
    Optional<Payment> findById(Long id);
}
