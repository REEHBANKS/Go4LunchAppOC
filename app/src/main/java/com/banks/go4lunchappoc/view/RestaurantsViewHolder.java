package com.banks.go4lunchappoc.view;

import androidx.recyclerview.widget.RecyclerView;

import com.banks.go4lunchappoc.databinding.FragmentListItemBinding;

public class RestaurantsViewHolder extends RecyclerView.ViewHolder {

    FragmentListItemBinding binding;


    public RestaurantsViewHolder(FragmentListItemBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
    }

}
