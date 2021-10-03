package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

    DatabaseReference db1;
    DatabaseReference db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

//        value=findViewById(R.id.item_name);
//        img=findViewById(R.id.item_img);
        review = findViewById(R.id.review);
        rating=findViewById(R.id.ratingBar);

        rev_btn = findViewById(R.id.rev_btn);

        rev = new writerev();

        Intent intent = getIntent();
            String value= intent.getStringExtra("name");
            Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();

//        value=findViewById(R.id.item_name);


//       rev_btn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               String s = String.valueOf(rating.getRating());
//               Toast.makeText(getApplicationContext(),s+"rating",Toast.LENGTH_SHORT).show();
//           }
//       });

    }
    public void Save(View view){

        db1 = FirebaseDatabase.getInstance().getReference().child("Items").child("Cakes").child("C1").child("Reviews");
        db2 = FirebaseDatabase.getInstance().getReference().child("YourReviews").child("Cus1");

//        rating= Float.parseFloat(String.valueOf(rating.getRating()));
//        rev=review.getText().toString().trim();

        if(TextUtils.isEmpty(review.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"please enter the review", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(rating.toString().trim())){
            Toast.makeText(getApplicationContext(),"please rate", Toast.LENGTH_LONG).show();
        }
        else {
            rev.setCusdescription(review.getText().toString().trim());
            rev.setRating(String.valueOf(rating.getRating()));
//            rev.setRating(Float.parseFloat(String.valueOf(rating.getRating())));

            Toast.makeText(getApplicationContext(),rev.getCusdescription().toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),String.valueOf(rating.getRating()), Toast.LENGTH_LONG).show();



            db1.push().setValue(rev);
            db2.push().setValue(rev);
//            db.child("WR2").setValue(rev);
            Toast.makeText(getApplicationContext(),"successful insertion", Toast.LENGTH_LONG).show();
        }
    }
}