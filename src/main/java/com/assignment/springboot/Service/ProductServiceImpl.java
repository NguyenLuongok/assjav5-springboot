package com.assignment.springboot.Service;

import com.assignment.springboot.Model.Product;
import com.assignment.springboot.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        productRepository.remove(id);
    }

    @Override
    public List<Product> getListByCategory(Long categoryId) {
        return productRepository.getListByCategory(categoryId);
    }

//    @Override
//    public List<Product> getListByCategoryAndLimit(Long categoryId, int limit) {
//        return productRepository.getListByCategoryAndLimit(categoryId,limit);
//    }

    @Override
    public List<Product> getListFeatured(int limit) {
        return productRepository.getListFeatured(limit);
    }

    @Override
    public List<Product> getListSale(int limit) {
        return productRepository.getListSale(limit);
    }

    @Override
    public List<Product> getListNav(int start, int limit) {
        return productRepository.getListNav(start,limit);
    }
}
