package com.banks.go4lunchappoc.activities;

import static com.facebook.FacebookSdk.getApplicationContext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.banks.go4lunchappoc.BuildConfig;
import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.databinding.ActivityRestaurantDetailBinding;
import com.banks.go4lunchappoc.fragment.ListUserInRestaurantDetailFragment;
import com.banks.go4lunchappoc.fragment.WorkmatesFragment;
import com.banks.go4lunchappoc.manager.FavoriteRestaurantManager;
import com.banks.go4lunchappoc.manager.SelectedRestaurantManager;
import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.RestaurantScreen;
import com.banks.go4lunchappoc.util.AlarmManagerHelper;
import com.bumptech.glide.Glide;

public class RestaurantDetailActivity extends AppCompatActivity {

    public static String RESTAURANT_KEY = "RESTAURANT_KEY";
    private RestaurantScreen restaurant;
    private ActivityRestaurantDetailBinding binding;
    private final SelectedRestaurantManager selectedRestaurantManager = SelectedRestaurantManager.getInstance();
    private final FavoriteRestaurantManager favoriteRestaurantManager = FavoriteRestaurantManager.getInstance();
    ListUserInRestaurantDetailFragment listUserInRestaurantDetailFragment = new ListUserInRestaurantDetailFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        restaurant = (RestaurantScreen) getIntent().getSerializableExtra(RESTAURANT_KEY);
        updateUi();
        fetchRestaurantId(restaurant.getRestaurant().getId());
        getFetchRestaurantId(restaurant.getRestaurant().getId());
        getSupportFragmentManager().beginTransaction().add(R.id.containerDetail, listUserInRestaurantDetailFragment)
                .commit();

    }


    public void updateUi() {

        //-----------
        // Set View
        //----------
        if (restaurant.getRestaurant().getUrlPictureRestaurant() != null) {
            Glide.with(binding.pictureRestaurantDetail.getContext())
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photo_reference="
                            + restaurant.getRestaurant().getUrlPictureRestaurant() + "&key=" + BuildConfig.RR_KEY)
                    .into(binding.pictureRestaurantDetail);
        } else {
            binding.pictureRestaurantDetail.setImageResource(R.drawable.picture_restaurant_with_workers);
        }

        binding.nameRestaurantDetail.setText(restaurant.getRestaurant().getRestaurantName());
        binding.addressRestaurantDetail.setText(restaurant.getRestaurant().getRestaurantAddress());
        if (restaurant.getRestaurant().getRating() != null) {
            float resultForThreeStars = 3 * restaurant.getRestaurant().getRating() / 5;
            binding.itemListRestaurantRatingBar.setRating(resultForThreeStars);
        } else {
            try {
                binding.itemListRestaurantRatingBar.setRating(Float.parseFloat("Pas de note!"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        //-----------
        // Set Button
        //----------

        if (restaurant.getRestaurant().getNumberPhone() != null) {
            binding.buttonCallRestaurantDetail.setOnClickListener(v -> Toast.makeText(getApplicationContext()
                    , restaurant.getRestaurant().getNumberPhone(), Toast.LENGTH_SHORT).show());
        } else {
            binding.buttonCallRestaurantDetail.setOnClickListener(v -> Toast.makeText(getApplicationContext()
                    , "Unavailable number!", Toast.LENGTH_SHORT).show());
        }

        if (restaurant.getRestaurant().getEmail() != null) {
            binding.buttonWebsiteRestaurantDetail.setOnClickListener(v -> Toast.makeText(getApplicationContext()
                    , restaurant.getRestaurant().getEmail(), Toast.LENGTH_SHORT).show());
        } else {
            binding.buttonCallRestaurantDetail.setOnClickListener(v -> Toast.makeText(getApplicationContext()
                    , "Unavailable website!", Toast.LENGTH_SHORT).show());
        }


        // button selected restaurant
        binding.buttonSelectedRestaurant.setOnClickListener(v -> {
            selectedRestaurantManager.createSelectedRestaurant();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                AlarmManagerHelper.onRestaurantSelected(this,restaurant.getRestaurant().getId());
            }
        });

        // button favorite restaurant
        binding.buttonLikeRestaurantDetail.setOnClickListener(v -> favoriteRestaurantManager.createFavoriteRestaurant());
    }



    public void fetchRestaurantId(String id) {
        selectedRestaurantManager.fetchRestaurantIdManager(id);

    }

    public void getFetchRestaurantId(String id) {
        favoriteRestaurantManager.getFetchRestaurantId(id);
    }
}
