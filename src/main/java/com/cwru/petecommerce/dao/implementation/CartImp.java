package com.cwru.petecommerce.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cwru.petecommerce.dao.abstraction.CRUDComposite;
import com.cwru.petecommerce.models.Cart;
import com.cwru.petecommerce.utils.DatabaseConnection;

public class CartImp implements CRUDComposite<Cart> {

    // Add a product to the cart for a specific customer
    @Override
    public int create(Cart cart) throws SQLException {
        String query = "INSERT INTO Cart (customerID, productID, quantity) VALUES (?, ?, ?)";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, cart.getCustomerID());
            pstmt.setInt(2, cart.getProductID());
            pstmt.setInt(3, cart.getQuantity());

            int result = pstmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve the cart for a specific customer
    @Override
    public Optional<Cart> getById(int customerID, int productID) throws SQLException {
        String query = "SELECT * FROM Cart WHERE customerID = ? AND productID = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, customerID);
            pstmt.setInt(2, productID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int resCustomerId = rs.getInt("customerID");
                int resProductId = rs.getInt("productID");
                int quantity = rs.getInt("quantity");

                Cart cart = new Cart(resCustomerId, resProductId, quantity);
                return Optional.of(cart);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve all items in all carts
    @Override
    public List<Cart> getAll() throws SQLException {
        String query = "SELECT * FROM Cart";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ) {
            List<Cart> carts = new ArrayList<>();

            while (rs.next()) {
                int customerId = rs.getInt("customerID");
                int productId = rs.getInt("productID");
                int quantity = rs.getInt("quantity");

                Cart cart = new Cart(customerId, productId, quantity);
                carts.add(cart);
            }

            return carts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update the quantity of a product in the cart for a specific customer
    @Override
    public int update(int customerID, int productID, Cart cart) throws SQLException {
        String query = "UPDATE Cart SET quantity = ? WHERE customerID = ? AND productID = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, cart.getQuantity());
            pstmt.setInt(2, customerID);
            pstmt.setInt(3, productID);

            int result = pstmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    // Delete a cart
    @Override
    public int delete(int customerID, int productID) throws SQLException {
    
        String query = "DELETE FROM Cart WHERE customerID = ? AND productID = ?";
    
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
    
        ) {
            pstmt.setInt(1, customerID);
            pstmt.setInt(2, productID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected;
    
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
    }

    // Get all items in the cart for a given customer ID
    public List<Cart> getAllByCustomerID(int customerID) throws SQLException {
        String query = "SELECT * FROM Cart WHERE customerID = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, customerID);

            ResultSet rs = pstmt.executeQuery();

            List<Cart> cartItems = new ArrayList<>();
            while (rs.next()) {
                int productId = rs.getInt("productID");
                int qty = rs.getInt("quantity");
                Cart item = new Cart(customerID, productId, qty);
                cartItems.add(item);
            }
            return cartItems;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete all items in the cart for a given customer ID
    public int deleteAllByCustomerID(int customerID) throws SQLException {
        String query = "DELETE FROM Cart WHERE customerID = ?";
        
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, customerID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}

