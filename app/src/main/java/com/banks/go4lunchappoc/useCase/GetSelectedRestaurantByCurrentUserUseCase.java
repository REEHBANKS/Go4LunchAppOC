package com.banks.go4lunchappoc.useCase;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.SelectedRestaurant;
import com.banks.go4lunchappoc.repository.RestaurantRepository;
import com.banks.go4lunchappoc.repository.SelectedRestaurantRepository;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetSelectedRestaurantByCurrentUserUseCase {

    private static volatile GetSelectedRestaurantByCurrentUserUseCase instance;
    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    List<Restaurant> restaurantList = new ArrayList<>();
    SelectedRestaurantRepository selectedRestaurantRepository = SelectedRestaurantRepository.getInstance();
    SelectedRestaurant restaurantSelectedByCurrentUser = new SelectedRestaurant();
    Restaurant restaurantOfCurrentUser = new Restaurant();


    public static GetSelectedRestaurantByCurrentUserUseCase getInstance() {
        GetSelectedRestaurantByCurrentUserUseCase result = instance;
        if (result != null) {
            return result;
        }
        synchronized (GetSelectedRestaurantByCurrentUserUseCase.class) {
            if (instance == null) {
                instance = new GetSelectedRestaurantByCurrentUserUseCase();
            }
            return instance;
        }
    }

    public void observeRestaurants(LifecycleOwner lifecycleOwner) {
        restaurantRepository.getRestaurantLiveData().observe(lifecycleOwner, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {

                restaurantList = restaurants;
                getRestaurantSelectedByCurrentUser();
            }
        });
    }

    // -----------------
    // GET THE RESTAURANT SELECTED BY CURRENT USER
    // -----------------

    public void getRestaurantSelectedByCurrentUser(){
        selectedRestaurantRepository.getSelectedRestaurantForCurrentUser()
                .addOnCompleteListener(task -> {
                    if(task.isComplete()){

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            restaurantSelectedByCurrentUser = document.toObject(SelectedRestaurant.class);
                        }
                        getRestaurantOfByCurrentUser();
                    }
                });
    }

    public void getRestaurantOfByCurrentUser(){

        for (Restaurant restaurant : restaurantList){
            if(restaurant.getId().equals(restaurantSelectedByCurrentUser.getRestaurantId())){

                restaurantOfCurrentUser = restaurant;
                Log.d("ListSelected", restaurant.getRestaurantName() + " " + restaurant.getRestaurantAddress());
            }

        }

    }
}


