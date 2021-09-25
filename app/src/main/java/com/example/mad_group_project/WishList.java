package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class WishList extends AppCompatActivity {


    RecyclerView wl_rv;
    WishListAdapter wishListAdapter;

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
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("WishList"), WishListModel.class)
                        .build();


        wishListAdapter = new WishListAdapter(options);

        wl_rv.setAdapter(wishListAdapter);
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
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("WishList").orderByChild("ItemName").startAt(str).endAt(str + "~"), WishListModel.class)
                        .build();



        wishListAdapter = new WishListAdapter(options);


        wishListAdapter.startListening();

        wl_rv.setAdapter(wishListAdapter);



    }
}