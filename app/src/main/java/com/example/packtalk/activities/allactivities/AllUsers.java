package com.example.packtalk.activities.allactivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.packtalk.R;
import com.example.packtalk.activities.adapters.AllUsersAdapter;
import com.example.packtalk.activities.models.AllUsersItem;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity {

    private RecyclerView recyclerViewAllUsers;
    AllUsersAdapter mAllUsersAdapter;
    List<AllUsersItem> mAllUsersItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        //initialising view
        recyclerViewAllUsers = findViewById(R.id.recyclerViewAllUsers);
        mAllUsersItem = new ArrayList<>();

//        try{
//            final ProgressDialog progressDialog = ProgressDialog.show(AllUsers.this, "Loading...", "Please wait...", true);
//
//            ParseQuery<ParseUser> query = ParseQuery.getQuery("User");
//            query.whereNotEqualTo("email",ParseUser.getCurrentUser().getEmail());
//            query.findInBackground(new FindCallback<ParseUser>() {
//                @Override
//                public void done(List<ParseUser> objects, ParseException e) {
//                    if (objects.size()>0 && e == null){
//                        for (ParseUser allUsers : objects){
//                            mAllUsersItem.add(new AllUsersItem(allUsers.getUsername().toString(),
//                                    allUsers.getEmail().toString(),R.drawable.ic_developer,true));
//                        }
//
//                    }
//
//                }
//            });
//
//            progressDialog.dismiss();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        mAllUsersItem.add(new AllUsersItem("Shashank","shashankbiplav@gmail.com",R.drawable.ic_developer,true));
        //adapter initialization
        mAllUsersAdapter = new AllUsersAdapter(this, mAllUsersItem );
        recyclerViewAllUsers.setAdapter(mAllUsersAdapter);
        recyclerViewAllUsers.setLayoutManager(new LinearLayoutManager(this));
    }
}
