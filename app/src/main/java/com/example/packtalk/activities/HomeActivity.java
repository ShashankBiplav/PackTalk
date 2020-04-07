package com.example.packtalk.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.packtalk.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseUser;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar tabsHolderToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;

    //Navigation Drawer UI components
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

//    private String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //calling parseServer for user information access
        final ParseUser currentUser = ParseUser.getCurrentUser();

        navigationView =findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        //adding header through the code  and inflating header to set TextView(s) dynamically
        LinearLayout navHeader=(LinearLayout) LayoutInflater.from(this).inflate(R.layout.header, null);
        navigationView.addHeaderView(navHeader);
        TextView headerUsername = (TextView) navHeader.findViewById (R.id.navHeaderUsername);
        TextView headerEmail = (TextView) navHeader.findViewById (R.id.navHeaderEmail);
        headerUsername.setText(currentUser.get("username").toString());
        headerEmail.setText(currentUser.get("email").toString());


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
        //removing tint from the icons in navigation drawer
        navigationView.setItemIconTintList(null);

        //setting username and email of header in navigation drawer dynamically
//        if( navigationView != null ){
//            LinearLayout mParent = ( LinearLayout ) navigationView.getHeaderView( 0 );
//
//            if( mParent != null ){
//                // Set your values to the image and text view by declaring and setting as you need to here.
//                headerUsername.setText( Objects.requireNonNull(currentUser.get("username")).toString());
//                headerEmail.setText(Objects.requireNonNull(currentUser.get("email")).toString());
//            }
//        }


        //to make navigation drawer items clickable
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);





        //for displaying fragments
        viewPager = findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager(),0); //check for behaviour later
        viewPager.setAdapter(tabAdapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, false);


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
                break;
            case R.id.itemLikedPosts:
                break;
            case R.id.itemSavedAnnouncements:
                break;
            case R.id.itemSettings:
                break;
            case R.id.itemLogout:
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
