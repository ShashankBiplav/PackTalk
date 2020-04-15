package com.example.packtalk.activities.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.packtalk.R;
import com.example.packtalk.activities.adapters.ChatsAdapter;
import com.example.packtalk.activities.models.ChatsItem;

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

    }
}
