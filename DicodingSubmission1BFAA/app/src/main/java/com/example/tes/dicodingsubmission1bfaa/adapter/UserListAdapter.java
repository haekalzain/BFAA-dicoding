package com.example.tes.dicodingsubmission1bfaa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tes.dicodingsubmission1bfaa.R;
import com.example.tes.dicodingsubmission1bfaa.model.User;
import com.example.tes.dicodingsubmission1bfaa.view.UserListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private ArrayList<User> userArrayList;
    public UserListView userListView;

    public UserListAdapter(ArrayList<User> list) {
        this.userArrayList = list;
    }

    public void setMainView(UserListView view) {
        this.userListView = view;
    }

    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {
        final User user = userArrayList.get(position);
        holder.tvName.setText(user.getName());
        holder.tvUsername.setText(user.getUsername());
        holder.tvLocation.setText(user.getLocation());
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.civUser);
        holder.cardView.setOnClickListener(view -> userListView.goToDetail(user));
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_username)
        TextView tvUsername;

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_location)
        TextView tvLocation;

        @BindView(R.id.civ_user)
        CircleImageView civUser;
        @BindView(R.id.cv_user)
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
