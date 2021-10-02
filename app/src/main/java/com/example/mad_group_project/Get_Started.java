package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Get_Started extends AppCompatActivity {

    ImageView logo;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

    start=findViewById(R.id.start);
    logo=findViewById(R.id.logo);

    }

    public void start(View view) {
        Intent intent = new Intent(Get_Started.this, login.class);
        startActivity(intent);
    }
}