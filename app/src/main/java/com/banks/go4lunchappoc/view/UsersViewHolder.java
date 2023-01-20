package com.banks.go4lunchappoc.view;

import androidx.recyclerview.widget.RecyclerView;

import com.banks.go4lunchappoc.databinding.FragmentListItemBinding;
import com.banks.go4lunchappoc.databinding.FragmentWorkmatesItemBinding;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    FragmentWorkmatesItemBinding binding;

    public UsersViewHolder( FragmentWorkmatesItemBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
    }

}
