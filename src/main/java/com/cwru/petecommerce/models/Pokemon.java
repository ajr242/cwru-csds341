package com.cwru.petecommerce.models;

public class Pokemon {
    private int id;
    private String name;
    private String category;
    private float weight;

    public Pokemon(int id, String name, String category, float weight) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.weight = weight;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Pokemon [id=" + id + ", name=" + name + ", category=" + category + ", weight=" + weight + "]";
    }

}
