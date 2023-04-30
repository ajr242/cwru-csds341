package com.cwru.petecommerce.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cwru.petecommerce.dao.abstraction.CRUDComposite;
import com.cwru.petecommerce.models.PurchaseProduct;
import com.cwru.petecommerce.utils.DatabaseConnection;

public class PurchaseProductImp implements CRUDComposite<PurchaseProduct> {

    // Add a product to the purchase for a specific customer
    @Override
    public int create(PurchaseProduct purchaseProduct) throws SQLException {
        String query = "INSERT INTO PurchaseProduct (purchaseID, productID, price, quantity) VALUES (?, ?, ?, ?)";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, purchaseProduct.getPurchaseID());
            pstmt.setInt(2, purchaseProduct.getProductID());
            pstmt.setInt(3, purchaseProduct.getPrice());
            pstmt.setInt(4, purchaseProduct.getQuantity());

            int result = pstmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve the purchase product for a specific purchase and product
    @Override
    public Optional<PurchaseProduct> getById(int purchaseID, int productID) throws SQLException {
        String query = "SELECT * FROM PurchaseProduct WHERE purchaseID = ? AND productID = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, purchaseID);
            pstmt.setInt(2, productID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int resPurchaseID = rs.getInt("purchaseID");
                int resProductID = rs.getInt("productID");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");

                PurchaseProduct purchaseProduct = new PurchaseProduct(resPurchaseID, resProductID, price, quantity);
                return Optional.of(purchaseProduct);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve all items in all purchases
    @Override
    public List<PurchaseProduct> getAll() throws SQLException {
        String query = "SELECT * FROM PurchaseProduct";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ) {
            List<PurchaseProduct> purchaseProducts = new ArrayList<>();

            while (rs.next()) {
                int purchaseID = rs.getInt("purchaseID");
                int productID = rs.getInt("productID");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");

                PurchaseProduct purchaseProduct = new PurchaseProduct(purchaseID, productID, price, quantity);
                purchaseProducts.add(purchaseProduct);
            }

            return purchaseProducts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Update the price and quantity of a product in a purchase
    @Override
    public int update(int purchaseID, int productID, PurchaseProduct purchaseProduct) throws SQLException {
        String query = "UPDATE PurchaseProduct SET price = ?, quantity = ? WHERE purchaseID = ? AND productID = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, purchaseProduct.getPrice());
            pstmt.setInt(2, purchaseProduct.getQuantity());
            pstmt.setInt(3, purchaseID);
            pstmt.setInt(4, productID);

            int result = pstmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    // Delete a product from a purchase
    @Override
    public int delete(int purchaseID, int productID) throws SQLException {
        String query = "DELETE FROM PurchaseProduct WHERE purchaseID = ? AND productID = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, purchaseID);
            pstmt.setInt(2, productID);

            int result = pstmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve all products in a purchase
    public List<PurchaseProduct> getAllByPurchaseID(int purchaseID) throws SQLException {
        String query = "SELECT * FROM PurchaseProduct WHERE purchaseID = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setInt(1, purchaseID);

            ResultSet rs = pstmt.executeQuery();

            List<PurchaseProduct> purchaseProducts = new ArrayList<>();
            while (rs.next()) {
                int productId = rs.getInt("productID");
                int price = rs.getInt("price");
                int qty = rs.getInt("quantity");
                PurchaseProduct purchaseProduct = new PurchaseProduct(purchaseID, productId, price, qty);
                purchaseProducts.add(purchaseProduct);
            }
            return purchaseProducts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}