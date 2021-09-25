package com.example.mad_group_project;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class myPurchases extends AppCompatActivity {

    RecyclerView RV_1;

    MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);

        //Assigning recycler view
        RV_1= (RecyclerView)findViewById(R.id.RV_1);

        RV_1.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<purchases> options =
                new FirebaseRecyclerOptions.Builder<purchases>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("myPurchases"), purchases.class)
                        .build();


        mainAdapter = new MainAdapter(options);

        RV_1.setAdapter(mainAdapter);
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
}


