package com.cwru.petecommerce.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cwru.petecommerce.dao.abstraction.CRUD;
import com.cwru.petecommerce.models.Cart;
import com.cwru.petecommerce.utils.DatabaseConnection;

public class CartImp implements CRUD<Cart>{

    // Create a new cart
    // No need to pass in an id, the database will auto generate one
    @Override
    public int create(Cart cart) throws SQLException {

        String query = "INSERT INTO Cart (customerID, productID, quantity) VALUES (?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {
             Integer customerID = cart.getCustomerID();
             if (customerID != null) {
                 pstmt.setInt(1, Integer.valueOf(customerID));
             } else {
                 pstmt.setNull(1, Types.INTEGER);
             }

             Integer productID = cart.getProductID();
             if (productID != null) {
                 pstmt.setInt(2, Integer.valueOf(productID));
             } else {
                 pstmt.setNull(2, Types.INTEGER);
             }

             Integer quantity = cart.getQuantity();
             if (quantity != null) {
                 pstmt.setInt(3, Integer.valueOf(quanityt));
             } else {
                 pstmt.setNull(3, Types.INTEGER);
             }

            int result = pstmt.executeUpdate();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve a cart by id
    // If the seller does not exist, return an empty Optional
    @Override
    public Optional<Seller> getById(int customerID) throws SQLException {

        String query = "SELECT * FROM Cart WHERE customerID = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {
            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int resId = rs.getInt("customerID");
                int productID = rs.getInt("productID");
                //String name = rs.getString("name");
                int quanityt = rs.getInt("quantity");
                //int categoryID = rs.getInt("categoryID");
                //int stock = rs.getInt("stock");
                //String email = rs.getString("email");

                Cart cart = new Cart(resId, productID, quantity);
                return Optional.of(seller);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve all sellers
    // If there are no sellers, return an empty list
    // Dangerous, if there are a lot of sellers, this could be a memory hog
    // Solution: use a cursor, page through the results, or stream the results
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
                int customerID = rs.getInt("customerID");
                int productID = rs.getInt("productID");
                //String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                //int categoryID = rs.getInt("categoryID");
                //int stock = rs.getInt("stock");
                //String email = rs.getString("email");

                Cart cart = new Cart(customerID, productID, quantity);

                products.add(cart);
            }
            return carts;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    // Update a seller
    @Override
    public int update(int customerID, Cart cart) throws SQLException {
        String query = "UPDATE Cart SET productID = ?, quanityt = ? WHERE customerID = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
    
        ) {
             if (cart.getProductID() != null) {
                 pstmt.setInt(1, Integer.valueOf(product.getProductID()));
             } else {
                 pstmt.setNull(1, Types.INTEGER);
             }

             if (cart.getQuantity() != null) {
                 pstmt.setInt(2, Integer.valueOf(product.getQuantity()));
             } else {
                 pstmt.setNull(2, Types.INTEGER);
             }

             if (cart.getCustomerID() != null) {
                pstmt.setInt(3, Integer.valueOf(product.getCustomerID()));
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            //pstmt.setInt(1, product.getSellerID());
            //pstmt.setString(1, seller.getName());
            //pstmt.setString(2, seller.getEmail());
            //pstmt.setInt(4, product.getCategoryID());
            // pstmt.setInt(5, product.getPrice());
            // pstmt.setInt(6, product.getStock());
             //pstmt.setInt(3, id);
    
            int rowsAffected = pstmt.executeUpdate();
    
            return rowsAffected;
    
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
    }
    
    // Delete a cart
    @Override
    public int delete(int customerID) throws SQLException {
    
        String query = "DELETE FROM Cart WHERE customerID = ?";
    
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
    
        ) {
            pstmt.setInt(1, customerID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected;
    
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
    }
    
    // Retrieve all products by seller
    // public List<Product> getBySeller(int sellerId) throws SQLException {
    //     String query = "SELECT * FROM Product WHERE seller_id = ?";
    
    //     try (
    //             Connection connection = DatabaseConnection.getConnection();
    //             PreparedStatement pstmt = connection.prepareStatement(query);
    
    //     ) {
    
    //         List<Product> products = new ArrayList<>();
    //         pstmt.setInt(1, sellerId);
    
    //         try (ResultSet rs = pstmt.executeQuery()) {
    //             while (rs.next()) {
    //                 int id = rs.getInt("id");
    //                 int sellerID = rs.getInt("sellerID");
    //                 String name = rs.getString("name");
    //                 int price = rs.getInt("price");
    //                 int categoryID = rs.getInt("categoryID");
    //                 int stock = rs.getInt("stock");
    //                 String description = rs.getString("description");
    
    //                 Product product = new Product(id, sellerID, name, description, categoryID, price, stock);
    
    //                 products.add(product);
    //             }
    //             return products;
    
    //         }
    
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         throw e;
    //     }
    
    // }
    
}

