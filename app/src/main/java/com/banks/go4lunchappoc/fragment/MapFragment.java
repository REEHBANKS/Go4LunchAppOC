package com.banks.go4lunchappoc.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.activities.RestaurantDetailActivity;
import com.banks.go4lunchappoc.injection.MapViewModel;
import com.banks.go4lunchappoc.manager.SelectedRestaurantManager;
import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.SelectedRestaurant;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MapFragment extends Fragment {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;
    MapViewModel mapViewModel = new MapViewModel();
    private final List<Restaurant> mapRestaurants = new ArrayList<>();
    List<SelectedRestaurant> listAllSelectedRestaurants = new ArrayList<>();
    private final SelectedRestaurantManager selectedRestaurantManager = SelectedRestaurantManager.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        observeMapLiveData();
        observeOneMapLiveData();
        getAllSelectedRestaurantsData();

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

        }


    }

    // Subscriber Live Data for set update the restaurants
    private void observeMapLiveData() {
        mapViewModel.getMapLiveData().observe(getViewLifecycleOwner(), this::updateUI);
    }

    // Get Current One Restaurant

    public void observeOneMapLiveData() {
        mapViewModel.getOneMapLiveData().observe(getViewLifecycleOwner(), new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {

                onMapReadyMarkerSearch(restaurant);

            }
        });
    }

    //Method for the update
    public void updateUI(List<Restaurant> theRestaurantsForMap) {
        mapRestaurants.clear();
        mapRestaurants.addAll(theRestaurantsForMap);

        addRestaurantsMarkers();
    }

    // -----------------
    // GET THE ALL USER SELECTED A RESTAURANT
    // -----------------
    private void getAllSelectedRestaurantsData() {
        selectedRestaurantManager.getAllSelectedRestaurantsData()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                SelectedRestaurant selectedRestaurant = document.toObject(SelectedRestaurant.class);
                                listAllSelectedRestaurants.add(selectedRestaurant);
                            }

                        }
                    }
                });
    }


    //Method to add restaurants with the markers
    public void addRestaurantsMarkers() {
        for (Restaurant restaurant : mapRestaurants) {
            LatLng testLocation = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
            boolean isSelected = false;
            for (SelectedRestaurant listAllSelectedRestaurant : listAllSelectedRestaurants) {
                if (restaurant.getId().equals(listAllSelectedRestaurant.getRestaurantId())) {
                    isSelected = true;
                    break;
                }
            }

            if (!isSelected) {
                Objects.requireNonNull(mMap.addMarker(new MarkerOptions()
                                .position(testLocation)
                                .icon(bitmapDescriptorFactory(getContext(), R.drawable.icon_red_lunch))
                                .title(restaurant.getRestaurantName())))
                        .setTag(restaurant);
            } else {
                Objects.requireNonNull(mMap.addMarker(new MarkerOptions()
                                .position(testLocation)
                                .icon(bitmapDescriptorFactory(getContext(), R.drawable.icon_green_lunch))
                                .title(restaurant.getRestaurantName())))
                        .setTag(restaurant);
            }
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {


                Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
                intent.putExtra(RestaurantDetailActivity.RESTAURANT_KEY, (Restaurant) marker.getTag());
                startActivity(intent);


                return true;
            }
        });
    }


    // Method to change icon marker on the google map
    private BitmapDescriptor bitmapDescriptorFactory(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth()
                , vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    // to Start the map
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mMap = googleMap;
            checkAccessRestaurant();
        }
    };


    // Checking Permission location
    private void checkAccessRestaurant() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        } else {
            getCurrentLocation();
            mMap.setMyLocationEnabled(true);
        }
    }

    // Ask of Permission
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


    // Methode Get Current Location
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
                            double mapLatitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double mapLongitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();

                            mapViewModel.fetchMapViewModel(mapLatitude, mapLongitude);


                            //  Marker current position
                            LatLng myLocation = new LatLng(mapLatitude, mapLongitude);
                            mMap.addMarker(new MarkerOptions()
                                    .icon(bitmapDescriptorFactory(getContext(), R.drawable.icon_you_are_here))
                                    .position(myLocation).title("You're here !"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));

                        }
                    }
                }, Looper.getMainLooper());
    }

    // Method to add marker for search option restaurant
    public void onMapReadyMarkerSearch(Restaurant restaurant) {

        LatLng latLng = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
        Objects.requireNonNull(mMap.addMarker(new MarkerOptions()
                        .position((latLng))
                        .title(restaurant.getRestaurantName())))
                .setTag(restaurant);
        mMap.moveCamera(CameraUpdateFactory.newLatLng((latLng)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
                intent.putExtra(RestaurantDetailActivity.RESTAURANT_KEY, (Restaurant) marker.getTag());
                startActivity(intent);
                return true;
            }
        });
    }
}