package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class edit_cart extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodCartAdapter foodCartAdapter;
    TextView TAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_cart_main);

        recyclerView = (RecyclerView)findViewById(R.id.CartRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TAmount=findViewById(R.id.total);





        //fetching data

        FirebaseRecyclerOptions<FoodCart> options =
                new FirebaseRecyclerOptions.Builder<FoodCart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child("C1"), FoodCart.class)
                        .build();

        foodCartAdapter = new FoodCartAdapter(options);
        recyclerView.setAdapter(foodCartAdapter);
//
//        LocalBroadcastManager.getInstance(this)
//                .registerReceiver(mMessageReceiver, new IntentFilter("Total Spending"));

//        int OverAllTotalPrice = getIntent().getIntExtra("OverAllTotalPrice",0);
//        TAmount.setText("LKR"+OverAllTotalPrice+".00");
    }

    @Override
    protected void onStart() {
        super.onStart();
        foodCartAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        foodCartAdapter.startListening();
    }

//    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            int OverAllTotalPrice = intent.getIntExtra("Total Amount",0);
//            TAmount.setText("LKR. "+OverAllTotalPrice+".00");
////
//        }
//    };


}