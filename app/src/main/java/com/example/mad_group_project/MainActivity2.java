package com.example.mad_group_project;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

//        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_logout).setVisible(false);

//        navigationView.bringChildToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

//        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
//                Intent intent = new Intent(MainActivity2.this, contactus.class);
//                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(MainActivity2.this, edit_profile.class);
                startActivity(intent1);
                break;
            case R.id.nav_wishlist:
                Intent intent2 = new Intent(MainActivity2.this, WishList.class);
                startActivity(intent2);
                break;
            case R.id.nav_cart:
                Intent intent3 = new Intent(MainActivity2.this, myPurchases.class);
                startActivity(intent3);
                break;
            case R.id.nav_reviews:
                Intent intent4 = new Intent(MainActivity2.this, your_reviews.class);
                startActivity(intent4);
                break;
            case R.id.nav_contact:
                Intent intent5 = new Intent(MainActivity2.this, contactus.class);
                startActivity(intent5);
                break;
            case R.id.nav_logout:
                Intent intent6 = new Intent(MainActivity2.this, login.class);
                startActivity(intent6);
                break;
        }
        return true;
    }
}