package com.banks.go4lunchappoc.model;

public class UserScreen {

    private String username;
    private String restaurantName;
    private String urlPictureUser;

    public UserScreen(User user, Restaurant restaurant) {
        this.username = user.getUsername();
        this.restaurantName = restaurant.getRestaurantName();
        this.urlPictureUser = user.getUrlPictureUser();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setUrlPictureUser(String urlPictureUser) {
        this.urlPictureUser = urlPictureUser;
    }

    public String getUrlPictureUser() {
        return urlPictureUser;
    }

    public String getUsername() {
        return username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

}
