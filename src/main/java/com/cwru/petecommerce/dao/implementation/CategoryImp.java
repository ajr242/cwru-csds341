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
import com.cwru.petecommerce.models.Category;
import com.cwru.petecommerce.utils.DatabaseConnection;

public class CategoryImp implements CRUD<Category> {

    // Create a new category
    // No need to pass in an id, the database will auto generate one
    @Override
    public int create(Category category) throws SQLException {
        String query = "INSERT INTO Category (name, parentCatID) VALUES (?, ?)";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setString(1, category.getName());
            if (category.getParentCatID() != null) {
                pstmt.setInt(2, category.getParentCatID());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }

            int result = pstmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve a category by id
    // If the category does not exist, return an empty Optional
    @Override
    public Optional<Category> getById(int id) throws SQLException {
        String query = "SELECT * FROM Category WHERE id = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int resId = rs.getInt("id");
                String name = rs.getString("name");
                Integer parentCatID = rs.getInt("parentCatID");
                if (rs.wasNull()) {
                    parentCatID = null;
                }

                Category category = new Category(resId, name, parentCatID);
                return Optional.of(category);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve all categories
    // If there are no categories, return an empty list
    @Override
    public List<Category> getAll() throws SQLException {
        String query = "SELECT * FROM Category";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ) {
            List<Category> categories = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Integer parentCatID = rs.getInt("parentCatID");
                if (rs.wasNull()) {
                    parentCatID = null;
                }

                Category category = new Category(id, name, parentCatID);
                categories.add(category);
            }

            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update a category
    @Override
    public int update(int id, Category category) throws SQLException {
        String query = "UPDATE Category SET name = ?, parentCatID = ? WHERE id = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setString(1, category.getName());
            if (category.getParentCatID() != null) {
                pstmt.setInt(2, category.getParentCatID());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            pstmt.setInt(3, id);

            int result = pstmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete a product
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
}


