package com.banks.go4lunchappoc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.fragment.ListRestaurantsFragment;

public class MainActivity extends AppCompatActivity {

    ListRestaurantsFragment mListRestaurantFragment = new ListRestaurantsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,mListRestaurantFragment )
                .commit();
    }
}