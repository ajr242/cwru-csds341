package com.cwru.petecommerce.models;

public class Seller {
    private int id;
    private String name;
    private String email;

    public Seller(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Seller [id=" + id + ", name=" + name + ", email=" + email + "]";
    }
}

