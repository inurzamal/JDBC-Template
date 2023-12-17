package com.nur.dao;

import com.nur.model.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CRUD operation using Spring JDBC Template
 */

@Repository
public class ProductDaoImpl2 implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDaoImpl2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
//        return jdbcTemplate.query(sql, new ProductRowMapper());
        return jdbcTemplate.query(sql, this::mapRowToProduct);
    }

    @Override
    public Product getProductById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToProduct, id);
    }

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice());
    }

    @Override
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getId());
    }

    @Override
    public void deleteProduct(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Product mapRowToProduct(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        return product;
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            return product;
        }
    }

}
