package com.banks.go4lunchappoc.injection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.banks.go4lunchappoc.model.restaurant.Restaurant;
import com.banks.go4lunchappoc.repository.RestaurantRepository;

import java.util.List;

public class ListRestaurantViewModel extends ViewModel {

    RestaurantRepository restaurantRepository = new RestaurantRepository();

    public LiveData<List<Restaurant>> getRestaurantLiveData(){
        return restaurantRepository.getRestaurantLiveData();
    }

    public void fetchAllRestaurantsViewModel(Double latitude, Double longitude){
        restaurantRepository.fetchRestaurant(latitude,longitude);
    }
}
