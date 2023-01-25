package com.banks.go4lunchappoc.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banks.go4lunchappoc.BuildConfig;
import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.databinding.FragmentListItemBinding;
import com.banks.go4lunchappoc.databinding.FragmentWorkmatesItemBinding;
import com.banks.go4lunchappoc.model.User;
import com.bumptech.glide.Glide;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private final List<User> users;


    public UsersAdapter(List<User> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FragmentWorkmatesItemBinding fragmentWorkmatesItemBinding = FragmentWorkmatesItemBinding.inflate(layoutInflater, parent, false);
        return new UsersViewHolder(fragmentWorkmatesItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = users.get(position);

        holder.binding.itemAvailabilitySentence.setText(user.getUsername());

        if (user.getUrlPictureUser() != null) {
            Glide.with(holder.binding.itemUserPicture.getContext())
                    .load(user.getUrlPictureUser())
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
