package com.banks.go4lunchappoc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.banks.go4lunchappoc.BuildConfig;
import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.fragment.ListRestaurantsFragment;
import com.banks.go4lunchappoc.fragment.MapFragment;
import com.banks.go4lunchappoc.fragment.WorkmatesFragment;
import com.banks.go4lunchappoc.injection.ListRestaurantViewModel;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ListRestaurantViewModel mMainViewModel = new ListRestaurantViewModel();
    BottomNavigationView bottomNavigationView;
    ListRestaurantsFragment mListRestaurantFragment = new ListRestaurantsFragment();
    MapFragment mMapFragment = new MapFragment();
    static WorkmatesFragment mWorkmatesFragment = new WorkmatesFragment();
    Toolbar toolbar;

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

}