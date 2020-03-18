package com.assignment.springboot.Repository;
import com.assignment.springboot.Model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ProductRepositoryImpl implements ProductRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll() {
        TypedQuery<Product> typedQuery = entityManager.createQuery("select p from Product p",Product.class);
        return typedQuery.getResultList();
    }

    @Override
    public Product findById(Long id) {
        return entityManager.find(Product.class,id);
    }

    @Override
    public void save(Product model) {
        if(model != null){
            entityManager.merge(model);
        }
        else {
            entityManager.persist(model);
        }
    }

    @Override
    public void remove(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public List<Product> getListByCategory(Long categoryId) {
        String hql = "select p from Product p where p.category.categoryId =: categoryId";
        TypedQuery<Product> typedQuery = entityManager.createQuery(hql,Product.class);
        typedQuery.setParameter("categoryId", categoryId);
        return typedQuery.getResultList();
    }

//    @Override
//    public List<Product> getListByCategoryAndLimit(Long categoryId, int limit) {
//        String hql = "select p from Product p where p.category.categoryId =: categoryId";
//        TypedQuery<Product> typedQuery = entityManager.createQuery(hql,Product.class);
//        typedQuery.setParameter("categoryId", categoryId);
//        typedQuery.setMaxResults(limit);
//        return typedQuery.getResultList();
//    }

    @Override
    public List<Product> getListFeatured(int limit) {
        String hql = "select p from Product p order by p.productView desc ";
        TypedQuery<Product> typedQuery = entityManager.createQuery(hql,Product.class);
        typedQuery.setMaxResults(limit);
        return typedQuery.getResultList();
    }

    @Override
    public List<Product> getListSale(int limit) {
        String hql = "select p from Product p order by p.productSale desc ";
        TypedQuery<Product> typedQuery = entityManager.createQuery(hql,Product.class);
        typedQuery.setMaxResults(limit);
        return typedQuery.getResultList();
    }

    @Override
    public List<Product> getListNav(int start, int limit) {
        String hql = "select p from Product p";
        TypedQuery<Product> typedQuery = entityManager.createQuery(hql,Product.class);
        typedQuery.setMaxResults(limit);
        return typedQuery.getResultList();
    }
}
