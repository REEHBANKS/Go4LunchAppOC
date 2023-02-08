package com.banks.go4lunchappoc.events;

import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.RestaurantScreen;

public class ClickListRestaurantEvent {

    public RestaurantScreen restaurant;

    public ClickListRestaurantEvent(RestaurantScreen restaurant) {
        this.restaurant = restaurant;
    }
}
