package com.example.mad_group_project;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class myPurchases extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView RV_1;
    TextView TAmount;
    int overAllTotalPrice;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);

        //Assigning recycler view
        RV_1= (RecyclerView)findViewById(R.id.RV_1);

        RV_1.setLayoutManager(new LinearLayoutManager(this));

        TAmount = findViewById(R.id.total_amount);

        FirebaseRecyclerOptions<purchases> options =
                new FirebaseRecyclerOptions.Builder<purchases>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("myPurchases"), purchases.class)
                        .build();

        System.out.println("--------------------------------------------------------------");

        mainAdapter = new MainAdapter(options);


        RV_1.setAdapter(mainAdapter);

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("Total Spending"));

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

        navigationView.setCheckedItem(R.id.nav_cart);
    }


    @Override
    protected void onStart() {
        super.onStart();

        mainAdapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int TotalPrice = intent.getIntExtra("TotalPrice",0);
            TAmount.setText("LKR. "+TotalPrice+".00");
        }
    };

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
                Intent intent = new Intent(myPurchases.this, HomeUI.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(myPurchases.this, user_profile.class);
                startActivity(intent1);
                break;
            case R.id.nav_wishlist:
                Intent intent2 = new Intent(myPurchases.this, WishList.class);
                startActivity(intent2);
                break;
            case R.id.nav_purchases:
//                Intent intent3 = new Intent(myPurchases.this, myPurchases.class);
//                startActivity(intent3);
                break;
            case R.id.nav_cart:
                Intent intent7 = new Intent(myPurchases.this, edit_cart.class);
                startActivity(intent7);
                break;
            case R.id.nav_reviews:
                Intent intent4 = new Intent(myPurchases.this, your_reviews.class);
                startActivity(intent4);
                break;
            case R.id.nav_contact:
                Intent intent5 = new Intent(myPurchases.this, contactus.class);
                startActivity(intent5);
                break;
            case R.id.nav_logout:
                Intent intent6 = new Intent(myPurchases.this, login.class);
                startActivity(intent6);
                break;
        }
        return true;
    }
}


