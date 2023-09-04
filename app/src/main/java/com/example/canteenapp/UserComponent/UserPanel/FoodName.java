package com.example.canteenapp.UserComponent.UserPanel;

public class FoodName {


  public FoodName() {
  }

  String foodname;
  String count;

  public String getFoodname() {
    return foodname;
  }

//  //public void setFoodname(String foodname) {
//    this.foodname = foodname;
//  }

  public String getCount() {
    return count;
  }

  public void setCount(String count) {
    this.count = count;
  }

  public FoodName(String foodname, String count) {
    this.foodname = foodname;
    this.count = count;
  }
}
