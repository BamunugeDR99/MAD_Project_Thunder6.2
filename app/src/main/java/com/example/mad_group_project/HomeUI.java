package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeUI extends AppCompatActivity {

    CardView cakeCard,giftCard,flowersCard,chocolatesCard,combosCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ui);


        cakeCard = (CardView) findViewById(R.id.CakeCard);
        giftCard = (CardView) findViewById(R.id.GiftsCard);
        flowersCard = (CardView) findViewById(R.id.FlowersCard);
        chocolatesCard = (CardView) findViewById(R.id.ChocolatesCard);
        combosCard = (CardView) findViewById(R.id.CombosCard);


        cakeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), CakeItems.class));


            }
        });

    }
}