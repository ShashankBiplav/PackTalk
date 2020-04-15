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
import com.example.packtalk.activities.models.AnnouncementsItem;

import java.util.List;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.MyViewHolder> {

    Context mContext;
    List<AnnouncementsItem> mAnnouncementsItems;

    public AnnouncementsAdapter(Context context, List<AnnouncementsItem> announcementsItems) {
        mContext = context;
        mAnnouncementsItems = announcementsItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_announcements,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textViewUsernameAnnouncementItem.setText(mAnnouncementsItems.get(position).getUsername());
        holder.textViewAnnounceAnnouncementItem.setText(mAnnouncementsItems.get(position).getAnnouncement());
        holder.textViewDateAnnouncementItem.setText(mAnnouncementsItems.get(position).getDate());
        holder.imageViewUserProfilePictureAnnouncementItem.setImageResource(mAnnouncementsItems.get(position).getUserProfilePicture());

    }

    @Override
    public int getItemCount() {
        return mAnnouncementsItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewUsernameAnnouncementItem,
                textViewAnnounceAnnouncementItem, textViewDateAnnouncementItem;
        private  ImageView imageViewUserProfilePictureAnnouncementItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUsernameAnnouncementItem = itemView.findViewById(R.id.textViewUsernameAnnouncementItem);
            textViewAnnounceAnnouncementItem = itemView.findViewById(R.id.textViewAnnounceAnnouncementItem);
            textViewDateAnnouncementItem = itemView.findViewById(R.id.textViewDateAnnouncementItem);
            imageViewUserProfilePictureAnnouncementItem =
                    (ImageView) itemView.findViewById(R.id.imageViewUserProfilePictureAnnouncementItem);

        }
    }

}












