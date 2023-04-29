package com.cwru.petecommerce.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cwru.petecommerce.dao.abstraction.CRUD;
import com.cwru.petecommerce.models.Customer;
import com.cwru.petecommerce.utils.DatabaseConnection;

public class CustomerImp implements CRUD<Customer>{

    // Create a new customer
    // No need to pass in an id, the database will auto generate one
    @Override
    public int create(Customer customer) throws SQLException {

        String query = "INSERT INTO Customer (firstName, lastName, email, passwordHash, address) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPasswordHash());
            pstmt.setString(5, customer.getAddress());

            int result = pstmt.executeUpdate();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve a customer by id
    // If the product does not exist, return an empty Optional
    @Override
    public Optional<Customer> getById(int id) throws SQLException {

        String query = "SELECT * FROM Customer WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int resId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String passwordHash = rs.getString("passwordHash");
                String address = rs.getString("address");

                Customer customer = new Customer(resId, firstName, lastName, email, passwordHash, address);
                return Optional.of(customer);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve all customers
    // If there are no customers, return an empty list
    // Dangerous, if there are a lot of customers, this could be a memory hog
    // Solution: use a cursor, page through the results, or stream the results
    @Override
    public List<Customer> getAll() throws SQLException {

        String query = "SELECT * FROM Customer";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();

        ) {

            List<Customer> customers = new ArrayList<>();

            while (rs.next()) {
                int resId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String passwordHash = rs.getString("passwordHash");
                String address = rs.getString("address");

                Customer customer = new Customer(resId, firstName, lastName, email, passwordHash, address);

                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    // Update a customer
    @Override
    public int update(int id, Customer customer) throws SQLException {
        String query = "UPDATE Customer SET firstName = ?, lastName = ?, email = ?, passwordHash = ?, address = ? WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
    
        ) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPasswordHash());
            pstmt.setString(5, customer.getAddress());
            pstmt.setInt(6, id);
    
            int rowsAffected = pstmt.executeUpdate();
    
            return rowsAffected;
    
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
    }
    
    // Delete a product
    @Override
    public int delete(int id) throws SQLException {
    
        String query = "DELETE FROM Customer WHERE id = ?";
    
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
    
        ) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected;
    
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
    }
    
}
