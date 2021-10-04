package com.example.mad_group_project;

import androidx.lifecycle.LiveData;

public class FoodCart {

    String name, description, foodImage, price, quantity ,finalPrice;

    FoodCart() {

    }

    public FoodCart(String name, String description, String foodImage, String price, String quantity, String finalPrice) {
        this.name = name;
        this.description = description;
        this.foodImage = foodImage;
        this.price = price;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
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

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


}