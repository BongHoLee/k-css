package com.kcss.kcss.application.service;

import com.kcss.kcss.application.error.ApplicationErrorCode;
import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.repository.payment.PaymentRepository;
import com.kcss.kcss.domain.service.PaymentService;
import com.kcss.kcss.global.error.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void pay(Payment payment) {
        repository.save(payment);
    }

    @Override
    public Payment paymentOf(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ApplicationErrorCode.NO_CONTENT));
    }
}
