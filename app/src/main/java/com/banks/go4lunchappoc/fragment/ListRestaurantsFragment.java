package com.banks.go4lunchappoc.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.banks.go4lunchappoc.activities.RestaurantDetailActivity;
import com.banks.go4lunchappoc.databinding.FragmentListRestaurantBinding;
import com.banks.go4lunchappoc.events.ClickListRestaurantEvent;
import com.banks.go4lunchappoc.events.SearchEvent;
import com.banks.go4lunchappoc.injection.ListRestaurantViewModel;
import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.RestaurantScreen;
import com.banks.go4lunchappoc.useCase.ShowListRestaurantWithAllDataUseCase;
import com.banks.go4lunchappoc.view.RestaurantsAdapter;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListRestaurantsFragment extends Fragment {

    ListRestaurantViewModel mMainViewModel = new ListRestaurantViewModel();
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private boolean isSearchEventReceived = false;


    //DESIGN
    private FragmentListRestaurantBinding binding;

    //DATA
    private List<RestaurantScreen> restaurants;
    private RestaurantsAdapter adapter;

    private ShowListRestaurantWithAllDataUseCase showListRestaurantWithAllDataUseCase;

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListRestaurantBinding.inflate(getLayoutInflater(), container, false);
        this.configureRecyclerView();
        showListRestaurantWithAllDataUseCase = new ShowListRestaurantWithAllDataUseCase();
        showListRestaurantWithAllDataUseCase.observeRestaurants(this);
        showListRestaurantWithAllDataUseCase.observeOneRestaurant(this);
        checkAccessRestaurant();
        observeOneRestaurantScreenLiveData();
        observeRestaurantScreenLiveData();
        return binding.getRoot();
    }

    public void observeRestaurantScreenLiveData(){
        showListRestaurantWithAllDataUseCase.getResultRestaurantScreen().observe(getViewLifecycleOwner(), new Observer<List<RestaurantScreen>>() {
            @Override
            public void onChanged(List<RestaurantScreen> restaurantScreens) {
                updateUI(restaurantScreens);
            }
        });

    }

    public void observeOneRestaurantScreenLiveData(){
        showListRestaurantWithAllDataUseCase.getOneResultRestaurantScreen().observe(getViewLifecycleOwner(), new Observer<RestaurantScreen>() {
            @Override
            public void onChanged(RestaurantScreen restaurantScreen) {
                if (isSearchEventReceived) {
                    updateRestaurantSearch(restaurantScreen);
                    isSearchEventReceived = false;
                }
            }
        });
    }

    // Get Current List Restaurant
/*
    public void observeRestaurantLiveData() {
        mMainViewModel.getRestaurantLiveData().observe(getViewLifecycleOwner(), new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {

                updateUI(restaurants);
            }
        });
    }

    // Get Current One Restaurant for the search function

    public void observeOneRestaurantForTheSearchLiveData() {
        mMainViewModel.getOneRestaurantForTheSearchLiveData().observe(getViewLifecycleOwner(), new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {
                if (isSearchEventReceived) {
                    updateRestaurantSearch(restaurant);
                    isSearchEventReceived = false;
                }
            }
        });
    }

*/
    // -----------------
    // CONFIGURATION RECYCLERVIEW
    // -----------------
    private void configureRecyclerView() {
        this.restaurants = new ArrayList<>();
        this.adapter = new RestaurantsAdapter(this.restaurants);
        binding.fragmentMainRecyclerView.setAdapter(this.adapter);
        this.binding.fragmentMainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // -------------------
    // UPDATE UI
    // -------------------
    @SuppressLint("NotifyDataSetChanged")
    public void updateUI(List<RestaurantScreen> theRestaurants) {
        restaurants.addAll(theRestaurants);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateRestaurantSearch(RestaurantScreen theRestaurant) {
        restaurants.clear();
        restaurants.add(theRestaurant);
        adapter.notifyDataSetChanged();
    }

    // -------------------
    // EventBus
    // -------------------
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onSearchEvent(SearchEvent event) {
        isSearchEventReceived = true;
    }

    @Subscribe
    public void onClickListRestaurant(ClickListRestaurantEvent event) {
        launchRestaurantDetailActivity(event.restaurant);
    }

    // -------------------
    // Method Launching restaurant detail activity with a element restaurant
    // -------------------

    private void launchRestaurantDetailActivity(RestaurantScreen restaurant) {
        Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
        intent.putExtra(RestaurantDetailActivity.RESTAURANT_KEY, restaurant);
        startActivity(intent);
    }


    // -------------------
    // Checking Permission use current location of user
    // -------------------
    private void checkAccessRestaurant() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        } else {
            getCurrentLocation();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();


            } else {
                Toast.makeText(requireContext(), "Permission died ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(requireContext())
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(requireContext())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();


                            mMainViewModel.fetchAllRestaurantsViewModel(latitude, longitude);
                        }
                    }
                }, Looper.getMainLooper());

    }

}