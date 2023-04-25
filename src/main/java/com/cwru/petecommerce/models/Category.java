package com.cwru.petecommerce.models;

public class Category {
    private int id;
    private String name;
    private Integer parentCatID;
    
    public Category(int id, String name, Integer parentCatID) {
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
    
    public Integer getParentCatID() {
        return parentCatID;
    }
    
    public void setParentCatID(Integer parentCatID) {
        this.parentCatID = parentCatID;
    }
    
    // toString method
    
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", parentCatID=" + parentCatID + "]";
    }
}
