package com.example.mad_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class your_reviews extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rv;

    YourAdaptor yourAdaptor;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_reviews);

        //Assigning recycler view
        rv = (RecyclerView) findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<yourreviews> options =
                new FirebaseRecyclerOptions.Builder<yourreviews>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("YourReviews"), yourreviews.class)
                        .build();


        yourAdaptor = new YourAdaptor(options);

        rv.setAdapter(yourAdaptor);

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

        navigationView.setCheckedItem(R.id.nav_home);
    }


    @Override
    protected void onStart() {
        super.onStart();

        yourAdaptor.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        yourAdaptor.stopListening();
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
                Intent intent1 = new Intent(your_reviews.this, user_profile.class);
                startActivity(intent1);
                break;
            case R.id.nav_wishlist:
//                Intent intent2 = new Intent(MainActivity2.this, contactus.class);
//                startActivity(intent2);
                break;
            case R.id.nav_cart:
//                Intent intent3 = new Intent(MainActivity2.this, your_reviews.class);
//                startActivity(intent3);
                break;
            case R.id.nav_reviews:
//                Intent intent4 = new Intent(your_reviews.this, your_reviews.class);
//                startActivity(intent4);
                break;
            case R.id.nav_contact:
                Intent intent5 = new Intent(your_reviews.this, contactus.class);
                startActivity(intent5);
                break;
            case R.id.nav_logout:
                Intent intent6 = new Intent(your_reviews.this, login.class);
                startActivity(intent6);
                break;
        }
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.search,menu);
//        MenuItem item= menu.findItem(R.id.search);
//        SearchView searchView = (SearchView)item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                txtSearch(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                txtSearch(query);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    private void txtSearch(String str)
//    {
//        FirebaseRecyclerOptions<yourreviews> options =
//                new FirebaseRecyclerOptions.Builder<yourreviews>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Reviews").orderByChild("name").startAt(str).endAt(str+"~"), review.class)
//                        .build();
//        yourAdaptor = new YourAdaptor(options);
//        yourAdaptor.startListening();
//        recyclerView.setAdapter(yourAdaptor);
//    }

}