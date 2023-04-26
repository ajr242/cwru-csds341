package com.cwru.petecommerce;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner; 

import com.cwru.petecommerce.dao.abstraction.CRUD;
import com.cwru.petecommerce.dao.implementation.ProductImp;

import com.cwru.petecommerce.models.Cart;
import com.cwru.petecommerce.models.Category;
import com.cwru.petecommerce.models.Customer;
import com.cwru.petecommerce.models.Product;
import com.cwru.petecommerce.models.Purchase;
import com.cwru.petecommerce.models.PurchaseProduct;
import com.cwru.petecommerce.models.Review;
import com.cwru.petecommerce.models.Seller;

import com.cwru.petecommerce.utils.InitDatabase;

public class App {
    public static void main(String[] args) {
        System.out.println("Begin");

        try {
            initDB();
            insertProduct();
            insertProductUI();
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
                + "CREATE TABLE Category (id INT IDENTITY(1,1) PRIMARY KEY, name VARCHAR(20), parentCatID INT, FOREIGN KEY (parentCatID) REFERENCES Category(id)) "
                + "END";
        
        String sellerTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Seller' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Seller (id INT IDENTITY(1,1) PRIMARY KEY, name VARCHAR(20), email VARCHAR(30)) "
                + "END";

        String productTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Product' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Product (id INT IDENTITY(1,1) PRIMARY KEY, sellerID INT, name VARCHAR(20), description VARCHAR(500), categoryID INT, price INT, stock INT, FOREIGN KEY (categoryID) REFERENCES Category(id), FOREIGN KEY (sellerID) REFERENCES Seller(id)) "
                + "END";
        
        String cartTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Cart' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Cart (customerID INT, productID INT, quantity INT, FOREIGN KEY (customerID) REFERENCES Customer(id), FOREIGN KEY (productID) REFERENCES Product(id)) "
                + "END";
        
        String purchaseTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Purchase' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Purchase (id INT IDENTITY(1,1) PRIMARY KEY, paymentType VARCHAR(20), totalAmount INT, date DATE, customerID INT, delivered BIT, FOREIGN KEY (customerID) REFERENCES Customer(id)) "
                + "END";
        
        String reviewTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Review' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Review (id INT IDENTITY(1,1) PRIMARY KEY, date VARCHAR(20), productID INT, customerID INT, rating INT, description VARCHAR(500), FOREIGN KEY (productID) REFERENCES Product(id), FOREIGN KEY (customerID) REFERENCES Customer(id)) "
                + "END";
        
        String purchaseProductTableQuery = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Purchase_Product' AND xtype='U') "
                + "BEGIN "
                + "CREATE TABLE Purchase_Product (purchaseID INT, productID INT, price INT, quantity INT, FOREIGN KEY (purchaseID) REFERENCES Purchase(id), FOREIGN KEY (productID) REFERENCES Product(id)) "
                + "END";
        
        try {
            initDatabase.createTable(customerTableQuery);
            initDatabase.createTable(categoryTableQuery);
            initDatabase.createTable(sellerTableQuery);
            initDatabase.createTable(productTableQuery);
            initDatabase.createTable(cartTableQuery);
            initDatabase.createTable(purchaseTableQuery);
            initDatabase.createTable(reviewTableQuery);
            initDatabase.createTable(purchaseProductTableQuery);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // Methods for Product
    private static void insertProduct() throws SQLException {

        Product dogFood = new Product(0, null, "Dog Food", "Yummy", null, 10000, 2);

        CRUD<Product> productImp = new ProductImp();

        int result = productImp.create(dogFood);

        System.out.println(result);
    }

    private static void insertProductUI() throws SQLException {
        Scanner myObj = new Scanner(System.in);
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
        CRUD<Product> productImp = new ProductImp();
        int result = productImp.create(x);
        System.out.println(result);
    }

    private static void retrieveProductById() throws SQLException {
        ProductImp productImp = new ProductImp();

        Optional<Product> optionalProduct = productImp.getById(32);

        if (optionalProduct.isPresent()) {
            System.out.println("Product found");
            Product product = optionalProduct.get();
            System.out.println(product);
        } else {
            System.out.println("Product not found");
        }

    }
    
    private static void retrieveAllProduct() throws SQLException {
        ProductImp productImp = new ProductImp();

        List<Product> products = productImp.getAll();

        for (Product product : products) {
            System.out.println(product);
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
