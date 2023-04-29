package com.cwru.petecommerce.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.cwru.petecommerce.dao.abstraction.CRUD;
import com.cwru.petecommerce.models.Purchase;
import com.cwru.petecommerce.utils.DatabaseConnection;

public class PurchaseImp implements CRUD<Purchase> {

    // Create a new purchase
    // No need to pass in an id, the database will auto generate one
    @Override
    public int create(Purchase purchase) throws SQLException {
        String query = "INSERT INTO Purchase (paymentType, totalAmount, date, customerID, delivered) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setString(1, purchase.getPaymentType());
            pstmt.setInt(2, purchase.getTotalAmount());
            pstmt.setDate(3, new java.sql.Date(purchase.getDate().getTime()));
            pstmt.setInt(4, purchase.getCustomerID());
            pstmt.setBoolean(5, purchase.isDelivered());

            int result = pstmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve a purchase by id
    // If the purchase does not exist, return an empty Optional
    @Override
    public Optional<Purchase> getById(int id) throws SQLException {
        String query = "SELECT * FROM Purchase WHERE id = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int resId = rs.getInt("id");
                String paymentType = rs.getString("paymentType");
                int totalAmount = rs.getInt("totalAmount");
                Date date = rs.getDate("date");
                int customerID = rs.getInt("customerID");
                boolean delivered = rs.getBoolean("delivered");

                Purchase purchase = new Purchase(resId, paymentType, totalAmount, date, customerID, delivered);
                return Optional.of(purchase);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve all purchases
    // If there are no purchases, return an empty list
    @Override
    public List<Purchase> getAll() throws SQLException {
        String query = "SELECT * FROM Purchase";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ) {
            List<Purchase> purchases = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String paymentType = rs.getString("paymentType");
                int totalAmount = rs.getInt("totalAmount");
                Date date = rs.getDate("date");
                int customerID = rs.getInt("customerID");
                boolean delivered = rs.getBoolean("delivered");

                Purchase purchase = new Purchase(id, paymentType, totalAmount, date, customerID, delivered);
                purchases.add(purchase);
            }

            return purchases;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update a purchase
    @Override
    public int update(int id, Purchase purchase) throws SQLException {
        String query = "UPDATE Purchase SET paymentType = ?, totalAmount = ?, date = ?, customerID = ?, delivered = ?, WHERE id = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setString(1, purchase.getPaymentType());
            pstmt.setInt(2, purchase.getTotalAmount());
            pstmt.setDate(3, new java.sql.Date(purchase.getDate().getTime()));
            pstmt.setInt(4, purchase.getCustomerID());
            pstmt.setBoolean(5, purchase.isDelivered());

            int result = pstmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete a purchase
    @Override
    public int delete(int id) throws SQLException {
    
        String query = "DELETE FROM Category WHERE id = ?";
    
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

    // Mark a purchase as delivered
    public int setDelivered(int id) throws SQLException {
        String query = "UPDATE Purchase SET delivered = ? WHERE id = ?";
    
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, id);
    
            int result = pstmt.executeUpdate();
    
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}