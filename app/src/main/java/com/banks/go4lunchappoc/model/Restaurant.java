package com.banks.go4lunchappoc.model;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private String id;
    private String restaurantName;
    private Double latitude;
    private Double longitude;
    private String urlPictureRestaurant;
    private String restaurantAddress;
    private Boolean openingHours;
    private Float rating;
    private int distanceKm;
    private String numberPhone;
    private String email;

    public Restaurant(String id, String restaurantName, Double latitude, Double longitude,
                      String urlPictureRestaurant, String restaurantAddress, Boolean openingHours,
                      Float rating, int distanceKm) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.urlPictureRestaurant = urlPictureRestaurant;
        this.restaurantAddress = restaurantAddress;
        this.openingHours = openingHours;
        this.rating = rating;
        this.distanceKm = distanceKm;
    }

    public Restaurant(String id, String restaurantName, Double latitude, Double longitude, String urlPictureRestaurant, String restaurantAddress, Boolean openingHours, Float rating, int distanceKm, String numberPhone, String email) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.urlPictureRestaurant = urlPictureRestaurant;
        this.restaurantAddress = restaurantAddress;
        this.openingHours = openingHours;
        this.rating = rating;
        this.distanceKm = distanceKm;
        this.numberPhone = numberPhone;
        this.email = email;
    }

    // --- GETTERS ---
    public String getId() {
        return id;
    }

    public String getUrlPictureRestaurant() {
        return urlPictureRestaurant;
    }

    public int getDistanceKm() {
        return distanceKm;
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public Boolean getOpeningHours() {
        return openingHours;
    }

    public Float getRating() {
        return rating;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getEmail() {
        return email;
    }


    // --- SETTERS ---
    public void setId(String id) {
        this.id = id;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public void setOpeningHours(Boolean openingHours) {
        this.openingHours = openingHours;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setDistanceKm(int distanceKm) {
        this.distanceKm = distanceKm;
    }

    public void setUrlPictureRestaurant(String urlPictureRestaurant) {
        this.urlPictureRestaurant = urlPictureRestaurant;
    }


}
