package com.example.mad_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_profile extends AppCompatActivity {

    TextView username;
    EditText name, email,number, address;
    //    CircleImageView img;
    Button btnUpdate, btnDelete;

    DatabaseReference db;

    User acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name=findViewById(R.id.pname);
        email=findViewById(R.id.pemail);
        number=findViewById(R.id.pnumber);
        address=findViewById(R.id.paddress);
        username=findViewById(R.id.pusername);

//        img = (CircleImageView)findViewById(R.id.profile_image);

        btnUpdate=findViewById(R.id.btn_up);
        btnDelete=findViewById(R.id.btn_del);

//        Intent intent = getIntent();
//        View view = null;
//        ShowProfile(View view);
        acc = new User();

    }




    //Method to clear all user Inputs
    public void clearFields(){

        name.setText("");
        email.setText("");
        number.setText("");
        address.setText("");
        username.setText("");

    }

    public void Show(View view){

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Account").child("AC2");

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()){

                    name.setText(dataSnapshot.child("name").getValue().toString());
                    number.setText(dataSnapshot.child("number").getValue().toString());
                    email.setText(dataSnapshot.child("email").getValue().toString());
                    address.setText(dataSnapshot.child("address").getValue().toString());
                    username.setText(dataSnapshot.child("username").getValue().toString());
//                    img.setImageBitmap(dataSnapshot.child("profileurl").getValue().toString());

                }

                else{

                    Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void Update(View view){

        DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("Account");
        upRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild("AC4")){

                    try{

                        acc.setName(name.getText().toString().trim());
                        acc.setEmail(email.getText().toString().trim());
                        acc.setAddress(address.getText().toString().trim());
                        acc.setUsername(username.getText().toString().trim());
                        acc.setNumber(Integer.parseInt(number.getText().toString().trim()));


                        db = FirebaseDatabase.getInstance().getReference().child("Account").child("AC2");
                        db.setValue(acc);
                        clearFields();
                        Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();



                    }catch(NumberFormatException e){
                        Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }


    public void Delete(View view){

        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Account");

        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                if (snapshot.hasChild(("AC2"))){

                    db = FirebaseDatabase.getInstance().getReference().child("Account").child("AC2");
                    db.removeValue();
                    clearFields();

                    Toast.makeText(getApplicationContext(), "Data Deleted Successfully" , Toast.LENGTH_SHORT).show();



                }
                else{

                    Toast.makeText(getApplicationContext(), "No Source to Delete", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}