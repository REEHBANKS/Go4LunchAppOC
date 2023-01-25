package com.banks.go4lunchappoc.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.databinding.FragmentListUserInRestaurantDetailItemBinding;
import com.banks.go4lunchappoc.databinding.FragmentWorkmatesItemBinding;
import com.banks.go4lunchappoc.model.User;
import com.bumptech.glide.Glide;

import java.util.List;

public class UsersInDetailAdapter extends RecyclerView.Adapter<UsersInDetailViewHolder> {

    private final List<User> users;

    public UsersInDetailAdapter(List<User> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public UsersInDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FragmentListUserInRestaurantDetailItemBinding fragmentListUserInRestaurantDetailItemBinding
                = FragmentListUserInRestaurantDetailItemBinding.inflate(layoutInflater, parent, false);

        return new UsersInDetailViewHolder(fragmentListUserInRestaurantDetailItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersInDetailViewHolder holder, int position) {

        User user = users.get(position);

        holder.binding.textUserRestaurantDetail.setText(user.getUsername());

        if (user.getUrlPictureUser() != null) {
            Glide.with(holder.binding.pictureUserRestaurantDetail.getContext())
                    .load(user.getUrlPictureUser())
                    .into(holder.binding.pictureUserRestaurantDetail);
        } else {
            holder.binding.pictureUserRestaurantDetail.setImageResource(R.drawable.mbappe_picture);
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
