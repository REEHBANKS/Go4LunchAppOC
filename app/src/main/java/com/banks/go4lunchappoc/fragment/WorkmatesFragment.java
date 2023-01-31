package com.banks.go4lunchappoc.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banks.go4lunchappoc.databinding.FragmentWorkmatesBinding;
import com.banks.go4lunchappoc.manager.UserManager;
import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.UserScreen;
import com.banks.go4lunchappoc.useCase.ShowRestaurantSelectedByUserUseCase;
import com.banks.go4lunchappoc.view.UsersAdapter;

import java.util.ArrayList;
import java.util.List;


public class WorkmatesFragment extends Fragment {

    //DESIGN
    private FragmentWorkmatesBinding binding;

    //DATA
    private List<UserScreen> users;
    private UsersAdapter adapter;
;
    private ShowRestaurantSelectedByUserUseCase showRestaurantSelectedByUserUseCase;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkmatesBinding.inflate(getLayoutInflater(), container, false);
        this.configureRecyclerView();
        showRestaurantSelectedByUserUseCase = new ShowRestaurantSelectedByUserUseCase();
        showRestaurantSelectedByUserUseCase.observeRestaurants(this);
        observeUserScreenLiveData();


        return binding.getRoot();
    }

    public void observeUserScreenLiveData() {
        showRestaurantSelectedByUserUseCase.getSelectedRestaurantsLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserScreen>>() {
            @Override
            public void onChanged(List<UserScreen> userScreenList) {
                updateUI(userScreenList);
            }
        });
    }



    // -----------------
    // CONFIGURATION RECYCLERVIEW
    // -----------------
    private void configureRecyclerView() {
        this.users = new ArrayList<>();
        this.adapter = new UsersAdapter(this.users);
        binding.fragmentListWorkmates.setAdapter(this.adapter);
        this.binding.fragmentListWorkmates.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // -------------------
    // UPDATE UI
    // -------------------
    public void updateUI(List<UserScreen> userScreenList) {
        users.addAll(userScreenList);
        adapter.notifyDataSetChanged();
    }




}