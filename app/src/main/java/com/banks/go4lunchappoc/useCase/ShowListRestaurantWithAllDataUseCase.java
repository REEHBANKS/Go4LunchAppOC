package com.banks.go4lunchappoc.useCase;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.RestaurantScreen;
import com.banks.go4lunchappoc.model.SelectedRestaurant;
import com.banks.go4lunchappoc.model.UserScreen;
import com.banks.go4lunchappoc.repository.RestaurantRepository;
import com.banks.go4lunchappoc.repository.SelectedRestaurantRepository;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowListRestaurantWithAllDataUseCase {

    private static volatile ShowListRestaurantWithAllDataUseCase instance;
    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    SelectedRestaurantRepository selectedRestaurantRepository = SelectedRestaurantRepository.getInstance();
    List<Restaurant> restaurantList = new ArrayList<>();
    List<SelectedRestaurant> listAllSelectedRestaurants = new ArrayList<>();
    private final MutableLiveData<List<RestaurantScreen>> resultRestaurantScreen = new MutableLiveData<>();
    private final MutableLiveData<RestaurantScreen> oneResultRestaurantScreen = new MutableLiveData<>();


    public static ShowListRestaurantWithAllDataUseCase getInstance() {
        ShowListRestaurantWithAllDataUseCase result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ShowListRestaurantWithAllDataUseCase.class) {
            if (instance == null) {
                instance = new ShowListRestaurantWithAllDataUseCase();
            }
            return instance;
        }
    }

    public void observeRestaurants(LifecycleOwner lifecycleOwner) {
        restaurantRepository.getRestaurantLiveData().observe(lifecycleOwner, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {

                restaurantList = restaurants;

                getAllSelectedRestaurantsData();
            }
        });
    }

    public void observeOneRestaurant (LifecycleOwner lifecycleOwner){
        restaurantRepository.getOneRestaurantLiveData().observe(lifecycleOwner, new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {
                RestaurantScreen restaurantScreenToSearch = new RestaurantScreen(restaurant, 0);
                oneResultRestaurantScreen.setValue(restaurantScreenToSearch);
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
                        getListRestaurantWithAllItem();
                    }
                });
    }


    public void getListRestaurantWithAllItem(){
        List<RestaurantScreen> restaurantScreenList = new ArrayList<>();
        for (Restaurant restaurant : restaurantList){
            int numberUser = 0;
            boolean restaurantFound = false;
            for (SelectedRestaurant listAllSelectedRestaurant : listAllSelectedRestaurants){
                if(restaurant.getId().equals(listAllSelectedRestaurant.getRestaurantId())){
                    numberUser++;
                    restaurantFound = true;
                    break;
                }
            }
            RestaurantScreen restaurantScreen;
            if(restaurantFound){
                restaurantScreen = new RestaurantScreen(restaurant, numberUser);
            } else {
                restaurantScreen = new RestaurantScreen(restaurant, 0);
            }
            restaurantScreenList.add(restaurantScreen);
            Log.d("ListSelected", restaurantScreen.getRestaurant().getRestaurantName() + restaurantScreen.getNumberUser());
        }

        resultRestaurantScreen.setValue(restaurantScreenList);
    }

    public LiveData<List<RestaurantScreen>> getResultRestaurantScreen() {
        return resultRestaurantScreen;
    }

    public LiveData<RestaurantScreen> getOneResultRestaurantScreen(){
        return oneResultRestaurantScreen;
    }

}
