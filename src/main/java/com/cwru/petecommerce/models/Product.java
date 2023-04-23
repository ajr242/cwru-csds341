package com.cwru.petecommerce.models;

public class Product {
    private int id;
    private int sellerID;
    private String name;
    private String description;
    private int categoryID;
    private int price;
    private int stock;
    
    public Product(int id, int sellerID, String name, String description, int categoryID, int price, int stock) {
        this.id = id;
        this.sellerID = sellerID;
        this.name = name;
        this.description = description;
        this.categoryID = categoryID;
        this.price = price;
        this.stock = stock;
    }
    
    // Getters and setters

    public int getId() {
        return id;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    @Override
    public String toString() {
        return "Product [id=" + id + ", sellerID=" + sellerID + ", name=" + name + ", description=" + description + ", categoryID=" + categoryID + ", price=" + price + ", stock=" + stock + "]";
    }
}
