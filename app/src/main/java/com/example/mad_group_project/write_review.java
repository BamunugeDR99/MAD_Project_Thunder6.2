package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class write_review extends AppCompatActivity {

    EditText review;
    TextView name;
    ImageView img;
    Button rev_btn;
    RatingBar rating;

    writerev rev;

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        name=findViewById(R.id.item_name);
        img=findViewById(R.id.item_img);
        review = findViewById(R.id.review);
        rating=findViewById(R.id.ratingBar);

        rev_btn = findViewById(R.id.rev_btn);

        rev = new writerev();

//       rev_btn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               String s = String.valueOf(rating.getRating());
//               Toast.makeText(getApplicationContext(),s+"rating",Toast.LENGTH_SHORT).show();
//           }
//       });

    }
    public void Save(View view){

        db = FirebaseDatabase.getInstance().getReference().child("WriteReview");

//        rating= Float.parseFloat(String.valueOf(rating.getRating()));
//        rev=review.getText().toString().trim();

        if(TextUtils.isEmpty(review.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"please enter the review", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(rating.toString().trim())){
            Toast.makeText(getApplicationContext(),"please rate", Toast.LENGTH_LONG).show();
        }
        else {
            rev.setReview(review.getText().toString().trim());
//            rev.setRating(Float.parseFloat(String.valueOf(rating.getRating())));
//            String s = String.valueOf(rating.getRating());

            db.push().setValue(rev);
//            db.child("WR2").setValue(rev);
            Toast.makeText(getApplicationContext(),"successful insertion", Toast.LENGTH_LONG).show();
        }
    }
}