package com.cwru.petecommerce.models;

public class Cart {
    private int customerID;
    private int productID;
    private int quantity;
    
    public Cart(int customerID, int productID, int quantity) {
        this.customerID = customerID;
        this.productID = productID;
        this.quantity = quantity;
    }
    
    // Getters and setters
    
    public int getCustomerID() {
        return customerID;
    }
    
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    public int getProductID() {
        return productID;
    }
    
    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    // toString method
    
    @Override
    public String toString() {
        return "Cart [customerID=" + customerID + ", productID=" + productID + ", quantity=" + quantity + "]";
    }
}
