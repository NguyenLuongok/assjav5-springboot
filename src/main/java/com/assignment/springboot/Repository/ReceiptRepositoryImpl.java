package com.assignment.springboot.Repository;

import com.assignment.springboot.Model.Receipt;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ReceiptRepositoryImpl implements ReceiptRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Receipt> findAll() {
        TypedQuery<Receipt> typedQuery = entityManager.createQuery("select r from Receipt  r",Receipt.class);
        return typedQuery.getResultList();
    }

    @Override
    public Receipt findById(Long id) {
        return entityManager.find(Receipt.class,id);
    }

    @Override
    public void save(Receipt model) {
        Session session = entityManager.unwrap(Session.class);
        session.save(model);
    }

    @Override
    public void remove(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public List<Receipt> fillAllReceiptByMemberId(Long id) {
        TypedQuery<Receipt> typedQuery = entityManager.createQuery("select r from Receipt r where r.member.memberId =:memberId order by r.receiptDate desc ", Receipt.class);
        typedQuery.setParameter("memberId", id);
        return typedQuery.getResultList();
    }
}
