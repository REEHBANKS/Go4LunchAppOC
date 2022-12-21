package com.banks.go4lunchappoc.model.jsonResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantResponse {


    private String place_id;
    private String name;
    private String vicinity;
    private Float rating;

    //we make object of class geometry, photos, opening_hours
    @SerializedName("geometry")
    private GeometryResponse geometryResponse;

    @SerializedName("photos")
    private List<PhotosResponse> photosResponse;

    @SerializedName("opening_hours")
    private OpeningResponse openingResponse;

    public RestaurantResponse(String place_id, String name, String vicinity, Float rating,
                              GeometryResponse geometryResponse, List <PhotosResponse> photosResponse,
                              OpeningResponse openingResponse) {
        this.place_id = place_id;
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
        this.geometryResponse = geometryResponse;
        this.photosResponse = photosResponse;
        this.openingResponse = openingResponse;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public GeometryResponse getGeometryResponse() {
        return geometryResponse;
    }

    public void setGeometryResponse(GeometryResponse geometryResponse) {
        this.geometryResponse = geometryResponse;
    }

    public List <PhotosResponse> getPhotosResponse() {
        return photosResponse;
    }

    public void setPhotosResponse(List <PhotosResponse> photosResponse) {
        this.photosResponse = photosResponse;
    }

    public OpeningResponse getOpeningResponse() {
        return openingResponse;
    }

    public void setOpeningResponse(OpeningResponse openingResponse) {
        this.openingResponse = openingResponse;
    }
}
