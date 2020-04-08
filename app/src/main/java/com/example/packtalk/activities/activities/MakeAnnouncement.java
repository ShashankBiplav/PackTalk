package com.example.packtalk.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packtalk.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MakeAnnouncement extends AppCompatActivity {

    //UI components
    private TextView announcementActivityUsername, announcementActivityEmail;
    private EditText editTextUserAnnouncement;
    private Button buttonPostAnnouncement;

    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_announcement);

        announcementActivityUsername = findViewById(R.id.announcementActivityUsername);
        announcementActivityEmail = findViewById(R.id.announcementActivityEmail);
        editTextUserAnnouncement = findViewById(R.id.editTextUserAnnouncement);
        buttonPostAnnouncement = findViewById(R.id.buttonPostAnnouncement);

        //calling parseServer for user information access
        currentUser = ParseUser.getCurrentUser();

        announcementActivityUsername.setText(currentUser.get("username").toString());
        announcementActivityEmail.setText(currentUser.get("email").toString());

    }

    public void postAnnouncement(View view){
        ParseObject parseObject = new ParseObject("myAnnouncement");
        parseObject.put("announcement", editTextUserAnnouncement.getText().toString());
        parseObject.put("user",ParseUser.getCurrentUser().getUsername());

        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Posting Announcement");
        progressDialog.show();

        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FancyToast.makeText(MakeAnnouncement.this,
                            currentUser.getUsername() + "'s announcement has been posted successfully!",
                            Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                            true).show();
                    Intent intent = new Intent(MakeAnnouncement.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    FancyToast.makeText(MakeAnnouncement.this,
                            "An error occurred!" + "\n" + e.getMessage(),
                            Toast.LENGTH_LONG, FancyToast.ERROR,
                            true).show();
                }
                progressDialog.dismiss();
            }
        });

    }
}
