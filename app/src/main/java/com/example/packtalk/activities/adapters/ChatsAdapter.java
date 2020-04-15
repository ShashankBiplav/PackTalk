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
import com.example.packtalk.activities.models.ChatsItem;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.MyViewHolder> {

    Context mContext;
    List<ChatsItem> mChatsItems;

    public ChatsAdapter(Context context, List<ChatsItem> chatsItems) {
        this.mContext = context;
        this.mChatsItems = chatsItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_chats,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //binding data
        holder.textViewUsernameChatsItem.setText(mChatsItems.get(position).getName());
        holder.textViewEmailChatsItem.setText(mChatsItems.get(position).getEmail());
        holder.profilePictureChatsItem.setImageResource(mChatsItems.get(position).getProfilePicture());

    }

    @Override
    public int getItemCount() {
        return mChatsItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewUsernameChatsItem, textViewEmailChatsItem;
        private ImageView profilePictureChatsItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUsernameChatsItem = itemView.findViewById(R.id.textViewUsernameChatsItem);
            textViewEmailChatsItem = itemView.findViewById(R.id.textViewEmailChatsItem);
            profilePictureChatsItem = itemView.findViewById(R.id.profilePictureChatsItem);

        }
    }

}
