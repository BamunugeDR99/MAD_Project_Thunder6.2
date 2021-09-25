package com.example.mad_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {

    EditText username, name,email,pass,number,address;
    TextView signin;
    Button btnReg;

    FirebaseAuth mAuth;
    ProgressDialog mDialog;

    DatabaseReference db;

    register reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        mDialog = new ProgressDialog(this);

        username = findViewById(R.id.reg_username);
        name = findViewById(R.id.reg_username);
        email = findViewById(R.id.reg_email);
        pass = findViewById(R.id.reg_pass);
        number = findViewById(R.id.reg_number);
        address = findViewById(R.id.reg_address);

        signin = findViewById(R.id.reg_txt);
        btnReg = findViewById(R.id.reg_btn);

        reg = new register();

//        btnReg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
//


    }

    public void save(View view) {
        db = FirebaseDatabase.getInstance().getReference().child("Account");

            try{
                if(TextUtils.isEmpty(username.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"please enter the Username", Toast.LENGTH_LONG).show();

                }
                else if(TextUtils.isEmpty(name.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "please enter the name", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(email.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "please enter the email", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(pass.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "please enter the password", Toast.LENGTH_LONG).show();
                }

                else if(TextUtils.isEmpty(number.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "please enter the number", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(address.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "please enter the address", Toast.LENGTH_LONG).show();

                }else{

//
//                    reg.setName(name.getText().toString().trim());
//                    reg.setEmail(email.getText().toString().trim());
//                    reg.setPassword(pass.getText().toString().trim());
//                    reg.setNumber(Integer.parseInt(number.getText().toString().trim()));
//                    reg.setAddress(address.getText().toString().trim());
//
////                    db.push().setValue(reg);
//                    db.child("AC3").setValue(reg);
//                    Toast.makeText(getApplicationContext(),"Successful Insertion", Toast.LENGTH_LONG).show();



                    //Authentication

                    String rEmail = email.getText().toString().trim();
                    String rPass = pass.getText().toString().trim();

                    if (TextUtils.isEmpty(rEmail)) {
                        email.setError("Required Field..");
                        return;
                    }
                    if (TextUtils.isEmpty(rPass)) {
                        pass.setError("Required Field...");
                        return;
                    }

                    mDialog.setMessage("Processing...");
                    mDialog.show();

//                save(View view);

                    mAuth.createUserWithEmailAndPassword(rEmail, rPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(),login.class));
                                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();

                                mDialog.dismiss();

                            } else {
                                Toast.makeText(getApplicationContext(), "Failed..", Toast.LENGTH_LONG).show();

                                mDialog.dismiss();
                            }
                        }


                    });
                }

            }catch (NumberFormatException e){
                Toast.makeText(getApplicationContext(),"Number Format Exception", Toast.LENGTH_LONG).show();
            }


        reg.setUsername(username.getText().toString().trim());
        reg.setName(name.getText().toString().trim());
        reg.setEmail(email.getText().toString().trim());
        reg.setPassword(pass.getText().toString().trim());
        reg.setNumber(Integer.parseInt(number.getText().toString().trim()));
        reg.setAddress(address.getText().toString().trim());



//                    db.push().setValue(reg);
        db.child("AC5").setValue(reg);
        Toast.makeText(getApplicationContext(),"Successful Insertion", Toast.LENGTH_LONG).show();

    }
}