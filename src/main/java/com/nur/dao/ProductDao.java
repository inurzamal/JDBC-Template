package com.nur.dao;

import com.nur.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void addProduct(Product product);
    void addProducts(List<Product> products);
    void updateProduct(Product product);
    void deleteProduct(Long id);
}

