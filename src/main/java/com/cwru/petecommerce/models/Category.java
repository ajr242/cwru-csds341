package com.cwru.petecommerce.models;

public class Category {
    private int id;
    private String name;
    private int parentCatID;
    
    public Category(int id, String name, int parentCatID) {
        this.id = id;
        this.name = name;
        this.parentCatID = parentCatID;
    }
    
    // Getters and setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getParentCatID() {
        return parentCatID;
    }
    
    public void setParentCatID(int parentCatID) {
        this.parentCatID = parentCatID;
    }
    
    // toString method
    
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", parentCatID=" + parentCatID + "]";
    }
}
