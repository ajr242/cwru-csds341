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
import com.cwru.petecommerce.models.Review;

public class ReviewImp implements CRUD<Review> {

    private final Connection connection;

    public ReviewImp(Connection connection) {
        this.connection = connection;
    }

    // Create a new review
    // No need to pass in an id, the database will auto generate one
    @Override
    public int create(Review review) throws SQLException {
        String query = "INSERT INTO Review (date, productID, customerID, rating, description) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setTimestamp(1, new java.sql.Timestamp(review.getDate().getTime()));
            pstmt.setInt(2, review.getProductID());
            pstmt.setInt(3, review.getCustomerID());
            pstmt.setInt(4, review.getRating());
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

        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int resId = rs.getInt("id");
                Date date = new Date(rs.getTimestamp("date").getTime());
                int productID = rs.getInt("productID");
                int customerID = rs.getInt("customerID");
                int rating = rs.getInt("rating");
                String description = rs.getString("description");

                Review review = new Review(resId, date, productID, customerID, rating, description);
                return Optional.of(review);
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
    @Override
    public List<Review> getAll() throws SQLException {
        String query = "SELECT * FROM Review";

        try (
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ) {
            List<Review> reviews = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = new Date(rs.getTimestamp("date").getTime());
                int productID = rs.getInt("productID");
                int customerID = rs.getInt("customerID");
                int rating = rs.getInt("rating");
                String description = rs.getString("description");

                Review review = new Review(id, date, productID, customerID, rating, description);
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

        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setTimestamp(1, new java.sql.Timestamp(review.getDate().getTime()));
            pstmt.setInt(2, review.getProductID());
            pstmt.setInt(3, review.getCustomerID());
            pstmt.setInt(4, review.getRating());
            pstmt.setString(5, review.getDescription());
            pstmt.setInt(6, review.getId());
    
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
    
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected;
    
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
    }
    
}
