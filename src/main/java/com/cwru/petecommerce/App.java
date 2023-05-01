package com.cwru.petecommerce;

import java.sql.SQLException;
import java.sql.Connection;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import com.cwru.petecommerce.dao.abstraction.CRUD;
import com.cwru.petecommerce.dao.abstraction.CRUDComposite;
import com.cwru.petecommerce.dao.implementation.*;
import com.cwru.petecommerce.models.*;
import com.cwru.petecommerce.utils.InitDatabase;
import com.cwru.petecommerce.utils.DatabaseConnection;

public class App {
    public static void main(String[] args) {
        System.out.println("Begin");

        try {
            initDB();

            List<Seller> sellers = new ArrayList<>();
            sellers.add(new Seller(0, "Pet Palace", "petpalace@gmail.com"));
            sellers.add(new Seller(0, "Pet Paradise", "petparadise@gmail.com"));
            sellers.add(new Seller(0, "Pet World", "petworld@gmail.com"));
            sellers.add(new Seller(0, "Pet Central", "petcentral@gmail.com"));
            sellers.add(new Seller(0, "Pet Stop", "petstop@gmail.com"));
            insertSellers(sellers);

            //insertProduct();
            //insertProductUI();
            //retrieveById();
            //retrieveAll();
            //update();
            //delete();
            //retrieveByCategory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initDB() throws SQLException {
        InitDatabase initDatabase = new InitDatabase();

        String customerTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Customer' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Customer (id INT IDENTITY(1,1) PRIMARY KEY, firstName VARCHAR(20), lastName VARCHAR(20), email VARCHAR(30), passwordHash VARCHAR(64), address VARCHAR(40)) "
                + "END";
                                
        String categoryTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Category' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Category (id INT IDENTITY(1,1) PRIMARY KEY, name VARCHAR(20), parentCatID INT, FOREIGN KEY (parentCatID) REFERENCES Category(id) ON DELETE CASCADE) "
                + "END";
        
        String sellerTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Seller' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Seller (id INT IDENTITY(1,1) PRIMARY KEY, name VARCHAR(20), email VARCHAR(30)) "
                + "END";

        String productTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Product' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Product (id INT IDENTITY(1,1) PRIMARY KEY, sellerID INT, name VARCHAR(20), description VARCHAR(500), categoryID INT, price INT, stock INT, FOREIGN KEY (categoryID) REFERENCES Category(id), FOREIGN KEY (sellerID) REFERENCES Seller(id) ON DELETE CASCADE) "
                + "END";
        
        String cartTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Cart' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Cart (customerID INT, productID INT, quantity INT, PRIMARY KEY (customerID, productID), FOREIGN KEY (customerID) REFERENCES Customer(id) ON DELETE CASCADE, FOREIGN KEY (productID) REFERENCES Product(id)) "
                + "END";
        
        String purchaseTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Purchase' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Purchase (id INT IDENTITY(1,1) PRIMARY KEY, paymentType VARCHAR(20), totalAmount INT, date DATE, customerID INT, delivered BIT, FOREIGN KEY (customerID) REFERENCES Customer(id) ON DELETE CASCADE) "
                + "END";
        
        String reviewTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Review' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Review (id INT IDENTITY(1,1) PRIMARY KEY, date VARCHAR(20), productID INT, customerID INT, rating INT, description VARCHAR(500), FOREIGN KEY (productID) REFERENCES Product(id), FOREIGN KEY (customerID) REFERENCES Customer(id)) "
                + "END";
        
        String purchaseProductTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Purchase_Product' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Purchase_Product (purchaseID INT, productID INT, price INT, quantity INT, PRIMARY KEY (purchaseID, productID), FOREIGN KEY (purchaseID) REFERENCES Purchase(id) ON DELETE CASCADE, FOREIGN KEY (productID) REFERENCES Product(id)) "
                + "END";

        String catDeleteTrigger = "CREATE TRIGGER delete_children_categories "
                + "AFTER DELETE ON Category "
                + "FOR EACH ROW "
                + "BEGIN "
                + "DELETE FROM Category "
                + "WHERE parentCatID = OLD.categoryID "
                + "END";
        
        String reviewTableRatingIndex = "CREATE INDEX idx_review_rating ON Review(rating)";
        String productTableSellerIDIndex = "CREATE INDEX idx_product_sellerID ON Product(sellerID)";
        String productTableCategoryIDIndex = "CREATE INDEX idx_product_categoryID ON Product(categoryID)";
        String purchaseProductTablePurchaseIDIndex = "CREATE INDEX idx_purchaseproduct_purchaseID ON PurchaseProduct(purchaseID)";
        
        try {
            initDatabase.createTable(customerTableQuery);
            initDatabase.createTable(categoryTableQuery);
            initDatabase.createTable(sellerTableQuery);
            initDatabase.createTable(productTableQuery);
            initDatabase.createTable(cartTableQuery);
            initDatabase.createTable(purchaseTableQuery);
            initDatabase.createTable(reviewTableQuery);
            initDatabase.createTable(purchaseProductTableQuery);

            initDatabase.createTrigger(catDeleteTrigger);

            initDatabase.createIndex(reviewTableRatingIndex);
            initDatabase.createIndex(productTableSellerIDIndex);
            initDatabase.createIndex(productTableCategoryIDIndex);
            initDatabase.createIndex(purchaseProductTablePurchaseIDIndex);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static void insertSellers(List<Seller> sellers) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
    
            CRUD<Seller> sellerImp = new SellerImp(connection);
    
            int totalInserted = 0;
    
            // create sellers and add them to the database
            for (Seller seller : sellers) {
                int result = sellerImp.create(seller);
                totalInserted += result;
            }
    
            System.out.println("Inserted " + totalInserted + " seller(s).");
    
            connection.commit(); // commit the transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // rollback the transaction if an exception occurs
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close(); // close the connection
            }
        }
    }

    // Methods for Product
    private static void insertProduct() throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
            
            // create the cart
            Product dogFood = new Product(0, null, "Dog Food", "Yummy", null, 10000, 2);

            CRUD<Product> productImp = new ProductImp(connection);
    
            int result = productImp.create(dogFood);
    
            System.out.println(result);
            
            connection.commit(); // commit the transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // rollback the transaction if an exception occurs
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close(); // close the connection
            }
        }
    }

    private static void insertProductUI() throws SQLException {
        Connection connection = null;
        Scanner myObj = new Scanner(System.in);
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
            
                        String productName, productDescrip;
            int sellerID, categoryID, price, stock;
            
            // Enter username and press Enter
            System.out.println("Enter product name then enter. "); 
            productName = myObj.nextLine();   
            System.out.println("Enter product description then enter.");
            productDescrip = myObj.nextLine();
            System.out.println("Enter sellerID and 0 if none. "); 
            sellerID = myObj.nextInt();
            System.out.println("Enter categoryID and 0 if none. "); 
            categoryID = myObj.nextInt();
            System.out.println("Enter price with no decimal. "); 
            price = myObj.nextInt();
            System.out.println("Enter stock quantity. "); 
            stock = myObj.nextInt();
            
            Product x = new Product(0, sellerID, productName, productDescrip, categoryID, price, stock);
            CRUD<Product> productImp = new ProductImp(connection);
            int result = productImp.create(x);
            System.out.println(result);
            
            connection.commit(); // commit the transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // rollback the transaction if an exception occurs
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close(); // close the connection
                myObj.close();
            }
        }
    }


    private static void retrieveProductById() throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
            
            // create the cart
            ProductImp productImp = new ProductImp(connection);

            Optional<Product> optionalProduct = productImp.getById(32);
    
            if (optionalProduct.isPresent()) {
                System.out.println("Product found");
                Product product = optionalProduct.get();
                System.out.println(product);
            } else {
                System.out.println("Product not found");
            }
            
            connection.commit(); // commit the transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // rollback the transaction if an exception occurs
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close(); // close the connection
            }
        }
    }
    
    private static void retrieveAllProducts() throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
            
            ProductImp productImp = new ProductImp(connection);

            List<Product> products = productImp.getAll();
    
            for (Product product : products) {
                System.out.println(product);
            }
            
            connection.commit(); // commit the transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // rollback the transaction if an exception occurs
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close(); // close the connection
            }
        }
    }

    private static void addToCart(int customerId, int productId, int quantity) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
            
            // create the cart
            Cart cart = new Cart(customerId, productId, quantity);
            CRUDComposite<Cart> cartImp = new CartImp(connection);
            int result = cartImp.create(cart); // pass the connection to the create method
            System.out.println(result); // print the ID of the created cart
            
            connection.commit(); // commit the transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // rollback the transaction if an exception occurs
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close(); // close the connection
            }
        }
    }
    

    public void checkout(int customerID, String paymentType) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction

            // get all items in the customer's cart
            CartImp cartImp = new CartImp(connection);
            List<Cart> cartItems = cartImp.getAllByCustomerID(customerID);
            
            // check if cart is empty
            if(cartItems.isEmpty()) {
                System.out.println("Cart is empty");
                return;
            }
            
            // get current date
            Date date = new Date();
            
            // create new purchase record
            PurchaseImp purchaseImp = new PurchaseImp(connection);
            Purchase purchase = new Purchase(0, paymentType, 0, date, customerID, false);
            int purchaseID = purchaseImp.create(purchase);
            
            // insert items into PurchaseProduct table and update product stock
            ProductImp productImp = new ProductImp(connection);
            PurchaseProductImp purchaseProductImp = new PurchaseProductImp(connection);
            int totalAmount = 0;
            for(Cart cartItem: cartItems) {
                Optional<Product> optionalProduct = productImp.getById(cartItem.getProductID());
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();

                    // TODO: Deal with purchases where this is not the case
                    if(product.getStock() >= cartItem.getQuantity()) {
                        // reduce product stock by cart item quantity
                        productImp.updateStock(product.getId(), -cartItem.getQuantity());
            
                        // insert item into PurchaseProduct table
                        PurchaseProduct purchaseProduct = new PurchaseProduct(purchaseID, cartItem.getProductID(), product.getPrice(), cartItem.getQuantity());
                        purchaseProductImp.create(purchaseProduct);
            
                        // update total amount
                        totalAmount += product.getPrice() * cartItem.getQuantity();
                    }
                }
            }
        
            // update totalAmount in Purchase table
            purchase.setTotalAmount(totalAmount);
            purchaseImp.update(purchaseID, purchase);
            
            // delete all items in customer's cart
            cartImp.deleteAllByCustomerID(customerID);

            connection.commit(); // commit the transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // rollback the transaction if an exception occurs
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close(); // close the connection
            }
        }
    }

    public void updateProductStock(int productID, int stock) throws SQLException {

        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction

            ProductImp productImp = new ProductImp(connection);
            productImp.updateStock(productID, stock);

            connection.commit(); // commit the transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // rollback the transaction if an exception occurs
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close(); // close the connection
            }
        }
    }

    public void getRevenueByCategoryID(int categoryID, int stock) throws SQLException {

        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction

            PurchaseProductImp purchaseproductImp = new PurchaseProductImp(connection);
            int revenue = purchaseproductImp.getTotalRevenueByCategory(categoryID);
            System.out.println(revenue);

            connection.commit(); // commit the transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // rollback the transaction if an exception occurs
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close(); // close the connection
            }
        }
    }
    
    
/* 
    private static void update() throws SQLException {
        Pokemon pokemon = new Pokemon(1, "Pikachur", "Electric", 13.2f);

        PokemonImp pokemonImp = new PokemonImp();

        int result = pokemonImp.update(4, pokemon);

        System.out.println(result);
    }

    private static void delete() throws SQLException {
        PokemonImp pokemonImp = new PokemonImp();

        int result = pokemonImp.delete(2);

        System.out.println(result);
    }

    private static void retrieveByCategory() throws SQLException {
        PokemonDAO pokemonImp = new PokemonImp();

        List<Pokemon> pokemons = pokemonImp.getByCategory("Fire");

        for (Pokemon pokemon : pokemons) {
            System.out.println(pokemon);
        }
    } */

}
