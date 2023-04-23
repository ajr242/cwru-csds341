package com.cwru.petecommerce.models;

public class PurchaseProduct {
    private int purchaseID;
    private int productID;
    private int price;
    private int quantity;

    public PurchaseProduct(int purchaseID, int productID, int price, int quantity) {
        this.purchaseID = purchaseID;
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PurchaseProduct [purchaseID=" + purchaseID + ", productID=" + productID + ", price=" + price
                + ", quantity=" + quantity + "]";
    }
}
