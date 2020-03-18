package com.assignment.springboot.Repository;

import com.assignment.springboot.Model.Product;

import java.util.List;

public interface ProductRepository extends Repository<Product> {

    public List<Product> getListByCategory(Long categoryId);

//    public List<Product> getListByCategoryAndLimit(Long categoryId,int limit);

    public List<Product> getListFeatured(int limit);

    public List<Product> getListSale(int limit);

    public List<Product> getListNav(int start,int limit);
}
