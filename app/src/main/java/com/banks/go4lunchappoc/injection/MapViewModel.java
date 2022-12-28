package com.banks.go4lunchappoc.injection;

import androidx.lifecycle.LiveData;

import com.banks.go4lunchappoc.model.restaurant.Restaurant;
import com.banks.go4lunchappoc.repository.RestaurantRepository;

import java.util.List;

public class MapViewModel {

    RestaurantRepository restaurantRepository = new RestaurantRepository();


    public LiveData<List<Restaurant>> getMapLiveData(){
        return restaurantRepository.getRestaurantLiveData();
    }

    public void fetchMapViewModel(Double latitude, Double longitude){
        restaurantRepository.fetchRestaurant(latitude,longitude);
    }


}
