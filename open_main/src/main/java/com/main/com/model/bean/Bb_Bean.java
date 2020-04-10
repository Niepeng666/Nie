package com.main.com.model.bean;

import android.widget.CheckBox;

public class Bb_Bean {
    private int id;
    private String name;
    private String imageView;
    private double price;
    private boolean check;

    public Bb_Bean() {
        this.id = id;
        this.name = name;
        this.imageView = imageView;
        this.price = price;
        this.check = check;
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

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
