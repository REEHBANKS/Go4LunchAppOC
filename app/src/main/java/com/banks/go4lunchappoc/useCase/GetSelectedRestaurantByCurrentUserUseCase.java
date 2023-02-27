package com.banks.go4lunchappoc.useCase;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.SelectedRestaurant;
import com.banks.go4lunchappoc.repository.RestaurantRepository;
import com.banks.go4lunchappoc.repository.SelectedRestaurantRepository;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class GetSelectedRestaurantByCurrentUserUseCase {

    // Singleton instance
    private static volatile GetSelectedRestaurantByCurrentUserUseCase instance;

    // Repositories
    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    SelectedRestaurantRepository selectedRestaurantRepository = SelectedRestaurantRepository.getInstance();

    // Lists and models
    List<Restaurant> restaurantList = new ArrayList<>();
    SelectedRestaurant restaurantSelectedByCurrentUser = new SelectedRestaurant();
    Restaurant restaurantOfCurrentUser = new Restaurant();

    // Private constructor for singleton pattern
    public GetSelectedRestaurantByCurrentUserUseCase() {}

    /**
     * Get the singleton instance of the use case.
     */
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

    /**
     * Observe the list of restaurants for changes.
     *
     * @param lifecycleOwner The lifecycle owner to attach the observer to.
     */
    public void observeRestaurants(LifecycleOwner lifecycleOwner) {
        restaurantRepository.getRestaurantLiveData().observe(lifecycleOwner, restaurants -> {
            restaurantList = restaurants;
            getRestaurantSelectedByCurrentUser();
        });
    }


    /**
     * Get the restaurant selected by the current user.
     */
    public void getRestaurantSelectedByCurrentUser(){
        selectedRestaurantRepository.getSelectedRestaurantForCurrentUser()
                .addOnCompleteListener(task -> {
                    if(task.isComplete()){
                        // Get the selected restaurant from the document
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            restaurantSelectedByCurrentUser = document.toObject(SelectedRestaurant.class);
                        }
                        // Once the selected restaurant is retrieved, get the corresponding restaurant
                        getRestaurantOfByCurrentUser();
                    }
                });
    }

    /**
     * Get the restaurant corresponding to the selected restaurant of the current user.
     */
    public void getRestaurantOfByCurrentUser(){
        for (Restaurant restaurant : restaurantList){
            if(restaurant.getId().equals(restaurantSelectedByCurrentUser.getRestaurantId())){
                restaurantOfCurrentUser = restaurant;
                Log.d("ListSelected", restaurant.getRestaurantName() + " " + restaurant.getRestaurantAddress());
            }
        }
    }
}


