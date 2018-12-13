package com.chapman.dev.vincecarpino.final_project;

public class Product {
    private int id;
    private String name;
    private String description;
    private int categoryId;
    private int sellerId;
    private int buyerId;
    private float price;
    private int isSold;

    public Product() { }

    public Product(String name, String description, int categoryId, int sellerId, int buyerId, float price) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.price = price;
        this.isSold = 0;
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

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getIsSold() {
        return isSold;
    }
}
