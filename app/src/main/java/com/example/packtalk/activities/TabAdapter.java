package com.example.packtalk.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.packtalk.activities.tabs.AnnouncementsTab;
import com.example.packtalk.activities.tabs.ChatsTab;
import com.example.packtalk.activities.tabs.FolksTab;
import com.example.packtalk.activities.tabs.PostsTab;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int tabPosition) {// This method returns the tab that we are dealing with

        switch (tabPosition){
            case 0:
                PostsTab postsTab = new PostsTab();
                return postsTab;
                //or we can directly write
            // return new PostsTab();
            case 1:
                AnnouncementsTab announcementsTab = new AnnouncementsTab();
                return announcementsTab;
            case 2:
                ChatsTab chatsTab = new ChatsTab();
                return  chatsTab;
            case 3:
                FolksTab folksTab = new FolksTab();
                return folksTab;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4; // the amount of tabs is returned here
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Posts";
            case 1:
                return  "Announces";
            case 2:
                return "Chats";
            case 3:
                return "Folks";
            default:            //default case is required because the data type is CharSequence
                return null;
        }
    }
}
