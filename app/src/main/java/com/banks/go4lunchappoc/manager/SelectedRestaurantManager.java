package com.banks.go4lunchappoc.manager;

import com.banks.go4lunchappoc.repository.SelectedRestaurantRepository;

public class SelectedRestaurantManager {

    private static volatile SelectedRestaurantManager instance;
    private final SelectedRestaurantRepository selectedRestaurantRepository;

    private SelectedRestaurantManager() {selectedRestaurantRepository = SelectedRestaurantRepository.getInstance();
    }

    public static SelectedRestaurantManager getInstance() {
        SelectedRestaurantManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized (SelectedRestaurantRepository.class) {
            if (instance == null) {
                instance = new SelectedRestaurantManager();
            }
            return instance;
        }
    }

    public void fetchRestaurantIdManager(String id){
        selectedRestaurantRepository.fetchRestaurantIdManager(id);
    }

    public void createSelectedRestaurant() {
        selectedRestaurantRepository.createSelectedRestaurant();
    }


}
