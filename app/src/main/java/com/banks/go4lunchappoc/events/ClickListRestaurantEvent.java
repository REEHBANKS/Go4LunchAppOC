package com.banks.go4lunchappoc.events;

import com.banks.go4lunchappoc.model.restaurant.Restaurant;

public class ClickListRestaurantEvent {

    public Restaurant restaurant;

    public ClickListRestaurantEvent(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
