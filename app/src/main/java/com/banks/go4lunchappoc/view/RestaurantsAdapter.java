package com.banks.go4lunchappoc.view;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.banks.go4lunchappoc.BuildConfig;
import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.databinding.FragmentListItemBinding;
import com.banks.go4lunchappoc.events.ClickListRestaurantEvent;
import com.banks.go4lunchappoc.injection.ListRestaurantViewModel;
import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.RestaurantScreen;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsViewHolder> {

    private final List<RestaurantScreen> restaurants;
    ListRestaurantViewModel mMainViewModel = new ListRestaurantViewModel();


    public RestaurantsAdapter(List<RestaurantScreen> restaurants) {
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
        RestaurantScreen restaurant = restaurants.get(position);

        // Set Name

        holder.binding.itemListRestaurantName.setText(restaurant.getRestaurant().getRestaurantName());

        // Set Number User Selected Restaurant
        holder.binding.itemListRestaurantPersonNumber.setText(String.valueOf("("+ restaurant.getNumberUser() + ")"));

        //Set Address
        holder.binding.itemListRestaurantAddress.setText(restaurant.getRestaurant().getRestaurantAddress());

        // Set Rating
        if (restaurant.getRestaurant().getRating() != null) {
            float resultForThreeStars = 3 * restaurant.getRestaurant().getRating() / 5;
            holder.binding.itemListRestaurantRatingBar.setRating(resultForThreeStars);
        } else {
            try {
                holder.binding.itemListRestaurantRatingBar.setRating(Float.parseFloat("Pas de note!"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Set Picture
        if (restaurant.getRestaurant().getUrlPictureRestaurant() != null) {
            Glide.with(holder.binding.itemListRestaurantPicture.getContext())
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photo_reference="
                            + restaurant.getRestaurant().getUrlPictureRestaurant() + "&key=" + BuildConfig.RR_KEY)
                    .into(holder.binding.itemListRestaurantPicture);
        } else {
            holder.binding.itemListRestaurantPicture.setImageResource(R.drawable.image_restaurant);
        }

        // Set Distance
        holder.binding.itemListRestaurantDistance.setText(String.valueOf(restaurant.getRestaurant().getDistanceKm() + " m"));

        // Set Opening
        if (restaurant.getRestaurant().getOpeningHours() != null) {
            holder.binding.itemListRestaurantOpening.setText(String.valueOf(openOrClose(restaurant)));
        } else {
            holder.binding.itemListRestaurantOpening.setText(String.valueOf(" "));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(restaurant.getRestaurant().getLatitude(), restaurant.getRestaurant().getLongitude());
                mMainViewModel.fetchOneRestaurantViewModel(latLng, restaurant.getRestaurant().getId(), restaurant.getRestaurant().getRating());
                EventBus.getDefault().post(new ClickListRestaurantEvent(restaurant));
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    static String statusRestaurant = "";

    public static String openOrClose(RestaurantScreen restaurant) {
        if (restaurant.getRestaurant().getOpeningHours()) {
            statusRestaurant = "Ouvert";
        } else {
            statusRestaurant = "Fermé";
        }
        return statusRestaurant;
    }


}
