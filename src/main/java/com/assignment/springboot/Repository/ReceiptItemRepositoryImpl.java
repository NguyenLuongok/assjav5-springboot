package com.assignment.springboot.Repository;

import com.assignment.springboot.Model.ReceiptItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ReceiptItemRepositoryImpl implements ReceiptItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ReceiptItem> findAll() {
        TypedQuery<ReceiptItem> typedQuery = entityManager.createQuery("select r from ReceiptItem  r",ReceiptItem.class);
        return typedQuery.getResultList();
    }

    @Override
    public ReceiptItem findById(Long id) {
        return entityManager.find(ReceiptItem.class,id);
    }

    @Override
    public void save(ReceiptItem model) {
        if(model != null){
            entityManager.merge(model);
        }
        else {
            entityManager.persist(model);
        }
    }

    @Override
    public List<ReceiptItem> saveAll(List<ReceiptItem> receiptItems) {
        for (ReceiptItem receipt : receiptItems){
            entityManager.persist(receipt);
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public List<ReceiptItem> fillAllByReceiptId(Long id) {
        TypedQuery<ReceiptItem> typedQuery = entityManager.createQuery("select ri from ReceiptItem  ri where ri.receipt.receiptId =:receiptId order by ri.receiptItemId asc ",ReceiptItem.class);
        typedQuery.setParameter("receiptId",id);
        return typedQuery.getResultList();
    }
}
