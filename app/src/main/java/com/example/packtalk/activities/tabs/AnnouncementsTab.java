package com.example.packtalk.activities.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.packtalk.R;
import com.example.packtalk.activities.AnnouncementsAdapter;
import com.example.packtalk.activities.AnnouncementsItem;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementsTab extends Fragment{

    private RecyclerView recyclerViewAnnouncements;
    private AnnouncementsAdapter announcementsAdapter;
    private List<AnnouncementsItem> mData;


    public AnnouncementsTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_announcements_tab, container, false);

        recyclerViewAnnouncements = view.findViewById(R.id.recyclerViewAnnouncements);
        mData = new ArrayList<>();

        mData.add(new AnnouncementsItem("Shashank","Hi this is my first post", "9th March, 2020",R.drawable.ic_announcement));

//       announcementsAdapter= new AnnouncementsAdapter(this, mData);
        recyclerViewAnnouncements.setAdapter(announcementsAdapter);
//       recyclerViewAnnouncements.setLayoutManager(new LinearLayoutManager(this));

        return view;
    }
}
