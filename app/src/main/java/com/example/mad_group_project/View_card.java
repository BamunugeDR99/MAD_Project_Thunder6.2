package com.example.mad_group_project;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class View_card extends AppCompatActivity {
    FloatingActionButton AC_FL_BTN;
    RecyclerView RV_3;

    ImageButton image_btn;

    ViewCardAdapter viewCardAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);

        image_btn=(ImageButton) findViewById(R.id.imgbtn);

        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent = new Intent(write_review.this, customer_reviews.class);
//                startActivity(intent);
            }
        });

        //Assigning recycler view
        RV_3= (RecyclerView)findViewById(R.id.RV_3);

        RV_3.setItemAnimator(null);

        RV_3.setLayoutManager(new LinearLayoutManager(this));

        AC_FL_BTN=(FloatingActionButton) findViewById(R.id.AC_FL_BTN);





        FirebaseRecyclerOptions<CardModel> options =
                new FirebaseRecyclerOptions.Builder<CardModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Card Details").child("Cus1"), CardModel.class)
                        .build();


        viewCardAdapter = new ViewCardAdapter(options);

        RV_3.setAdapter(viewCardAdapter);

        AC_FL_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Add_New_Payment.class));
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        viewCardAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewCardAdapter.stopListening();
    }
}


