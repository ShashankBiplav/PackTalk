package com.example.packtalk.activities.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.packtalk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsTab extends Fragment {

    public ChatsTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats_tab, container, false);
    }
}
