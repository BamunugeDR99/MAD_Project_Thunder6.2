package com.example.mad_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class HomeUI extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CardView cakeCard,giftCard,flowersCard,chocolatesCard,combosCard;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ui);


        cakeCard = (CardView) findViewById(R.id.CakeCard);
        giftCard = (CardView) findViewById(R.id.GiftsCard);
        flowersCard = (CardView) findViewById(R.id.FlowersCard);
        chocolatesCard = (CardView) findViewById(R.id.ChocolatesCard);
        combosCard = (CardView) findViewById(R.id.CombosCard);


        cakeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), CakeItems.class));


            }


        });


        chocolatesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ChocolateItems.class));
            }
        });


        giftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), GiftItems.class));
            }
        });


        flowersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), FlowerItems.class));
            }
        });


        combosCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ComboItems.class));
            }
        });







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
//                Intent intent = new Intent(contactus.this, contactus.class);
//                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(HomeUI.this, user_profile.class);
                startActivity(intent1);
                break;
            case R.id.nav_wishlist:
                Intent intent2 = new Intent(HomeUI.this, WishList.class);
                startActivity(intent2);
                break;
            case R.id.nav_purchases:
                Intent intent3 = new Intent(HomeUI.this, myPurchases.class);
                startActivity(intent3);
                break;
            case R.id.nav_cart:
                Intent intent7 = new Intent(HomeUI.this, edit_cart.class);
                startActivity(intent7);
                break;
            case R.id.nav_reviews:
                Intent intent4 = new Intent(HomeUI.this, your_reviews.class);
                startActivity(intent4);
                break;
            case R.id.nav_contact:
                Intent intent5 = new Intent(HomeUI.this, contactus.class);
                startActivity(intent5);
                break;
            case R.id.nav_logout:
                Intent intent6 = new Intent(HomeUI.this, login.class);
                startActivity(intent6);
                break;
        }
        return true;
    }
}