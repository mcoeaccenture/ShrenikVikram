package com.shrenikvikam.textalbums.ui.user.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shrenikvikam.textalbums.R;
import com.shrenikvikam.textalbums.model.UserDetails;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder> {

    private final List<UserDetails> userDetailsList;
    private final OnUserDetailsClickListener onUserDetailsClickListener;

    public UserListAdapter(List<UserDetails> userDetailsList,
                           OnUserDetailsClickListener onUserDetailsClickListener) {
        this.userDetailsList = userDetailsList;
        this.onUserDetailsClickListener = onUserDetailsClickListener;
    }

    @NonNull
    @Override
    public UserListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.user_list_item, viewGroup, false);
        return new UserListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListItemViewHolder viewHolder, int position) {
        UserDetails userDetails = userDetailsList.get(position);
        viewHolder.tvUserName.setText(userDetails.getName());
        viewHolder.tvUserEmail.setText(userDetails.getEmail());
        viewHolder.bind(userDetails, onUserDetailsClickListener);
    }

    @Override
    public int getItemCount() {
        return userDetailsList.size();
    }

    public final static class UserListItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName;
        TextView tvUserEmail;

        public UserListItemViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.text_username);
            tvUserEmail = itemView.findViewById(R.id.text_useremail);
        }

        public void bind(UserDetails userDetails, OnUserDetailsClickListener onUserDetailsClickListener) {
            itemView.setOnClickListener(v -> onUserDetailsClickListener.onUserDetailsClick(userDetails));
        }
    }

    public interface OnUserDetailsClickListener {
        void onUserDetailsClick(UserDetails userDetails);
    }
}
