package com.banks.go4lunchappoc.injection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.repository.RestaurantRepository;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ListRestaurantViewModel extends ViewModel {

    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();

    public LiveData<List<Restaurant>> getRestaurantLiveData() {
        return restaurantRepository.getRestaurantLiveData();
    }

    public LiveData<Restaurant> getOneRestaurantForTheSearchLiveData() {
        return restaurantRepository.getOneRestaurantLiveData();
    }

    public LiveData<Restaurant> getOneRestaurantLiveData() {
        return restaurantRepository.getOneRestaurantLiveData();
    }

    public void fetchAllRestaurantsViewModel(Double latitude, Double longitude) {
        restaurantRepository.fetchRestaurant(latitude, longitude);
    }

    public void fetchOneRestaurantForTheSearchViewModel(LatLng latLng, String id, Float rating) {
        restaurantRepository.fetchOneRestaurant(latLng, id, rating);
    }

    public void fetchOneRestaurantViewModel(LatLng latLng, String id, Float rating) {
        restaurantRepository.fetchOneRestaurant(latLng, id, rating);
    }
}
