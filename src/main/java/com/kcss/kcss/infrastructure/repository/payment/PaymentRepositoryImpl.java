package com.kcss.kcss.infrastructure.repository.payment;

import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.repository.payment.PaymentRepository;
import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JpaPaymentRepository jpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public PaymentRepositoryImpl(JpaPaymentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Payment payment) {
        entityManager.persist(PaymentEntity.from(payment));
    }

    @Override
    public Optional<Payment> findById(Long id) {
        Optional<PaymentEntity> findEntity = jpaRepository.findById(id);
        return findEntity.map(PaymentEntity::convert);
    }
}
