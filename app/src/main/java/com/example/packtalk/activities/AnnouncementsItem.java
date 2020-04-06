package com.example.packtalk.activities;

public class AnnouncementsItem {

    String username, announcement, date;
    int userProfilePicture;

    public AnnouncementsItem(){
    }

    public AnnouncementsItem(String username, String announcement, String date, int userProfilePicture) {
        this.username = username;
        this.announcement = announcement;
        this.date = date;
        this.userProfilePicture = userProfilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(int userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }
}
