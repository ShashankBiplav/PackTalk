package com.example.packtalk.activities.allactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packtalk.R;
import com.example.packtalk.activities.adapters.TabAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar tabsHolderToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;

    //Navigation Drawer UI components
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ParseUser currentUser;
    private ImageView navHeaderProfilePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //calling parseServer for user information access
        currentUser = ParseUser.getCurrentUser();

        navigationView =findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        //adding header through the code  and inflating header to set TextView(s) dynamically
        LinearLayout navHeader=(LinearLayout) LayoutInflater.from(this).inflate(R.layout.header, null);
        navigationView.addHeaderView(navHeader);
        TextView headerUsername = (TextView) navHeader.findViewById (R.id.navHeaderUsername);
        TextView headerEmail = (TextView) navHeader.findViewById (R.id.navHeaderEmail);
        navHeaderProfilePicture = navHeader.findViewById(R.id.navHeaderProfilePicture);
        headerUsername.setText(currentUser.get("username").toString());
        headerEmail.setText(currentUser.get("email").toString());

        //setting user profile picture in nav header
        ParseFile profilePicture = (ParseFile) currentUser.get("profilePicture");

        if (profilePicture !=null) {
            profilePicture.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (data != null && e == null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        navHeaderProfilePicture.setImageBitmap(bitmap);
                    } else {
                        navHeaderProfilePicture.setImageResource(R.drawable.ic_defaultprofilepicture);
                    }
                }
            });
        }
        else{
            navHeaderProfilePicture.setImageResource(R.drawable.ic_defaultprofilepicture);
        }


        //custom toolbar
        tabsHolderToolbar = findViewById(R.id.tabs_Holder_Toolbar);
        setSupportActionBar(tabsHolderToolbar);
        tabsHolderToolbar.setTitle(R.string.app_name);


        //Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, tabsHolderToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //removing icon tint from the icons in navigation drawer
        navigationView.setItemIconTintList(null);


        //to make navigation drawer items clickable
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);


        //for displaying fragments
        viewPager = findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager(),0); //check for behaviour later
        viewPager.setAdapter(tabAdapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, false);

        //setting fragment icons and removing icon tint
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_posts);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_announcement_feed);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_chats);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_folks);
        tabLayout.setTabIconTint(null);


    }
    //on pressing back button instead of closing the app navigation drawer closes
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    //handling navigation drawer activities
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemUpdateProfile:
                Intent intent = new Intent(HomeActivity.this, UpdateProfile.class);
                startActivity(intent);
                finish();
                break;
            case R.id.itemAllUsers:
                Intent intent1  = new Intent(HomeActivity.this, AllUsers.class);
                startActivity(intent1);
                break;
            case R.id.itemLikedPosts:
                break;
            case R.id.itemSavedAnnouncements:
                break;
            case R.id.itemSettings:
                break;
            case R.id.itemLogout:
                ParseUser.getCurrentUser().logOut();

                FancyToast.makeText(HomeActivity.this,
                        currentUser.getUsername() + " , you are Logged Out",
                        Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                        true).show();
                Intent intentLogout = new Intent(HomeActivity.this, Login.class);
                startActivity(intentLogout);
                finish();
                break;
            case R.id.itemAboutApp:
                break;
            case R.id.itemAboutDeveloper:
                break;
        }
        return true;
    }
    //displaying custom buttons on action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }
    // function for app bar -> buttons click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCreatePost:
                Intent intent = new Intent(HomeActivity.this, CreatePost.class);
                startActivity(intent);
                return true;

            case R.id.menuAnnouncements:
                Intent intent1 = new Intent(HomeActivity.this, MakeAnnouncement.class);
                startActivity(intent1);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
