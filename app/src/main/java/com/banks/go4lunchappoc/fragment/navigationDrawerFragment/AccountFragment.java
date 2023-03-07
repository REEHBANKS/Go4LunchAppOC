package com.banks.go4lunchappoc.fragment.navigationDrawerFragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banks.go4lunchappoc.BuildConfig;
import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.databinding.FragmentAccountBinding;
import com.banks.go4lunchappoc.databinding.FragmentListRestaurantBinding;
import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.useCase.GetSelectedRestaurantByCurrentUserUseCase;
import com.bumptech.glide.Glide;

public class AccountFragment extends DialogFragment {

    //DESIGN
    private FragmentAccountBinding binding;
    private GetSelectedRestaurantByCurrentUserUseCase getSelectedRestaurantByCurrentUserUseCase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater(), container, false);
        getSelectedRestaurantByCurrentUserUseCase = new GetSelectedRestaurantByCurrentUserUseCase();
        getSelectedRestaurantByCurrentUserUseCase.observeRestaurants(this);
        updateRestaurantView();
        // Utiliser l'objet Restaurant pour mettre à jour l'interface utilisateur

        return binding.getRoot();
    }


    public void updateRestaurantView(){
        getSelectedRestaurantByCurrentUserUseCase.getRestaurantLiveData().observe(getViewLifecycleOwner(), restaurant -> {
            if (restaurant.getUrlPictureRestaurant() != null) {
                binding.restaurantNameTextView.setText(restaurant.getRestaurantName());
                binding.restaurantAddressTextView.setText(restaurant.getRestaurantAddress());
                binding.restaurantNoNameTextView.setVisibility(View.GONE);
            // Set Picture
                Glide.with(binding.restaurantImageView.getContext())
                        .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photo_reference="
                                + restaurant.getUrlPictureRestaurant() + "&key=" + BuildConfig.RR_KEY)
                        .into(binding.restaurantImageView);
            } else {
                binding.restaurantNameTextView.setVisibility(View.GONE);
                binding.restaurantAddressTextView.setVisibility(View.GONE);
                binding.restaurantIcAddress.setVisibility(View.GONE);
                binding.restaurantImageView.setVisibility(View.GONE);

            }
        });
    }


}
