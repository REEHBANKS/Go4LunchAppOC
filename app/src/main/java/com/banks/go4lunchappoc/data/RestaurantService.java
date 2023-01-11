package com.banks.go4lunchappoc.data;

import com.banks.go4lunchappoc.model.jsonResponse.AllRestaurantsResponse;
import com.banks.go4lunchappoc.model.jsonResponse.OneRestaurantResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantService {

    // The "location" variable is filled in when the view is initialized, it indicates the current location
    // the 'api' variable is the value of the API KEY, this one is in the file 'local.properties' of my project

    @GET("maps/api/place/nearbysearch/json?radius=1500&type=restaurant")
    Observable<AllRestaurantsResponse> getAllRestaurantsResponse(@Query("key") String api,
                                                          @Query("location") String location);

    @GET("maps/api/place/details/json?")
    Observable<OneRestaurantResponse> getOneRestaurantByIdResponse(@Query("key") String api,
                                                                   @Query("place_id") String id);

}
