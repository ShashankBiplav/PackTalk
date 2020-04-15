package com.example.packtalk.activities.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packtalk.R;
import com.example.packtalk.activities.activities.Login;
import com.example.packtalk.activities.models.ChatsItem;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.MyViewHolder> {

    Context mContext;
    List<ChatsItem> mChatsItems;
    Dialog myDialog;

    public ChatsAdapter(Context context, List<ChatsItem> chatsItems) {
        this.mContext = context;
        this.mChatsItems = chatsItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_chats,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.linearLayoutChatsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyToast.makeText(mContext,
                        "Test Click" + String.valueOf(viewHolder.getAdapterPosition()),
                        Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                        true).show();
            }
        });

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
        private LinearLayout linearLayoutChatsItem;
        private TextView textViewUsernameChatsItem, textViewEmailChatsItem;
        private ImageView profilePictureChatsItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayoutChatsItem = itemView.findViewById(R.id.linearLayoutChatsItem);
            textViewUsernameChatsItem = itemView.findViewById(R.id.textViewUsernameChatsItem);
            textViewEmailChatsItem = itemView.findViewById(R.id.textViewEmailChatsItem);
            profilePictureChatsItem = itemView.findViewById(R.id.profilePictureChatsItem);

        }
    }

}
