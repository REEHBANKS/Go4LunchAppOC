package com.banks.go4lunchappoc.model;

import java.io.Serializable;

public class RestaurantScreen implements Serializable {

    private Restaurant restaurant;

    private int numberUser;

    public RestaurantScreen(Restaurant restaurant, int numberUser) {
        this.restaurant = restaurant;
        this.numberUser = numberUser;
    }

    public int getNumberUser() {
        return numberUser;
    }

    public void setNumberUser(int numberUser) {
        this.numberUser = numberUser;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
