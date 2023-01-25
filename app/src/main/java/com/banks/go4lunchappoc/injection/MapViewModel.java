package com.banks.go4lunchappoc.injection;

import androidx.lifecycle.LiveData;

import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.repository.RestaurantRepository;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MapViewModel {

    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();


    public LiveData<List<Restaurant>> getMapLiveData() {
        return restaurantRepository.getRestaurantLiveData();
    }

    public LiveData<Restaurant> getOneMapLiveData() {
        return restaurantRepository.getOneRestaurantLiveData();
    }

    public void fetchMapViewModel(Double latitude, Double longitude) {
        restaurantRepository.fetchRestaurant(latitude, longitude);
    }

    public void fetchOneMapViewModel(LatLng latLng, String id, Float rating) {
        restaurantRepository.fetchOneRestaurant(latLng, id, rating);
    }

}
