package com.banks.go4lunchappoc.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.databinding.FragmentListRestaurantBinding;
import com.banks.go4lunchappoc.databinding.FragmentWorkmatesBinding;
import com.banks.go4lunchappoc.manager.UserManager;
import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.model.User;
import com.banks.go4lunchappoc.model.UserScreen;
import com.banks.go4lunchappoc.useCase.ShowRestaurantSelectedByUserUseCase;
import com.banks.go4lunchappoc.view.RestaurantsAdapter;
import com.banks.go4lunchappoc.view.UsersAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class WorkmatesFragment extends Fragment {

    private final UserManager userManager = UserManager.getInstance();

    //DESIGN
    private FragmentWorkmatesBinding binding;

    //DATA
    private List<UserScreen> users;
    private UsersAdapter adapter;
    ShowRestaurantSelectedByUserUseCase showRestaurantSelectedByUserUseCase = ShowRestaurantSelectedByUserUseCase.getInstance();
    List<UserScreen> userScreenList = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkmatesBinding.inflate(getLayoutInflater(), container, false);
        this.configureRecyclerView();
        showRestaurantSelectedByUserUseCase.observeRestaurants(this);
        updateUI(userScreenList = showRestaurantSelectedByUserUseCase.sortUsersByID());


        return binding.getRoot();
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
        users.clear();
        users.addAll(userScreenList);
        adapter.notifyDataSetChanged();
    }


}