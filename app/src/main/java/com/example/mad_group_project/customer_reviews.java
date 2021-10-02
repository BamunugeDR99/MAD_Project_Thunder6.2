package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class customer_reviews extends AppCompatActivity {

    RecyclerView rv2;

    CustomerAdaptor cusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reviews);

        //Assigning recycler view
        rv2 = (RecyclerView) findViewById(R.id.rv2);

        rv2.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<cusreview> options =
                new FirebaseRecyclerOptions.Builder<cusreview>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Items").child("Cakes").child("C1").child("Reviews"), cusreview.class)
                        .build();


        cusAdapter = new CustomerAdaptor(options);

        rv2.setAdapter(cusAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();

        cusAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cusAdapter.stopListening();
    }

}

