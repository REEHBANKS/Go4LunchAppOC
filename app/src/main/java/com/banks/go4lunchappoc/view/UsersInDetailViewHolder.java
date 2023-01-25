package com.banks.go4lunchappoc.view;

import androidx.recyclerview.widget.RecyclerView;

import com.banks.go4lunchappoc.databinding.FragmentListUserInRestaurantDetailItemBinding;
import com.banks.go4lunchappoc.databinding.FragmentWorkmatesItemBinding;

public class UsersInDetailViewHolder extends RecyclerView.ViewHolder {

    FragmentListUserInRestaurantDetailItemBinding binding;

    public UsersInDetailViewHolder(FragmentListUserInRestaurantDetailItemBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
    }

}
