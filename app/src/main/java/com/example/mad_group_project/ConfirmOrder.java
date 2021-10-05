package com.example.mad_group_project;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmOrder extends AppCompatActivity {

    RecyclerView RV;
    TextView TAmount,Type,Number;

    Button confirm,cancel;
    ImageButton image_btn;

    Context context;

    String type, number, positionS,name;
//    int overAllTotalPrice;

    ConfirmAdaptor confirmAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

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

    public void Confirm(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(Html.fromHtml("<font color='#4CAF50'>Your Order has been Successfully Placed!</font>"));
        alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000000'>Thanks for Shopping with Us!</font></font>"));
        alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ConfirmOrder.this,HomeUI.class);
                       startActivity(intent);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void cancel(View view) {
        Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(ConfirmOrder.this,edit_cart.class);
        startActivity(intent2);

    }
}
