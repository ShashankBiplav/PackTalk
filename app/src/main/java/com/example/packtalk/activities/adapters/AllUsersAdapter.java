package com.example.packtalk.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packtalk.R;
import com.example.packtalk.activities.models.AllUsersItem;

import java.util.List;

public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.UsersViewHolder> {

    Context mContext;
    List<AllUsersItem> mData;

    public AllUsersAdapter(Context context, List<AllUsersItem> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_user, viewGroup, false);

        return new UsersViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        // binding data here
        holder.profilePictureAllUsersItem.setImageResource(mData.get(position).getUserProfilePicture());
        holder.textViewUsernameAllUsersItem.setText(mData.get(position).getUsername());
        holder.textViewEmailAllUsersItem.setText(mData.get(position).getEmail());
        if (mData.get(position).getFollowing() == true){
            holder.imageViewFollowingAllUsersItem.setImageResource(R.drawable.ic_following);
        }
        else if (mData.get(position).getFollowing() == false){
            holder.imageViewFollowingAllUsersItem.setImageResource(R.drawable.ic_not_following);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class UsersViewHolder extends RecyclerView.ViewHolder{

        ImageView profilePictureAllUsersItem, imageViewFollowingAllUsersItem;
        TextView textViewUsernameAllUsersItem, textViewEmailAllUsersItem;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePictureAllUsersItem =itemView.findViewById(R.id.profilePictureAllUsersItem);
            imageViewFollowingAllUsersItem = itemView.findViewById(R.id.imageViewFollowingAllUsersItem);
            textViewUsernameAllUsersItem = itemView.findViewById(R.id.textViewUsernameAllUsersItem);
            textViewEmailAllUsersItem = itemView.findViewById(R.id.textViewEmailAllUsersItem);

        }
    }

}
