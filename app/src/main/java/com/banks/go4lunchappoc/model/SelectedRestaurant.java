package com.banks.go4lunchappoc.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class SelectedRestaurant {

    private String restaurantId;
    private String userId;
    private Date dateSelected;



    public SelectedRestaurant() { }

    public SelectedRestaurant (String restaurantId,String userId) {
        this.restaurantId = restaurantId;
        this.userId = userId;

    }

    // --- GETTERS ---
    public String getRestaurantId() {return restaurantId;}
    public String getUserId() {return userId;}
    @ServerTimestamp  public Date getDateSelected() {return dateSelected;}

    // --- SETTERS ---
    public void setRestaurantId(String restaurantId) {this.restaurantId = restaurantId;}
    public void setUserId(String userId) {this.userId = userId;}
    public void setDateSelected(Date dateSelected) {this.dateSelected = dateSelected;}
}
