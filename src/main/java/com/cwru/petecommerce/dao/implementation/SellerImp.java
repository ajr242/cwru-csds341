package com.cwru.petecommerce.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cwru.petecommerce.dao.abstraction.CRUD;
import com.cwru.petecommerce.models.Seller;
import com.cwru.petecommerce.utils.DatabaseConnection;

public class SellerImp implements CRUD<Seller>{

    // Create a new seller
    // No need to pass in an id, the database will auto generate one
    @Override
    public int create(Seller seller) throws SQLException {

        String query = "INSERT INTO Seller (name, email) VALUES (?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {
            pstmt.setString(1, seller.getName());
            pstmt.setString(2, seller.getEmail());

            int result = pstmt.executeUpdate();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve a seller by id
    // If the seller does not exist, return an empty Optional
    @Override
    public Optional<Seller> getById(int id) throws SQLException {

        String query = "SELECT * FROM Seller WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);

        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int resId = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                Seller seller = new Seller(resId, name, email);
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
    public List<Seller> getAll() throws SQLException {

        String query = "SELECT * FROM Seller";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();

        ) {

            List<Seller> sellers = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                Seller seller = new Seller(id, name, email);

                sellers.add(seller);
            }
            return sellers;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    // Update a seller
    @Override
    public int update(int id, Seller seller) throws SQLException {
        String query = "UPDATE Seller SET name = ?, email = ? WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
    
        ) {
            pstmt.setString(1, seller.getName());
            pstmt.setString(2, seller.getEmail());
            pstmt.setInt(3, id);
    
            int rowsAffected = pstmt.executeUpdate();
    
            return rowsAffected;
    
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
    }
    
    // Delete a seller
    @Override
    public int delete(int id) throws SQLException {
    
        String query = "DELETE FROM Seller WHERE id = ?";
    
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
