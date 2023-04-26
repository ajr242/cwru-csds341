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
import com.cwru.petecommerce.models.Product;
import com.cwru.petecommerce.utils.DatabaseConnection;

public class ReviewImp implements CRUD<Review>{

    // Create a new Review
    // No need to pass in an id, the database will auto generate one
    @Override
    public int create(Review review) throws SQLException {

        String query = "INSERT INTO Review (date, productID, customerID, rating, description) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {
            Integer productID = review.getProductID();
            if (productId != null) {
                pstmt.setInt(1, Integer.valueOf(productId));
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }

            Integer customerId = review.getCustomerID();
            if (customerId != null) {
                pstmt.setInt(3, Integer.valueOf(customerId));
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            Integer rating = review.getRating();
            if (rating != null) {
                pstmt.setInt(4, Integer.valueOf(rating));
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            //pstmt.setInt(1, product.getSellerID());
            pstmt.setString(1, review.getDate());
            pstmt.setString(5, review.getDescription());


            int result = pstmt.executeUpdate();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve a review by id
    // If the review does not exist, return an empty Optional
    @Override
    public Optional<Review> getById(int id) throws SQLException {

        String query = "SELECT * FROM Review WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int resId = rs.getInt("id");
                int date = rs.getInt("date");
                int productID = rs.getInt("productID");
                int customerID = rs.getInt("customerID");
                int rating = rs.getInt("rating");
                String description = rs.getString("description");

                Review review = new Review(resId, date, productID, customerID, rating, description);
                return Optional.of(product);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve all reviews
    // If there are no reviews, return an empty list
    // Dangerous, if there are a lot of reviews, this could be a memory hog
    // Solution: use a cursor, page through the results, or stream the results
    @Override
    public List<Review> getAll() throws SQLException {

        String query = "SELECT * FROM Review";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();

        ) {

            List<Review> reviews = new ArrayList<>();

            while (rs.next()) {
                int resId = rs.getInt("id");
                int date = rs.getInt("date");
                int productID = rs.getInt("productID");
                int customerID = rs.getInt("customerID");
                int rating = rs.getInt("rating");
                String description = rs.getString("description");

                Review review = new Review(resId, date, productID, customerID, rating, description);

                reviews.add(review);
            }
            return reviews;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    // Update a review
    @Override
    public int update(int id, Review review) throws SQLException {
        String query = "UPDATE Review SET date = ?, productID = ?, customerID = ?, rating = ?, description = ? WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
    
        ) {
            if (review.getProductID() != null) {
                pstmt.setInt(2, Integer.valueOf(product.getProductID()));
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }

            if (product.getCustomerID() != null) {
                pstmt.setInt(3, Integer.valueOf(product.getCustomerID()));
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            if (product.getRating() != null) {
                pstmt.setInt(4, Integer.valueOf(product.getRating()));
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            //pstmt.setInt(1, product.getSellerID());
            pstmt.setString(1, review.getDate());
            pstmt.setString(5, product.getDescription());

            pstmt.setInt(7, id);
    
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
    
        String query = "DELETE FROM Review WHERE id = ?";
    
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
    
    // Retrieve all reviews by seller
    public List<Review> getBySeller(int sellerId) throws SQLException {
        String query = "SELECT * FROM Review WHERE seller_id = ?";
    
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
    
        ) {
    
            List<Review> reviews = new ArrayList<>();
            pstmt.setInt(1, sellerId);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int resId = rs.getInt("id");
                    int date = rs.getInt("date");
                    int productID = rs.getInt("productID");
                    int customerID = rs.getInt("customerID");
                    int rating = rs.getInt("rating");
                    String description = rs.getString("description");
    
                    Review review = new Review(resId, date, productID, customerID, rating, description);
    
                    reviews.add(review);
                }
                return reviews;
    
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    
    }
    
}
