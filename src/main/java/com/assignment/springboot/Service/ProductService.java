package com.assignment.springboot.Service;

import com.assignment.springboot.Model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
    void save(Product product);
    void remove(Long id);
    List<Product> getListByCategory(Long categoryId);
//    List<Product> getListByCategoryAndLimit(Long categoryId,int limit);
    List<Product> getListFeatured(int limit);
    List<Product> getListSale(int limit);
    List<Product> getListNav(int start,int limit);
}
