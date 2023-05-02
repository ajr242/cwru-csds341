package com.cwru.petecommerce;

import java.sql.SQLException;
import java.sql.Connection;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

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
            List<Seller> sellers = new ArrayList<>();
            sellers.add(new Seller(0, "Pet Palace", "petpalace@gmail.com"));
            sellers.add(new Seller(0, "Pet Paradise", "petparadise@gmail.com"));
            sellers.add(new Seller(0, "Pet World", "petworld@gmail.com"));
            sellers.add(new Seller(0, "Pet Central", "petcentral@gmail.com"));
            sellers.add(new Seller(0, "Pet Stop", "petstop@gmail.com"));

            List<Category> categories = new ArrayList<>();
            categories.add(new Category(1, "Animals", null));
            categories.add(new Category(2, "Dogs", 1));
            categories.add(new Category(3, "Cats", 1));
            categories.add(new Category(4, "Birds", 1));
            categories.add(new Category(5, "Small Animals", 1));
            categories.add(new Category(6, "Food", null));
            categories.add(new Category(7, "Dry Food", 6));
            categories.add(new Category(8, "Wet Food", 6));
            categories.add(new Category(9, "Treats", 6));
            categories.add(new Category(10, "Accessories", null));
            categories.add(new Category(11, "Collars", 10));
            categories.add(new Category(12, "Leashes", 10));
            categories.add(new Category(13, "Toys", 10));
            categories.add(new Category(14, "Grooming", null));
            categories.add(new Category(15, "Shampoo", 14));
            categories.add(new Category(16, "Conditioner", 14));
            categories.add(new Category(17, "Brushes", 14));

            List<Product> products = new ArrayList<>();
            products.add(new Product(1, 1, "Pedigree Dry Dog Food", "Pedigree Dry Dog Food with Real Chicken, 10kg Pack", 7, 2499, 100));
            products.add(new Product(2, 1, "Royal Canin Wet Dog Food", "Royal Canin Wet Dog Food for Adult Dogs, Pack of 12 Cans, 400g each", 8, 1099, 50));
            products.add(new Product(3, 2, "Whiskas Dry Cat Food", "Whiskas Dry Cat Food with Real Tuna, 7kg Pack", 9, 1799, 75));
            products.add(new Product(4, 2, "Fancy Feast Wet Cat Food", "Fancy Feast Wet Cat Food for Adult Cats, Pack of 24 Cans, 85g each", 9, 2999, 25));
            products.add(new Product(5, 3, "ZuPreem FruitBlend Parrot Food", "ZuPreem FruitBlend Parrot Food with Natural Fruit Flavors, 2.25kg Pack", 4, 1999, 30));
            products.add(new Product(6, 3, "Kaytee Forti-Diet Canary Food", "Kaytee Forti-Diet Canary Food with Omega-3, 2lb Pack", 4, 799, 50));
            products.add(new Product(7, 4, "Oxbow Animal Health Hay", "Oxbow Animal Health Hay for Rabbits, Guinea Pigs, Chinchillas, 1kg Pack", 5, 999, 60));
            products.add(new Product(8, 4, "Living World Timothy Hay", "Living World Timothy Hay for Small Animals, 1kg Pack", 5, 899, 40));
            products.add(new Product(9, 5, "Bayer K9 Advantix II", "Bayer K9 Advantix II for Medium Dogs, 4-Month Supply", 2, 3999, 20));
            products.add(new Product(10, 5, "Hartz UltraGuard Flea & Tick Collar", "Hartz UltraGuard Flea & Tick Collar for Cats and Kittens", 11, 299, 100));
            products.add(new Product(11, 1, "Pedigree Chicken Gravy", "Pedigree Chicken Gravy for Dogs, Pack of 15 Pouches, 80g each", 2, 549, 80));
            products.add(new Product(12, 1, "Me-O Cat Food", "Me-O Cat Food with Tuna and Chicken, 7kg Pack", 3, 1499, 60));
            products.add(new Product(13, 2, "Hikari Cichlid Gold Fish Food", "Hikari Cichlid Gold Fish Food, Mini Pellets, 250g Pack", 6, 1099, 30));
            products.add(new Product(14, 2, "TetraMin Tropical Flakes", "TetraMin Tropical Flakes for Fish, 200g Pack", 6, 899, 50));
            products.add(new Product(15, 3, "Zilla Reptile Munchies", "Zilla Reptile Munchies, Omnivore Mix, 113g Pack", 12, 499, 120));
            products.add(new Product(16, 3, "Zoo Med Repti Basking Spot Lamp", "Zoo Med Repti Basking Spot Lamp, 100W", 10, 1299, 25));
            products.add(new Product(17, 4, "Marshall Ferret Harness", "Marshall Ferret Harness with Bell, Red", 13, 699, 50));
            products.add(new Product(18, 4, "Tetrafauna ReptoHeat Basking Heater", "Tetrafauna ReptoHeat Basking Heater, 50W", 7, 1999, 20));
            products.add(new Product(19, 5, "KONG Classic Dog Toy", "KONG Classic Dog Toy, Red, Large", 13, 1299, 40));
            products.add(new Product(20, 5, "Hartz DuraPlay Ball", "Hartz DuraPlay Ball for Dogs, Large", 13, 799, 60));

            List<Customer> customers = new ArrayList<>();
            customers.add(new Customer(1, "John", "Doe", "johndoe@gmail.com", "hjk7$g@1", "123 Main St, Anytown USA"));
            customers.add(new Customer(2, "Jane", "Smith", "janesmith@gmail.com", "fgh3@h6", "456 Oak St, Anycity USA"));
            customers.add(new Customer(3, "Mark", "Johnson", "markjohnson@yahoo.com", "abc9&d$8", "789 Maple St, Anystate USA"));
            customers.add(new Customer(4, "Susan", "Lee", "susanlee@yahoo.com", "klm1#n@4", "321 Pine St, Anyville USA"));
            customers.add(new Customer(5, "David", "Brown", "davidbrown@gmail.com", "pqr5$t^7", "654 Elm St, Anymetropolis USA"));
            customers.add(new Customer(6, "Amanda", "Jones", "amandajones@yahoo.com", "xyz2*q%9", "987 Cedar St, Anyborough USA"));
            customers.add(new Customer(7, "Michael", "Davis", "michaeldavis@gmail.com", "uvw6$r^5", "246 Oak St, Anycity USA"));
            customers.add(new Customer(8, "Emily", "Wilson", "emilywilson@yahoo.com", "efg4!h$7", "135 Maple St, Anystate USA"));
            customers.add(new Customer(9, "James", "Miller", "jamesmiller@gmail.com", "nop8*r$3", "864 Pine St, Anyville USA"));
            customers.add(new Customer(10, "Sarah", "Taylor", "sarahtaylor@yahoo.com", "ijk2#l$9", "753 Elm St, Anymetropolis USA"));

            List<Review> reviews = new ArrayList<>();
            reviews.add(new Review(1, new Date(), 1, 1, 4, "Great product, my cat loves it!"));
            reviews.add(new Review(2, new Date(), 2, 2, 5, "Amazing service and high-quality products."));
            reviews.add(new Review(3, new Date(), 3, 3, 3, "Product was okay, but shipping took a long time."));
            reviews.add(new Review(4, new Date(), 4, 4, 2, "Product was damaged when it arrived, very disappointed."));
            reviews.add(new Review(5, new Date(), 5, 5, 4, "Happy with my purchase, will buy from this seller again."));
            reviews.add(new Review(6, new Date(), 6, 6, 5, "Fast shipping and great customer service."));
            reviews.add(new Review(7, new Date(), 7, 7, 3, "Product was not what I expected, but seller was responsive to my concerns."));
            reviews.add(new Review(8, new Date(), 8, 8, 4, "Good quality product, would recommend."));
            reviews.add(new Review(9, new Date(), 9, 9, 5, "My dog absolutely loves this toy!"));
            reviews.add(new Review(10, new Date(), 10, 10, 3, "Product was smaller than I expected, but otherwise good."));
            reviews.add(new Review(11, new Date(), 2, 3, 5, "Great product and fast shipping!"));
            reviews.add(new Review(12, new Date(), 3, 1, 4, "Good product, but wish it was cheaper."));
            reviews.add(new Review(13, new Date(), 1, 2, 3, "Product was okay, but my pet didn't seem to like it."));
            reviews.add(new Review(14, new Date(), 5, 4, 5, "Excellent product and customer service!"));
            reviews.add(new Review(15, new Date(), 4, 5, 2, "Product arrived late and damaged."));
            reviews.add(new Review(16, new Date(), 6, 7, 4, "Great quality product, worth the price."));
            reviews.add(new Review(17, new Date(), 7, 8, 3, "Product was not as advertised."));
            reviews.add(new Review(18, new Date(), 8, 9, 5, "Very satisfied with my purchase!"));
            reviews.add(new Review(19, new Date(), 9, 10, 4, "Good product, but could have been packaged better."));
            reviews.add(new Review(20, new Date(), 10, 6, 5, "Love this product and seller!"));

            List<Cart> carts = new ArrayList<>();
            carts.add(new Cart(1, 3, 2));
            carts.add(new Cart(1, 5, 1));
            carts.add(new Cart(2, 4, 3));
            carts.add(new Cart(3, 1, 2));
            carts.add(new Cart(3, 6, 1));
            carts.add(new Cart(4, 2, 4));
            carts.add(new Cart(4, 5, 2));
            carts.add(new Cart(5, 1, 1));
            carts.add(new Cart(5, 4, 2));
            carts.add(new Cart(5, 6, 1));
            carts.add(new Cart(6, 2, 1));
            carts.add(new Cart(6, 5, 3));
            carts.add(new Cart(6, 6, 2));
            carts.add(new Cart(7, 3, 2));
            carts.add(new Cart(7, 4, 1));
            carts.add(new Cart(7, 5, 1));
            carts.add(new Cart(7, 6, 3));
            carts.add(new Cart(8, 1, 2));
            carts.add(new Cart(8, 2, 1));
            carts.add(new Cart(8, 3, 1));
            carts.add(new Cart(8, 4, 3));
            carts.add(new Cart(9, 2, 1));
            carts.add(new Cart(9, 3, 2));
            carts.add(new Cart(9, 5, 1));
            carts.add(new Cart(10, 1, 3));
            carts.add(new Cart(10, 4, 1));
            carts.add(new Cart(10, 6, 2));

            initDB();
            insertSellers(sellers);
            insertCategories(categories);
            insertProducts(products);
            insertCustomers(customers);
            insertReviews(reviews);
            insertCarts(carts);
            checkout(1, "Cash");
            checkout(6, "Credit");
            checkout(7, "Debit");//
            System.out.println("Revenue by category");
            getRevenueByCategoryID(4);
            getRevenueByCategoryID(2);

            System.out.println("5 best selling products");
            getBestSellingProducts(5);

            System.out.println("5 best reviewed products");
            getBestReviewedProducts(5);

            System.out.println("1 and 2 star products (avg)");
            filterByAvgRating(1,2);

            System.out.println("Revenue earned today");
            Date today = new Date();
            getRevenueByDateRange(today, today);
            
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
                + "CREATE TABLE Category (id INT IDENTITY(1,1) PRIMARY KEY, name VARCHAR(20), parentCatID INT, FOREIGN KEY (parentCatID) REFERENCES Category(id)) "
                + "END";
        
        String sellerTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Seller' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Seller (id INT IDENTITY(1,1) PRIMARY KEY, name VARCHAR(20), email VARCHAR(30)) "
                + "END";

        String productTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Product' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Product (id INT IDENTITY(1,1) PRIMARY KEY, sellerID INT, name VARCHAR(60), description VARCHAR(500), categoryID INT, price INT, stock INT, FOREIGN KEY (categoryID) REFERENCES Category(id), FOREIGN KEY (sellerID) REFERENCES Seller(id)) "
                + "END";
        
        String cartTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Cart' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Cart (customerID INT, productID INT, quantity INT, PRIMARY KEY (customerID, productID), FOREIGN KEY (customerID) REFERENCES Customer(id), FOREIGN KEY (productID) REFERENCES Product(id)) "
                + "END";
        
        String purchaseTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Purchase' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Purchase (id INT IDENTITY(1,1) PRIMARY KEY, paymentType VARCHAR(20), totalAmount INT, date DATE, customerID INT, delivered BIT, FOREIGN KEY (customerID) REFERENCES Customer(id)) "
                + "END";
        
        String reviewTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Review' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Review (id INT IDENTITY(1,1) PRIMARY KEY, date DATE, productID INT, customerID INT, rating INT, description VARCHAR(500), FOREIGN KEY (productID) REFERENCES Product(id), FOREIGN KEY (customerID) REFERENCES Customer(id)) "
                + "END";

        String purchaseProductTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Purchase_Product' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Purchase_Product (purchaseID INT, productID INT, price INT, quantity INT, PRIMARY KEY (purchaseID, productID), FOREIGN KEY (purchaseID) REFERENCES Purchase(id), FOREIGN KEY (productID) REFERENCES Product(id)) "
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
        String purchaseProductTablePurchaseIDIndex = "CREATE INDEX idx_purchaseproduct_purchaseID ON Purchase_Product(purchaseID)";
        
        try {
            initDatabase.createTable(customerTableQuery);
            initDatabase.createTable(categoryTableQuery);
            initDatabase.createTable(sellerTableQuery);
            initDatabase.createTable(productTableQuery);
            initDatabase.createTable(cartTableQuery);
            initDatabase.createTable(purchaseTableQuery);
            initDatabase.createTable(reviewTableQuery);
            initDatabase.createTable(purchaseProductTableQuery);

            //initDatabase.createTrigger(catDeleteTrigger);

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

    private static void insertCategories(List<Category> categories) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
    
            CRUD<Category> categoryImp = new CategoryImp(connection);
    
            int totalInserted = 0;
    
            // create sellers and add them to the database
            for (Category category : categories) {
                int result = categoryImp.create(category);
                totalInserted += result;
            }
    
            System.out.println("Inserted " + totalInserted + " category(s).");
    
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

    private static void insertProducts(List<Product> products) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
    
            CRUD<Product> productImp = new ProductImp(connection);
    
            int totalInserted = 0;
    
            // create sellers and add them to the database
            for (Product product : products) {
                int result = productImp.create(product);
                totalInserted += result;
            }
    
            System.out.println("Inserted " + totalInserted + " products(s).");
    
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

    private static void insertCustomers(List<Customer> customers) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
    
            CRUD<Customer> customerImp = new CustomerImp(connection);
    
            int totalInserted = 0;
    
            // create sellers and add them to the database
            for (Customer product : customers) {
                int result = customerImp.create(product);
                totalInserted += result;
            }
    
            System.out.println("Inserted " + totalInserted + " products(s).");
    
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

    private static void insertReviews(List<Review> reviews) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
    
            CRUD<Review> reviewImp = new ReviewImp(connection);
    
            int totalInserted = 0;
    
            // create sellers and add them to the database
            for (Review review : reviews) {
                int result = reviewImp.create(review);
                totalInserted += result;
            }
    
            System.out.println("Inserted " + totalInserted + " products(s).");
    
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

    private static void insertCarts(List<Cart> carts) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction
    
            CRUDComposite<Cart> cartImp = new CartImp(connection);
    
            int totalInserted = 0;
    
            // create sellers and add them to the database
            for (Cart review : carts) {
                int result = cartImp.create(review);
                totalInserted += result;
            }
    
            System.out.println("Inserted " + totalInserted + " products(s).");
    
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
    

    private static void checkout(int customerID, String paymentType) throws SQLException {
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

    private static void updateProductStock(int productID, int stock) throws SQLException {

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

    private static void getRevenueByCategoryID(int categoryID) throws SQLException {

        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction

            PurchaseProductImp purchaseproductImp = new PurchaseProductImp(connection);
            int revenue = purchaseproductImp.getTotalRevenueByCategory(categoryID);
            System.out.println((double) revenue / 100);

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

    private static void getBestSellingProducts(int numProducts) throws SQLException {

        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction

            ProductImp productImp = new ProductImp(connection);
            List<Product> best_products = productImp.getBestSellingProducts(numProducts);
            
            for (Product prod : best_products) {
                System.out.println(prod);
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
    
    private static void getBestReviewedProducts(int numProducts) throws SQLException {

        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction

            ProductImp productImp = new ProductImp(connection);
            List<Product> best_products = productImp.getBestReviewedProducts(numProducts);
            
            for (Product prod : best_products) {
                System.out.println(prod);
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

    private static void filterByAvgRating(int minRating, int maxRating) throws SQLException {

        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction

            ProductImp productImp = new ProductImp(connection);
            List<Product> best_products = productImp.filterByAvgRating(minRating, maxRating);
            
            for (Product prod : best_products) {
                System.out.println(prod);
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
    
    private static void getRevenueByDateRange(Date startDate, Date endDate) throws SQLException {

        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // get the database connection
            connection.setAutoCommit(false); // start the transaction

            PurchaseProductImp purchaseProductImp = new PurchaseProductImp(connection);
            int revenue = purchaseProductImp.getRevenueByDateRange(startDate, endDate);
            
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
}
