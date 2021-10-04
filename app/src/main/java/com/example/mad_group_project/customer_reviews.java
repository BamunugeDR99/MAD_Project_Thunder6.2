package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.util.Log;

import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class customer_reviews extends AppCompatActivity {

    RecyclerView rv2;

    CustomerAdaptor cusAdapter;
    String category;
    String position;
    String image, itemName;
//    String value="Review";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reviews);

        //Assigning recycler view
        rv2 = (RecyclerView) findViewById(R.id.rv2);

        rv2.setLayoutManager(new LinearLayoutManager(this));

         category = getIntent().getStringExtra("Category");
         position = getIntent().getStringExtra("Position");
         image = getIntent().getStringExtra("Image");
         itemName = getIntent().getStringExtra("ItemName");

        Log.d("Category",category);
        Log.d("Position",position);

        Log.d("Image", image);




        FirebaseRecyclerOptions<cusreview> options =
                new FirebaseRecyclerOptions.Builder<cusreview>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Items").child(category).child(position).child("Reviews"), cusreview.class)
                        .build();


        cusAdapter = new CustomerAdaptor(options);

        rv2.setAdapter(cusAdapter);

//
//        Intent intent = getIntent();
////        String value1= intent.getStringExtra("item_name");
////        String value3= intent.getStringExtra("test");
//        String value2= intent.getStringExtra("item_img");
////        Toast.makeText(getApplicationContext(),value1,Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(),value2,Toast.LENGTH_LONG).show();
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



    public void go(View view) {
        Intent intent = new Intent(customer_reviews.this, write_review.class);
        intent.putExtra("Category",category);
        intent.putExtra("Image", image);
        intent.putExtra("Position", position);
        intent.putExtra("ItemName", itemName);
        startActivity(intent);


    }
}

