package com.example.canteenapp;

public class Foodcount {
    public Foodcount(String foodname, String count) {
        this.foodname = foodname;
        this.count = count;
    }

    public Foodcount() {
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    String foodname;


    public void setCount(String count) {
        this.count = count;
    }

    String count;


    public String getCount() {
        return count;
    }


}
