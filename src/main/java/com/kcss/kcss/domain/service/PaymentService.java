package com.kcss.kcss.domain.service;

import com.kcss.kcss.domain.model.payment.Payment;

public interface PaymentService {
    void pay(Payment payment);
    Payment paymentOf(Long id);
}
