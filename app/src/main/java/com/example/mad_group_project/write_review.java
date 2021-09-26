package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class write_review extends AppCompatActivity {

    EditText review;
    Button rev_btn;

    writerev rev;

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        review = findViewById(R.id.review);

        rev_btn = findViewById(R.id.rev_btn);

        rev = new writerev();

    }

    public void Save(View view){

        db = FirebaseDatabase.getInstance().getReference().child("WriteReview");

        if(TextUtils.isEmpty(review.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"please enter the review", Toast.LENGTH_LONG).show();

        }else {
            rev.setReview(review.getText().toString().trim());

//            db.push().setValue(rev);
            db.child("WR2").setValue(rev);

            Toast.makeText(getApplicationContext(),"successful insertion", Toast.LENGTH_LONG).show();
        }
    }

}