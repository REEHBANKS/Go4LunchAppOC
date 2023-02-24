package com.banks.go4lunchappoc.repository;

import androidx.annotation.Nullable;

import com.banks.go4lunchappoc.model.FavoriteRestaurant;
import com.banks.go4lunchappoc.model.SelectedRestaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FavoriteRestaurantRepository {
    private static volatile FavoriteRestaurantRepository instance;
    private static final String FAVORITE_RESTAURANTS_FIELD = "favorite_restaurants";
    private static String restaurantID = null;


    private FavoriteRestaurantRepository() {
    }

    public static FavoriteRestaurantRepository getInstance() {
        FavoriteRestaurantRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (FavoriteRestaurantRepository.class) {
            if (instance == null) {
                instance = new FavoriteRestaurantRepository();
            }
            return instance;
        }
    }

    //-----------
    // Get the Collection Reference
    //----------
    private CollectionReference getFavoriteRestaurantCollection() {
        return FirebaseFirestore.getInstance().collection(FAVORITE_RESTAURANTS_FIELD);
    }

    //-----------
    // Create Favorite restaurant in Firestore
    //----------
    public void createFavoriteRestaurant() {
        FirebaseUser user = getCurrentUser();
        if (user != null && restaurantID != null) {
            String restaurantId = restaurantID;
            String userId = user.getUid();

            FavoriteRestaurant favoriteRestaurantToCreate = new FavoriteRestaurant(restaurantId, userId);

            this.getFavoriteRestaurantCollection().document(restaurantId).set(favoriteRestaurantToCreate);

        }
    }

    //-----------
    // Get  the user currently logged in
    //----------
    @Nullable
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }


    public void getFetchRestaurantId(String id) {
        restaurantID = id;
    }
}
