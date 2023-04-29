package com.cwru.petecommerce.models;

import java.util.Date;

public class Review {
    private int id;
    private Date date;
    private int productID;
    private int customerID;
    private int rating;
    private String description;

    public Review(int id, Date date, int productID, int customerID, int rating, String description) {
        this.id = id;
        this.date = date;
        this.productID = productID;
        this.customerID = customerID;
        this.rating = rating;
        this.description = description;
    }

    // Getters and setters

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // toString

    @Override
    public String toString() {
        return "Review [id=" + id + ", date=" + date + ", productID=" + productID + ", customerID=" + customerID + ", rating=" + rating + ", description=" + description + "]";
    }
}

