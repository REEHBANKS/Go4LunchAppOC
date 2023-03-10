package com.banks.go4lunchappoc.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.banks.go4lunchappoc.databinding.FragmentListUserInRestaurantDetailBinding;
import com.banks.go4lunchappoc.manager.SelectedRestaurantManager;
import com.banks.go4lunchappoc.manager.UserManager;
import com.banks.go4lunchappoc.model.SelectedRestaurant;
import com.banks.go4lunchappoc.model.User;
import com.banks.go4lunchappoc.view.UsersInDetailAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ListUserInRestaurantDetailFragment extends Fragment {

    private final UserManager userManager = UserManager.getInstance();
    private final SelectedRestaurantManager selectedRestaurantManager = SelectedRestaurantManager.getInstance();
    FragmentListUserInRestaurantDetailBinding binding;

    //DATA
    private UsersInDetailAdapter adapter;
    List<User> listAllUsers = new ArrayList<>();
    private List<User> users;
    List<SelectedRestaurant> listAllSelectedRestaurants = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListUserInRestaurantDetailBinding.inflate(getLayoutInflater(), container, false);
        this.configureRecyclerView();
        getAllUser();


        return binding.getRoot();
    }


    // -----------------
    // GET THE USERS
    // -----------------
    private void getAllUser() {

        userManager.getAllUsers()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                listAllUsers.add(user);
                            }
                            getAllUserSelectedRestaurantWithIdData();
                        }
                    }
                });
    }

    // -----------------
    // GET THE ALL USER SELECTED A RESTAURANT
    // -----------------
    private void getAllUserSelectedRestaurantWithIdData() {
        selectedRestaurantManager.getAllUserSelectedRestaurantWithIdData()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                SelectedRestaurant selectedRestaurant = document.toObject(SelectedRestaurant.class);
                                listAllSelectedRestaurants.add(selectedRestaurant);
                                Log.d("SelectedRestaurant", "Information: " + selectedRestaurant.getRestaurantId() + "date" + selectedRestaurant.getDateSelected());
                            }
                        }


                        sortUsersByID();
                    }
                });
    }


    public void sortUsersByID() {
        List<User> matchingUsersId = new ArrayList<>();
        for (User listAllUser : listAllUsers) {
            for (SelectedRestaurant listAllSelectedRestaurant : listAllSelectedRestaurants) {
                if (listAllUser.getUid().equals(listAllSelectedRestaurant.getUserId())) {
                    matchingUsersId.add(listAllUser);
                }
            }
        }
        updateUI(matchingUsersId);
    }


    // -----------------
    // CONFIGURATION RECYCLERVIEW
    // -----------------
    private void configureRecyclerView() {
        this.users = new ArrayList<>();
        this.adapter = new UsersInDetailAdapter(this.users);
        binding.fragmentListUserInRestaurantRecyclerView.setAdapter(this.adapter);
        this.binding.fragmentListUserInRestaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // -------------------
    // UPDATE UI
    // -------------------
    public void updateUI(List<User> theUsers) {
        users.addAll(theUsers);
        adapter.notifyDataSetChanged();
    }

}