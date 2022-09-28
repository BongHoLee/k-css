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

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Payment payment) {
        entityManager.persist(PaymentEntity.from(payment));
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return Optional.of(entityManager.find(PaymentEntity.class, id)).map(PaymentEntity::convert);

    }
}
