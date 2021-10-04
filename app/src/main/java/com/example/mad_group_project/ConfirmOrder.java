package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmOrder extends AppCompatActivity {

    RecyclerView RV;
//    TextView TAmount;
//    int overAllTotalPrice;

    ConfirmAdaptor confirmAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        //Assigning recycler view
        RV= (RecyclerView)findViewById(R.id.rv3);

        RV.setLayoutManager(new LinearLayoutManager(this));

//        TAmount = findViewById(R.id.total_amount);

        FirebaseRecyclerOptions<FoodCart> options =
                new FirebaseRecyclerOptions.Builder<FoodCart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child("C1"), FoodCart.class)
                        .build();


        Log.d("OrderItems", options.getSnapshots().toString());

//        System.out.println("--------------------------------------------------------------");

        confirmAdaptor = new ConfirmAdaptor(options);


        RV.setAdapter(confirmAdaptor);
    }



    @Override
    protected void onStart() {
        super.onStart();

        confirmAdaptor.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        confirmAdaptor.stopListening();
    }

}
