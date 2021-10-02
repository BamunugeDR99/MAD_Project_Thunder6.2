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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class myPurchases extends AppCompatActivity {

    RecyclerView RV_1;
    TextView TAmount;
    int overAllTotalPrice;



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
}


