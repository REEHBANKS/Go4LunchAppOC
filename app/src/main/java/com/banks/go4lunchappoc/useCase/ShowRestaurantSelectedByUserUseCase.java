package com.banks.go4lunchappoc.useCase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.SelectedRestaurant;
import com.banks.go4lunchappoc.model.User;
import com.banks.go4lunchappoc.model.UserScreen;
import com.banks.go4lunchappoc.repository.RestaurantRepository;
import com.banks.go4lunchappoc.repository.SelectedRestaurantRepository;
import com.banks.go4lunchappoc.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowRestaurantSelectedByUserUseCase {

    private static volatile ShowRestaurantSelectedByUserUseCase instance;

    private final UserRepository userRepository;
    private final SelectedRestaurantRepository selectedRestaurantRepository;
    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    List<SelectedRestaurant> listAllSelectedRestaurants = new ArrayList<>();
    List<User> listAllUsers = new ArrayList<>();
    List<Restaurant> restaurantList = new ArrayList<>();
    private final MutableLiveData<List<UserScreen>> selectedRestaurantsLiveData = new MutableLiveData<>();



    public ShowRestaurantSelectedByUserUseCase() {
        userRepository = UserRepository.getInstance();
        selectedRestaurantRepository = SelectedRestaurantRepository.getInstance();

    }


    public static ShowRestaurantSelectedByUserUseCase getInstance() {
        ShowRestaurantSelectedByUserUseCase result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ShowRestaurantSelectedByUserUseCase.class) {
            if (instance == null) {
                instance = new ShowRestaurantSelectedByUserUseCase();
            }
            return instance;
        }
    }

    public void observeRestaurants(LifecycleOwner lifecycleOwner) {
        restaurantRepository.getRestaurantLiveData().observe(lifecycleOwner, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {

                    restaurantList = restaurants;

                getAllUser();
            }
        });
    }

    private void getAllUser() {
        userRepository.getAllUserData()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                listAllUsers.add(user);
                            }
                            for (User user : listAllUsers) {
                                Log.d("UserList", user.getUsername());
                            }
                            getAllSelectedRestaurantsData();

                        }
                    }
                });
    }

    // -----------------
    // GET THE ALL USER SELECTED A RESTAURANT
    // -----------------
    private void getAllSelectedRestaurantsData() {
        selectedRestaurantRepository.getAllSelectedRestaurantsData()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            SelectedRestaurant selectedRestaurant = document.toObject(SelectedRestaurant.class);
                            listAllSelectedRestaurants.add(selectedRestaurant);
                        }
                        for (SelectedRestaurant selectedRestaurant : listAllSelectedRestaurants){
                            Log.d("ListSelected", selectedRestaurant.getRestaurantId());
                        }
                        sortUsersByID();

                    }
                });
    }

    public void  sortUsersByID() {
        List<UserScreen> userScreenList = new ArrayList<>();
        for (User listAllUser : listAllUsers) {
            boolean userAdded = false;
            for (SelectedRestaurant listAllSelectedRestaurant : listAllSelectedRestaurants) {
                if (listAllUser.getUid().equals(listAllSelectedRestaurant.getUserId())) {
                    for (Restaurant restaurant : restaurantList) {
                        if (restaurant.getId().equals(listAllSelectedRestaurant.getRestaurantId())) {

                            UserScreen userScreen = new UserScreen(listAllUser, restaurant);
                            userScreenList.add(userScreen);
                            userAdded = true;
                            break;
                        }
                    }
                }
                if (userAdded) break;
            }
            if (!userAdded) {
                UserScreen userScreen = new UserScreen(listAllUser, new Restaurant());
                userScreenList.add(userScreen);
            }
        }

        selectedRestaurantsLiveData.setValue(userScreenList);


    }

    public LiveData<List<UserScreen>> getSelectedRestaurantsLiveData() {
        return selectedRestaurantsLiveData;
    }
}


