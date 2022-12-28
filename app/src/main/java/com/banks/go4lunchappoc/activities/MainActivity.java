package com.banks.go4lunchappoc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.fragment.ListRestaurantsFragment;
import com.banks.go4lunchappoc.fragment.MapFragment;

public class MainActivity extends AppCompatActivity {

    ListRestaurantsFragment mListRestaurantFragment = new ListRestaurantsFragment();
    MapFragment mMapFragment = new MapFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,mMapFragment )
                .commit();
    }
}