package com.example.packtalk.activities;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("QkL65bMraAY6lNtud5tRVtWqrgsKG8Z72rlWW8U4")
                .clientKey("aD9ZhOgON0N4f4otMmTKX148HbtKHfloY22n0Ejt")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
