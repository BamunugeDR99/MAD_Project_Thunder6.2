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

public class login extends AppCompatActivity {

    EditText email,pass;
    TextView signup;
    Button btnLogin;

    FirebaseAuth mAuth;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.log_email);
        pass=findViewById(R.id.log_pass);

        btnLogin=findViewById(R.id.log_btn);
        signup=findViewById(R.id.sign_txt);

        mAuth= FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rEmail=email.getText().toString().trim();
                String rPass=pass.getText().toString().trim();

                if(TextUtils.isEmpty(rEmail)){
                    email.setError("Required Field...");
                    return;
                }if (TextUtils.isEmpty(rPass)){
                    pass.setError("Required Field..");
                    return;
                }
                mDialog.setMessage("Processing..");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(rEmail,rPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                            Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Failed..",Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        }
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),sign_up_page.class));
            }
        });
    }
}
