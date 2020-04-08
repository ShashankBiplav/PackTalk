package com.example.packtalk.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packtalk.R;
import com.example.packtalk.activities.tabs.AnnouncementsTab;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.AnnouncementsViewHolder> {

    Context mContext;
    List<AnnouncementsItem> mList;


//    public AnnouncementsAdapter(AnnouncementsTab announcementsTab, List<AnnouncementsItem> data) {
//        this. =mList;
//    }


    @NonNull
    @Override
    public AnnouncementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_announcements, parent, false);

        return new AnnouncementsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementsViewHolder holder, int position) {

        //bind data here
        holder.textViewUsername.setText(mList.get(position).getUsername());
        holder.textViewAnnouncement.setText(mList.get(position).getAnnouncement());
        holder.textViewDate.setText(mList.get(position).getDate());
        holder.imageViewUserProfilePicture.setImageResource(mList.get(position).getUserProfilePicture());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class AnnouncementsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewUsername, textViewAnnouncement, textViewDate;
        ImageView imageViewUserProfilePicture;

        public  AnnouncementsViewHolder(View itemView){
            super(itemView);

           textViewUsername = itemView.findViewById(R.id.textViewUsername);
           textViewAnnouncement = itemView.findViewById(R.id.textViewAnnouncement);
           textViewDate = itemView.findViewById(R.id.textViewDate);
           imageViewUserProfilePicture = itemView.findViewById(R.id.imageViewUserProfilePicture);

        }

    }


}
