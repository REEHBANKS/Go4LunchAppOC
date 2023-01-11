package com.banks.go4lunchappoc.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.banks.go4lunchappoc.BuildConfig;
import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.fragment.ListRestaurantsFragment;
import com.banks.go4lunchappoc.fragment.MapFragment;
import com.banks.go4lunchappoc.fragment.WorkmatesFragment;
import com.banks.go4lunchappoc.injection.ListRestaurantViewModel;
import com.banks.go4lunchappoc.injection.MapViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    MapViewModel mapViewModel = new MapViewModel();
    ListRestaurantViewModel mMainViewModel = new ListRestaurantViewModel();
    BottomNavigationView bottomNavigationView;

    ListRestaurantsFragment mListRestaurantFragment = new ListRestaurantsFragment();
    MapFragment mMapFragment = new MapFragment();
    static WorkmatesFragment mWorkmatesFragment = new WorkmatesFragment();
    Toolbar toolbar;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int AUTOCOMPLETE_REQUEST_CODE_2 = 2;
    private static final int RESULT_OK = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), BuildConfig.RR_KEY);
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mMapFragment)
                .commit();



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.maps_view:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,mMapFragment )
                                .commit();
                        return true;
                    case R.id.list_view:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,mListRestaurantFragment )
                                .commit();
                        return true;
                    case R.id.workmates:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,mWorkmatesFragment )
                                .commit();
                        return true;
                }

                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bar_top_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_search:

                if (mMapFragment.isVisible()) {
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                            Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.RATING))
                            .setTypesFilter(Collections.singletonList("restaurant"))
                            .setCountry("FR")
                            .build(this);
                    startActivityForResult  ( intent,AUTOCOMPLETE_REQUEST_CODE);

                }else
                if (mListRestaurantFragment.isVisible()) {
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                            Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ICON_URL, Place.Field.OPENING_HOURS,
                                    Place.Field.RATING))
                            .setTypesFilter(Collections.singletonList("restaurant"))
                            .setCountry("FR")
                            .build(this);
                    startActivityForResult  ( intent,AUTOCOMPLETE_REQUEST_CODE_2);

                }else
                if(mWorkmatesFragment.isVisible()){
                    Toast.makeText(this, "NOT AVAILABLE", Toast.LENGTH_SHORT).show();
                    break;

                }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // Search for map
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Place place = Autocomplete.getPlaceFromIntent(data);
                // mMapFragment.onMapReadyMarkerSearch(place.getLatLng(), place.getName());
                mapViewModel.fetchOneMapViewModel(place.getLatLng(),place.getId(), Objects.requireNonNull(place.getRating()).floatValue());


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                assert data != null;
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
            // Search for list
        } else if (requestCode == AUTOCOMPLETE_REQUEST_CODE_2){
            if (resultCode == RESULT_OK) {
                assert data != null;
                Place place = Autocomplete.getPlaceFromIntent(data);

                mMainViewModel.fetchOneRestaurantForTheSearchViewModel(place.getLatLng(),place.getId(), Objects.requireNonNull(place.getRating()).floatValue());


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                assert data != null;
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}