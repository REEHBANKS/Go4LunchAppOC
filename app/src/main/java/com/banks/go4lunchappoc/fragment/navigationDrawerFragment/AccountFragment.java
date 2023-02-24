package com.banks.go4lunchappoc.fragment.navigationDrawerFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.databinding.FragmentAccountBinding;
import com.banks.go4lunchappoc.databinding.FragmentListRestaurantBinding;
import com.banks.go4lunchappoc.useCase.GetSelectedRestaurantByCurrentUserUseCase;

public class AccountFragment extends Fragment {

    //DESIGN
    private FragmentAccountBinding binding;
    private GetSelectedRestaurantByCurrentUserUseCase getSelectedRestaurantByCurrentUserUseCase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater(), container, false);
        getSelectedRestaurantByCurrentUserUseCase = new GetSelectedRestaurantByCurrentUserUseCase();
        getSelectedRestaurantByCurrentUserUseCase.observeRestaurants(this);

        return binding.getRoot();
    }



}