package com.chuck.android.firebase_foodie.models;

public class Topping {
    private String toppingName;
    private String imgSrc;
    private Double price;


    public Topping(String toppingName, String imgSrc, Double price) {
        this.toppingName = toppingName;
        this.imgSrc = imgSrc;
        this.price = price;
    }
    public Topping(){}

    public String getToppingName() {
        return toppingName;
    }

    public void setToppingName(String toppingName) {
        this.toppingName = toppingName;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
