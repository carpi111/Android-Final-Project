package com.chapman.dev.vincecarpino.final_project;

public class Product {
    private int id;
    private String name;
    private String description;
    private int categoryId;
    private int sellerId;
    private float price;

    public Product() { }

    public Product(String name, String description, int categoryId, int sellerId, float price) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.price = price;
    }

    public Product(int id, String name, String description, int categoryId, int sellerId, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
