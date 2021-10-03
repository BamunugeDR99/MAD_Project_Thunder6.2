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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class WishList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    RecyclerView wl_rv;
    WishListAdapter wishListAdapter;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        wl_rv = (RecyclerView) findViewById(R.id.rv);

        //wl_rv.setLayoutManager(new LinearLayoutManager(this));
        wl_rv.setLayoutManager(new CustomLinearLayoutManager(this));

        // wl_rv.setLayoutManager(new LinearLayoutManagerWrapper(this));
//
//        wl_rv.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<WishListModel> options =
                new FirebaseRecyclerOptions.Builder<WishListModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("WishList").child("C1"), WishListModel.class)
                        .build();


        wishListAdapter = new WishListAdapter(options);

        wl_rv.setAdapter(wishListAdapter);






        int total = 0;
        for(int i = 0; i < wishListAdapter.getItemCount(); i++){
            TextView text = ((TextView) wl_rv.findViewHolderForAdapterPosition(i)
                    .itemView.findViewById(R.id.wl_price));

            Log.d("ItemPrice", wl_rv.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.wl_price).toString());
            total = total + Integer.parseInt(text.getText().toString());

            Log.d("Total22", String.valueOf(total));

        }

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

        wishListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wishListAdapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.wl_search, menu);

        MenuItem item = menu.findItem(R.id.wl_search);
        SearchView wl_Search =(SearchView)item.getActionView();


        wl_Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {


                txtSearch(query);
                return false;
            }


        });


        return super.onCreateOptionsMenu(menu);
    }



    private  void txtSearch(String str ){


        FirebaseRecyclerOptions<WishListModel> options =
                new FirebaseRecyclerOptions.Builder<WishListModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("WishList").child("C1").orderByChild("ItemName").startAt(str).endAt(str + "~"), WishListModel.class)
                        .build();



        wishListAdapter = new WishListAdapter(options);


        wishListAdapter.startListening();

        wl_rv.setAdapter(wishListAdapter);

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
                Intent intent = new Intent(WishList.this, contactus.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(WishList.this, edit_profile.class);
                startActivity(intent1);
                break;
            case R.id.nav_wishlist:
//                Intent intent2 = new Intent(WishList.this, WishList.class);
//                startActivity(intent2);
                break;
            case R.id.nav_cart:
                Intent intent3 = new Intent(WishList.this, myPurchases.class);
                startActivity(intent3);
                break;
            case R.id.nav_reviews:
                Intent intent4 = new Intent(WishList.this, your_reviews.class);
                startActivity(intent4);
                break;
            case R.id.nav_contact:
                Intent intent5 = new Intent(WishList.this, contactus.class);
                startActivity(intent5);
                break;
            case R.id.nav_logout:
                Intent intent6 = new Intent(WishList.this, login.class);
                startActivity(intent6);
                break;
        }
        return true;
    }

}