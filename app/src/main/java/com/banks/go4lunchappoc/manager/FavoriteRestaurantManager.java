package com.banks.go4lunchappoc.manager;

import com.banks.go4lunchappoc.repository.FavoriteRestaurantRepository;
import com.banks.go4lunchappoc.repository.SelectedRestaurantRepository;

public class FavoriteRestaurantManager {

    private static volatile FavoriteRestaurantManager instance;
    private final FavoriteRestaurantRepository favoriteRestaurantRepository;

    private FavoriteRestaurantManager() {
        favoriteRestaurantRepository = FavoriteRestaurantRepository.getInstance();
    }

    public static FavoriteRestaurantManager getInstance() {
        FavoriteRestaurantManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized (FavoriteRestaurantRepository.class) {
            if (instance == null) {
                instance = new FavoriteRestaurantManager();
            }
            return instance;
        }
    }

    public void getFetchRestaurantId(String id) {
        favoriteRestaurantRepository.getFetchRestaurantId(id);
    }

    public void createFavoriteRestaurant(){
        favoriteRestaurantRepository.createFavoriteRestaurant();
    }

}
