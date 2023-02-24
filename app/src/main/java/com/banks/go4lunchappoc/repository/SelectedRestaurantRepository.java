package com.banks.go4lunchappoc.repository;

import android.util.Log;

import androidx.annotation.Nullable;

import com.banks.go4lunchappoc.model.SelectedRestaurant;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.PortUnreachableException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SelectedRestaurantRepository {

    private static volatile SelectedRestaurantRepository instance;
    private static final String SELECTED_RESTAURANTS_FIELD = "selected_restaurants";
    private static String restaurantID = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");


    private SelectedRestaurantRepository() {
    }

    public static SelectedRestaurantRepository getInstance() {
        SelectedRestaurantRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (SelectedRestaurantRepository.class) {
            if (instance == null) {
                instance = new SelectedRestaurantRepository();
            }
            return instance;
        }
    }

    //-----------
    // Get the Collection Reference
    //----------
    private CollectionReference getSelectedRestaurantCollection() {
        return FirebaseFirestore.getInstance().collection(SELECTED_RESTAURANTS_FIELD);
    }

    //-----------
    // Create Selected restaurant in Firestore
    //----------
    public void createSelectedRestaurant() {
        FirebaseUser user = getCurrentUser();
        if (user != null && restaurantID != null) {
            String restaurantId = restaurantID;
            String userId = user.getUid();
            String dateDeJour = sdf.format(new Date());

            SelectedRestaurant selectedRestaurantToCreate = new SelectedRestaurant(restaurantId, userId, dateDeJour);

            this.getSelectedRestaurantCollection().add(selectedRestaurantToCreate);

        }

    }


    //-----------
    // Get  the user currently logged in
    //----------
    @Nullable
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }


    public void fetchRestaurantIdManager(String id) {
        restaurantID = id;
    }




    // -----------------
    // GET THE ALL USER SELECTED A RESTAURANT
    // -----------------
    public Task<QuerySnapshot> getAllUserSelectedRestaurantWithIdData() {
        String dateDuJour = sdf.format(new Date());
        return getSelectedRestaurantCollection()
                .whereEqualTo("restaurantId", restaurantID)
                .whereEqualTo("dateSelected", dateDuJour)
                .get();


    }

    // Get the currently logged in user
    public Task<QuerySnapshot> getSelectedRestaurantForCurrentUser() {
        FirebaseUser user = getCurrentUser();
        String dateDuJour = sdf.format(new Date());
        if (user == null) {
            // User is not authenticated
            return null;
        }

        // Build the query to get the selected restaurant
        return getSelectedRestaurantCollection()
                .whereEqualTo("userId", user.getUid())
                .whereEqualTo("dateSelected", dateDuJour)
                .get();
    }

    // .whereEqualTo("dateSelected", dateToday())

    // -----------------
    // GET THE ALL SELECTED RESTAURANTS
    // -----------------
    public Task<QuerySnapshot> getAllSelectedRestaurantsData() {
        return getSelectedRestaurantCollection().get();
    }


}
