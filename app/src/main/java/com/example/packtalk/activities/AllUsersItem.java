package com.example.packtalk.activities;

import android.widget.ImageView;

public class AllUsersItem {
    private String username, email;
    private int userProfilePicture;
    private Boolean isFollowing;

    public AllUsersItem(){
        //required empty constructor
    }

    public AllUsersItem(String username, String email, int userProfilePicture, Boolean isFollowing) {
        this.username = username;
        this.email = email;
        this.userProfilePicture = userProfilePicture;
        this.isFollowing = isFollowing;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(int userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public Boolean getFollowing() {
        return isFollowing;
    }

    public void setFollowing(Boolean following) {
        isFollowing = following;
    }
}

