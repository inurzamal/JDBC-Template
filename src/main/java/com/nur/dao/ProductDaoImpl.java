package com.nur.dao;

import com.nur.model.Product;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Plain JDBC Example
 */

@Repository
public class ProductDaoImpl implements ProductDao {

    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_test";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public ProductDaoImpl() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            handleSQLException("Error establishing connection", e);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM products")) {

            while (resultSet.next()) {
                Product product = mapResultSetToProduct(resultSet);
                products.add(product);
            }

        } catch (SQLException e) {
            handleSQLException("Error fetching all products", e);
        }
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        Product product = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE id = ?")) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = mapResultSetToProduct(resultSet);
                }
            }

        } catch (SQLException e) {
            handleSQLException("Error fetching product by ID", e);
        }
        return product;
    }

    @Override
    public void addProduct(Product product) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO products (name, price) VALUES (?, ?)")) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException("Error adding product", e);
        }
    }

    @Override
    public void addProducts(List<Product> products) {
    }

    @Override
    public void updateProduct(Product product) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE products SET name = ?, price = ? WHERE id = ?")) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException("Error updating product", e);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException("Error deleting product", e);
        }
    }

    private void handleSQLException(String message, SQLException e) {
        // Log the exception or throw a custom exception with a meaningful message
        e.printStackTrace();
        throw new RuntimeException(message, e);
    }

    private Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        return product;
    }

    @PreDestroy
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            handleSQLException("Error closing connection", e);
        }
    }
}
