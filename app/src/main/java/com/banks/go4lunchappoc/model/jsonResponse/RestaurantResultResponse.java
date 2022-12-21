package com.banks.go4lunchappoc.model.jsonResponse;

import java.util.List;

public class RestaurantResultResponse {

    private List<RestaurantResponse> results;

    // GETTER & SETTER
    public List<RestaurantResponse> getResults() {
        return results;
    }

    public void setResults(List<RestaurantResponse> results) {
        this.results = results;
    }

    // CONSTRUCTOR

    public RestaurantResultResponse(List<RestaurantResponse> results) {
        this.results = results;
    }
}
