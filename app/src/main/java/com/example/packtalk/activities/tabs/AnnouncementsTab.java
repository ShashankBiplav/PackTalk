package com.example.packtalk.activities.tabs;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.packtalk.R;
import com.example.packtalk.activities.AnnouncementsAdapter;
import com.example.packtalk.activities.AnnouncementsItem;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementsTab extends Fragment{

    View mView;
    private RecyclerView recyclerViewAnnouncements;
    private ArrayList<AnnouncementsItem> mData = new ArrayList<>();

    public AnnouncementsTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_announcements_tab, container, false);

        recyclerViewAnnouncements = view.findViewById(R.id.recyclerViewAnnouncements);
        AnnouncementsAdapter announcementsAdapter = new AnnouncementsAdapter(getContext(), mData);
        recyclerViewAnnouncements.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAnnouncements.setAdapter(announcementsAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            try {



                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("myAnnouncement");
                parseQuery.orderByDescending("createdAt"); //displaying latest posts at top
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                         if (objects.size() >0){

                             final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);

                             //iterating through all
                             for (ParseObject announcementObject : objects){

                                 Date dateOfAnnouncement = announcementObject.getCreatedAt();
                                 DateFormat df = new SimpleDateFormat("h:mm a | EEE | MMM d |''yy");
                                 String announcementDate = df.format(dateOfAnnouncement);

                                 mData.add(new AnnouncementsItem
                                         (announcementObject.getString("user").toString(),
                                                 announcementObject.getString("announcement").toString(),
                                                announcementDate,
                                                 R.drawable.ic_developer));
                             }

                             AnnouncementsAdapter announcementsAdapter = new AnnouncementsAdapter(getContext(), mData);
                             recyclerViewAnnouncements.setLayoutManager(new LinearLayoutManager(getActivity()));
                             recyclerViewAnnouncements.setAdapter(announcementsAdapter);
                             progressDialog.dismiss();
                         }
                    }
                });

            }
            catch (Exception e){
                e.printStackTrace();
            }
    }
}
