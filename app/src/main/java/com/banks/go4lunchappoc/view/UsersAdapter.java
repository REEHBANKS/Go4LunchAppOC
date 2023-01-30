package com.banks.go4lunchappoc.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.databinding.FragmentWorkmatesItemBinding;
import com.banks.go4lunchappoc.model.User;
import com.banks.go4lunchappoc.model.UserScreen;
import com.bumptech.glide.Glide;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private final List<UserScreen> users;


    public UsersAdapter(List<UserScreen> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FragmentWorkmatesItemBinding fragmentWorkmatesItemBinding = FragmentWorkmatesItemBinding.inflate(layoutInflater, parent, false);
        return new UsersViewHolder(fragmentWorkmatesItemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        UserScreen userScreen = users.get(position);

        holder.binding.userNameFragmentWorkMates.setText(userScreen.getUsername());

        if (userScreen.getRestaurantName() != null) {
            holder.binding.restaurantNameFragmentWorkMates.setText("is eating " + userScreen.getRestaurantName());
        } else {
            holder.binding.restaurantNameFragmentWorkMates.setText("hasn't decided yet");
        }

        if (userScreen.getUrlPictureUser() != null) {
            Glide.with(holder.binding.itemUserPicture.getContext())
                    .load(userScreen.getUrlPictureUser())
                    .into(holder.binding.itemUserPicture);
        } else {
            holder.binding.itemUserPicture.setImageResource(R.drawable.mbappe_picture);
        }



    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}
