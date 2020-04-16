package com.example.packtalk.activities.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.packtalk.R;
import com.example.packtalk.activities.adapters.ChatsAdapter;
import com.example.packtalk.activities.models.ChatsItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsTab extends Fragment {

    View mView;
    private RecyclerView recyclerViewChats;
    private List<ChatsItem> mChatsItems = new ArrayList<>();

    public ChatsTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_chats_tab, container, false);
        recyclerViewChats =mView.findViewById(R.id.recyclerViewChats);
        ChatsAdapter recyclerViewChatsAdapter = new ChatsAdapter(getContext(),mChatsItems);
        recyclerViewChats.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewChats.setAdapter(recyclerViewChatsAdapter);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChatsItems.add(new ChatsItem("Shashank","shashankbiplav@gmail.com",R.drawable.ic_developer));
        try {

//            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Loading Chats...", "Please wait...", true);


            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("User");
            parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if ((objects.size() > 0) && e == null) {
                        for (ParseObject user : objects) {
                                mChatsItems.add(new ChatsItem(user.getString("username"),
                                        user.getString("email"),R.drawable.ic_developer));
                        }
                        ChatsAdapter recyclerViewChatsAdapter = new ChatsAdapter(getContext(),mChatsItems);
                        recyclerViewChats.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerViewChats.setAdapter(recyclerViewChatsAdapter);
                    }
                }
            });


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
