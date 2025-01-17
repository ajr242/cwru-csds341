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

public class ProductImp implements CRUD<Product>{

    private final Connection connection;

    public ProductImp(Connection connection) {
        this.connection = connection;
    }

    // Create a new product
    // No need to pass in an id, the database will auto generate one
    @Override
    public int create(Product product) throws SQLException {

        String query = "INSERT INTO Product (sellerID, name, description, categoryID, price, stock) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            Integer sellerId = product.getSellerID();
            if (sellerId != null) {
                pstmt.setInt(1, Integer.valueOf(sellerId));
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }

            Integer categoryId = product.getCategoryID();
            if (categoryId != null) {
                pstmt.setInt(4, Integer.valueOf(categoryId));
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            //pstmt.setInt(1, product.getSellerID());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            //pstmt.setInt(4, product.getCategoryID());
            pstmt.setInt(5, product.getPrice());
            pstmt.setInt(6, product.getStock());

            int result = pstmt.executeUpdate();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Retrieve a product by id
    // If the product does not exist, return an empty Optional
    @Override
    public Optional<Product> getById(int id) throws SQLException {

        String query = "SELECT * FROM Product WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int resId = rs.getInt("id");
                int sellerID = rs.getInt("sellerID");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int categoryID = rs.getInt("categoryID");
                int stock = rs.getInt("stock");
                String description = rs.getString("description");

                Product product = new Product(resId, sellerID, name, description, categoryID, price, stock);
                return Optional.of(product);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /*
    public List<Product> getAllProdSellerInfobySellerID(int sellerID) throws SQLException {
        //String query = "SELECT * FROM Product WHERE sellerID = ?";

        String query = "SELECT *, p.id as productID, s.id as sellerID" +
                        "FROM Seller s" +
                        "JOIN Product p ON s.id = p.Sellerid" +
                        "WHERE s.sellerID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, sellerID);

            List<Product> products = new ArrayList<>();
            pstmt.setInt(1, sellerID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("productID");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int categoryID = rs.getInt("categoryID");
                int stock = rs.getInt("stock");
                String description = rs.getString("description");

                Product product = new Product(id, sellerID, name, description, categoryID, price, stock);

                products.add(product);
            }

            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    */

    // Retrieve all products
    // If there are no products, return an empty list
    // Dangerous, if there are a lot of products, this could be a memory hog
    // Solution: use a cursor, page through the results, or stream the results
    @Override
    public List<Product> getAll() throws SQLException {

        String query = "SELECT * FROM Product";

        try (
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();

        ) {

            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                int sellerID = rs.getInt("sellerID");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int categoryID = rs.getInt("categoryID");
                int stock = rs.getInt("stock");
                String description = rs.getString("description");

                Product product = new Product(id, sellerID, name, description, categoryID, price, stock);

                products.add(product);
            }
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    // Update a product
    @Override
    public int update(int id, Product product) throws SQLException {
        String query = "UPDATE Product SET sellerID = ?, name = ?, description = ?, categoryID = ?, price = ?, stock = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            if (product.getSellerID() != null) {
                pstmt.setInt(1, Integer.valueOf(product.getSellerID()));
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }

            if (product.getCategoryID() != null) {
                pstmt.setInt(4, Integer.valueOf(product.getCategoryID()));
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            //pstmt.setInt(1, product.getSellerID());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            //pstmt.setInt(4, product.getCategoryID());
            pstmt.setInt(5, product.getPrice());
            pstmt.setInt(6, product.getStock());
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
    
        String query = "DELETE FROM Product WHERE id = ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected;
    
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
    }
    
    // Retrieve all products by seller
    public List<Product> getBySeller(int sellerId) throws SQLException {
        String query = "SELECT * FROM Product WHERE sellerID = ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
    
            List<Product> products = new ArrayList<>();
            pstmt.setInt(1, sellerId);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int sellerID = rs.getInt("sellerID");
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    int categoryID = rs.getInt("categoryID");
                    int stock = rs.getInt("stock");
                    String description = rs.getString("description");
    
                    Product product = new Product(id, sellerID, name, description, categoryID, price, stock);
    
                    products.add(product);
                }
                return products;
    
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    
    }

    public void updateStock(int id, int stockChange) throws SQLException, IllegalArgumentException {
        Optional<Product> optionalProduct = getById(id);
    
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
    
            int currentStock = product.getStock();
            int newStock = currentStock + stockChange;
    
            if (newStock >= 0) {
                product.setStock(newStock);
                update(id, product);
            } else {
                throw new IllegalArgumentException("Stock would be less than 0 after the update.");
            }
        } else {
            throw new IllegalArgumentException("Product with id " + id + " does not exist.");
        }
    }    
    
    public List<Product> getBestSellingProducts(int numProducts) throws SQLException {
        String query = "SELECT TOP (?) p.*, SUM(pp.quantity) as totalQuantity " +
                        "FROM Purchase_Product pp " +
                        "JOIN Product p ON pp.productID = p.id " +
                        "GROUP BY p.id, p.name, p.sellerID, p.description, p.categoryID, p.price, p.stock " +
                        "ORDER BY totalQuantity DESC";

        List<Product> bestSellingProducts = new ArrayList<>();
    
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, numProducts);
    
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int sellerID = rs.getInt("sellerID");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int categoryID = rs.getInt("categoryID");
                int stock = rs.getInt("stock");
                String description = rs.getString("description");

                Product product = new Product(id, sellerID, name, description, categoryID, price, stock);
                bestSellingProducts.add(product);
            }
    
            return bestSellingProducts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Product> getBestReviewedProducts(int numProducts) throws SQLException {
        String query = "SELECT TOP (?) p.*, AVG(r.rating) as avgRating " +
                        "FROM Product p " +
                        "JOIN Review r ON p.id = r.productID " +
                        "GROUP BY p.id, p.name, p.sellerID, p.description, p.categoryID, p.price, p.stock " +
                        "ORDER BY avgRating DESC";
    
        List<Product> bestReviewedProducts = new ArrayList<>();
    
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, numProducts);
    
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int sellerID = rs.getInt("sellerID");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int categoryID = rs.getInt("categoryID");
                int stock = rs.getInt("stock");
                String description = rs.getString("description");
                double avgRating = rs.getDouble("avgRating");
    
                Product product = new Product(id, sellerID, name, description, categoryID, price, stock);
                bestReviewedProducts.add(product);
            }
    
            return bestReviewedProducts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Product> filterByAvgRating(int minRating, int maxRating) throws SQLException {
        String query = "SELECT p.*, AVG(r.rating) as avgRating " +
                        "FROM Product p " +
                        "JOIN Review r ON p.id = r.productID " +
                        "GROUP BY p.id, p.name, p.sellerID, p.description, p.categoryID, p.price, p.stock " +
                        "HAVING AVG(r.rating) >= ? AND AVG(r.rating) <= ? " +
                        "ORDER BY p.id ASC";
        
        List<Product> filteredProducts = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, minRating);
            pstmt.setInt(2, maxRating);
        
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int sellerID = rs.getInt("sellerID");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int categoryID = rs.getInt("categoryID");
                int stock = rs.getInt("stock");
                String description = rs.getString("description");
                double avgRating = rs.getDouble("avgRating");
        
                Product product = new Product(id, sellerID, name, description, categoryID, price, stock);
                filteredProducts.add(product);
            }
        
            return filteredProducts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    
}
