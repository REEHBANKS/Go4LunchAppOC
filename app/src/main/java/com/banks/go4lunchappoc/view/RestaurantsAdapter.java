package com.banks.go4lunchappoc.view;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.banks.go4lunchappoc.BuildConfig;
import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.databinding.FragmentListItemBinding;
import com.banks.go4lunchappoc.events.ClickListRestaurantEvent;
import com.banks.go4lunchappoc.model.restaurant.Restaurant;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsViewHolder> {

    private final List<Restaurant> restaurants;


    public RestaurantsAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }


    @NonNull
    @Override
    public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FragmentListItemBinding fragmentListItemBinding = FragmentListItemBinding.inflate(layoutInflater, parent, false);
        return new RestaurantsViewHolder(fragmentListItemBinding);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RestaurantsViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);

        // Set Name

        holder.binding.itemListRestaurantName.setText(restaurant.getRestaurantName());

        //Set Address
        holder.binding.itemListRestaurantAddress.setText(restaurant.getRestaurantAddress());

        // Set Rating
        if (restaurant.getRating() != null) {
            float resultForThreeStars = 3 * restaurant.getRating()/5;
            holder.binding.itemListRestaurantRatingBar.setRating(resultForThreeStars);
        } else {
            try {
                holder.binding.itemListRestaurantRatingBar.setRating(Float.parseFloat("Pas de note!"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Set Picture
        if (restaurant.getUrlPictureRestaurant() != null) {
            Glide.with(holder.binding.itemListRestaurantPicture.getContext())
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photo_reference="
                            + restaurant.getUrlPictureRestaurant() +"&key="+ BuildConfig.RR_KEY)
                    .into(holder.binding.itemListRestaurantPicture);
        } else {
            holder.binding.itemListRestaurantPicture.setImageResource(R.drawable.image_restaurant);
        }

        // Set Distance
        holder.binding.itemListRestaurantDistance.setText(String.valueOf(restaurant.getDistanceKm() + " m"));

        // Set Opening
        if (restaurant.getOpeningHours() != null) {
            holder.binding.itemListRestaurantOpening.setText(String.valueOf(openOrClose(restaurant)));
        } else {
            holder.binding.itemListRestaurantOpening.setText(String.valueOf(" "));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ClickListRestaurantEvent(restaurant));
                Log.d("mat", "On complete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    static String statusRestaurant = "";

    public static String openOrClose(Restaurant restaurant) {
        if (restaurant.getOpeningHours()) {
            statusRestaurant = "Ouvert";
        } else {
            statusRestaurant = "Fermé";
        }
        return statusRestaurant;
    }



}
