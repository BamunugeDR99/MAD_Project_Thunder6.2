package com.example.mad_group_project;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmOrder extends AppCompatActivity {

    RecyclerView RV;
    TextView TAmount,Type,Number;



    String type, number, positionS,name;
//    int overAllTotalPrice;

    ConfirmAdaptor confirmAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);



        //Assigning recycler view
        RV= (RecyclerView)findViewById(R.id.rv3);

        RV.setLayoutManager(new LinearLayoutManager(this));

        TAmount = findViewById(R.id.total_amount);
        Type=findViewById(R.id.card_type);
        Number=findViewById(R.id.card_number);

        Intent intent = getIntent();

        type = getIntent().getStringExtra("Type");
        number = getIntent().getStringExtra("Number");
        positionS = getIntent().getStringExtra("Position");

        Type.setText(type);
        Number.setText(number);


        FirebaseRecyclerOptions<FoodCart> options =
                new FirebaseRecyclerOptions.Builder<FoodCart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child("C1"), FoodCart.class)
                        .build();


        Log.d("OrderItems", options.getSnapshots().toString());

//        System.out.println("--------------------------------------------------------------");

        confirmAdaptor = new ConfirmAdaptor(options);


        RV.setAdapter(confirmAdaptor);

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("Total Amount"));
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

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int Total = intent.getIntExtra("TotalPrice",0);
            TAmount.setText("LKR. "+Total+".00");
        }
    };

}
