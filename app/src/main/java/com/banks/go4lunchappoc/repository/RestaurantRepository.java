package com.banks.go4lunchappoc.repository;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.banks.go4lunchappoc.data.RestaurantService;
import com.banks.go4lunchappoc.data.RetrofitClient;
import com.banks.go4lunchappoc.model.jsonResponse.AllRestaurantsResponse;
import com.banks.go4lunchappoc.model.jsonResponse.RestaurantResponse;
import com.banks.go4lunchappoc.model.restaurant.Restaurant;
import com.banks.go4lunchappoc.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RestaurantRepository {

    MutableLiveData<List<Restaurant>> result = new MutableLiveData<>();

    public LiveData<List<Restaurant>> getRestaurantLiveData() {
        return result;
    }




    public void fetchRestaurant(Double latitude, Double longitude) {
        streamFetchRestaurantResponse(latitude,longitude)
                .subscribeWith(new DisposableObserver<List<Restaurant>>() {

                    @Override
                    public void onNext(@NonNull List<Restaurant> restaurants) {
                        result.setValue(restaurants);


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("Renel", "On error");

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Renel", "On complete");

                    }
                });
    }



    public Observable<List<Restaurant>> streamFetchRestaurantResponse(Double latitude, Double longitude) {

        String lat = Double.toString(latitude);
        String lng = Double.toString(longitude);
        String location = lat + "," + lng;




        RestaurantService restaurantService = RetrofitClient.getRetrofit().create(RestaurantService.class);
        return restaurantService.getAllRestaurantsResponse(BuildConfig.RR_KEY, location)

                .map((Function<AllRestaurantsResponse, List<Restaurant>>) resultsResponse -> {
                    ArrayList<Restaurant> restaurants = new ArrayList<>();


                    for (RestaurantResponse restaurantResponse : resultsResponse.getResults()) {
                        Boolean isOpen = restaurantResponse.getOpeningResponse() != null ? restaurantResponse.getOpeningResponse().getOpen_now() : false;
                        String photoIsHere = restaurantResponse.getPhotosResponse() == null || restaurantResponse.getPhotosResponse().isEmpty() ? null :
                                restaurantResponse.getPhotosResponse().get(0).getPhotoReference();

                        float[] results = new float[1];
                        Location.distanceBetween(latitude, longitude, restaurantResponse.getGeometryResponse().getLocationResponse().getLat()
                                ,restaurantResponse.getGeometryResponse().getLocationResponse().getLng(), results);
                        float distanceResults = results[0];
                        int distance = (int) distanceResults;


                        Restaurant restaurant = new Restaurant(restaurantResponse.getPlace_id(),
                                restaurantResponse.getName(),
                                restaurantResponse.getGeometryResponse().getLocationResponse().getLat(),
                                restaurantResponse.getGeometryResponse().getLocationResponse().getLng(),
                                photoIsHere,
                                restaurantResponse.getVicinity(),
                                isOpen,
                                restaurantResponse.getRating(),
                                distance);

                        restaurants.add(restaurant);
                    }
                    return restaurants;
                })


                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}