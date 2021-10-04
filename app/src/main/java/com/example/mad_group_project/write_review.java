package com.example.mad_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class write_review extends AppCompatActivity {

    EditText review;
    TextView name;
    ImageView img;
    Button rev_btn;
    RatingBar rating;
    ImageButton image_btn;

    writerev rev;



    String image, category, positionS,itemName;

    DatabaseReference db1;
    DatabaseReference db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        image_btn=(ImageButton) findViewById(R.id.imgbtn);

        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent = new Intent(write_review.this, customer_reviews.class);
//                startActivity(intent);
            }
        });


        review = findViewById(R.id.review);
        rating=findViewById(R.id.ratingBar);

        rev_btn = findViewById(R.id.rev_btn);

        rev = new writerev();

        Intent intent = getIntent();

        category = getIntent().getStringExtra("Category");
        positionS = getIntent().getStringExtra("Position");
        image = getIntent().getStringExtra("Image");
        itemName = getIntent().getStringExtra("ItemName");

        img = (ImageView)findViewById(R.id.item_img);

        Picasso.get()
                .load(image)
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark)
                .into(img);
    }
    public void Save(View view){

        db1 = FirebaseDatabase.getInstance().getReference().child("Items").child(category).child(positionS).child("Reviews");
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
            rev.setCusname("Tharindu");
            rev.setCusurl("https://www.clipartmax.com/png/middle/405-4050774_avatar-icon-flat-icon-shop-download-free-icons-for-avatar-icon-flat.png");
//
//            myrev.setDescription(review.getText().toString().trim());
//            myrev.setImageurl(image);
//            myrev.setName(itemName);
//            myrev.setRating(String.valueOf(rating.getRating()));
//
            yourreviews myrev = new yourreviews(itemName,review.getText().toString().trim(),image,String.valueOf(rating.getRating()));




//            Toast.makeText(getApplicationContext(),rev.getCusdescription().toString(), Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(),String.valueOf(rating.getRating()), Toast.LENGTH_LONG).show();



            db1.push().setValue(rev).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(getApplicationContext(),"successful insertion", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(write_review.this,customer_reviews.class);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                    Toast.makeText(getApplicationContext(),"Data Insertion Failed", Toast.LENGTH_LONG).show();

                }
            });




            db2.push().setValue(myrev).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(getApplicationContext(),"successful insertion", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                    Toast.makeText(getApplicationContext(),"Data Insertion Failed", Toast.LENGTH_LONG).show();

                }
            });








//            db2.push().setValue(myrev);
////            db.child("WR2").setValue(rev);

        }
    }
}