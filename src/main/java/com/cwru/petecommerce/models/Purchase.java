package com.cwru.petecommerce.models;

import java.util.Date;

public class Purchase {
    private int id;
    private String paymentType;
    private int totalAmount;
    private Date date;
    private int customerID;
    private boolean delivered;
    
    public Purchase(int id, String paymentType, int totalAmount, Date date, int customerID, boolean delivered) {
        this.id = id;
        this.paymentType = paymentType;
        this.totalAmount = totalAmount;
        this.date = date;
        this.customerID = customerID;
        this.delivered = delivered;
    }
    
    // Getters and setters
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    
    public int getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
        
    public int getCustomerID() {
        return customerID;
    }
    
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    public boolean isDelivered() {
        return delivered;
    }
    
    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
    
    // toString method
    
    @Override
    public String toString() {
        return "Purchase [id=" + id + ", paymentType=" + paymentType + ", totalAmount=" + totalAmount + ", date=" + date + ", customerID=" + customerID + ", delivered=" + delivered + "]";
    }
}

