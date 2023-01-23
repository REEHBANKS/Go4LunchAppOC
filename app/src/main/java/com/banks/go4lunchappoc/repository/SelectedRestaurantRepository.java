package com.banks.go4lunchappoc.repository;

import android.util.Log;

import androidx.annotation.Nullable;

import com.banks.go4lunchappoc.model.SelectedRestaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class SelectedRestaurantRepository {

    private static volatile SelectedRestaurantRepository instance;
    private static final String SELECTED_RESTAURANTS_FIELD = "selected_restaurants";
    private static  String restaurantID = null;




    private SelectedRestaurantRepository() { }

    public static SelectedRestaurantRepository getInstance() {
        SelectedRestaurantRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(SelectedRestaurantRepository.class) {
            if (instance == null) {
                instance = new SelectedRestaurantRepository();
            }
            return instance;
        }
    }

    //-----------
    // Get the Collection Reference
    //----------
    private CollectionReference getSelectedRestaurantCollection(){
        return FirebaseFirestore.getInstance().collection(SELECTED_RESTAURANTS_FIELD);
    }

    //-----------
    // Create Selected restaurant in Firestore
    //----------
    public void createSelectedRestaurant() {
        FirebaseUser user = getCurrentUser();
        if(user != null && restaurantID != null ) {
            String restaurantId = restaurantID;
            String userId = user.getUid();

            SelectedRestaurant selectedRestaurantToCreate = new SelectedRestaurant(restaurantId,userId);

            this.getSelectedRestaurantCollection().document(restaurantId).set(selectedRestaurantToCreate);

        }

    }


    //-----------
    // Get  the user currently logged in
    //----------
    @Nullable
    public FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }


    public void  fetchRestaurantIdManager(String id){
        restaurantID =id;

    }


}
