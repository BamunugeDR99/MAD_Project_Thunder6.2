package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class CakeItems extends AppCompatActivity {

    RecyclerView Item_rv;
    ItemAdapter itemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_items);

        RecyclerView.LayoutManager layoutManager;

        Item_rv = (RecyclerView) findViewById(R.id.Item_RV);

        Item_rv.setItemAnimator(null);

        layoutManager = new GridLayoutManager(this, 2);

        Item_rv.setLayoutManager(layoutManager);



        FirebaseRecyclerOptions<ItemModel> options =
                new FirebaseRecyclerOptions.Builder<ItemModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Items").child("Cakes"), ItemModel.class)
                        .build();



        itemAdapter = new ItemAdapter(options);

        Item_rv.setAdapter(itemAdapter);



    }


    @Override
    protected void onStart() {
        super.onStart();

        itemAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        itemAdapter.stopListening();
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


        FirebaseRecyclerOptions<ItemModel> options =
                new FirebaseRecyclerOptions.Builder<ItemModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Items").child("Cakes").orderByChild("ItemName").startAt(str).endAt(str + "~"), ItemModel.class)
                        .build();



       itemAdapter = new ItemAdapter(options);


        itemAdapter.startListening();

        Item_rv.setAdapter(itemAdapter);

    }








}