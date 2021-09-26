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

public class contactus extends AppCompatActivity {


    EditText et_name, et_email, et_massage;
    Button btn_submit;

    Contact contact;

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_massage = findViewById(R.id.et_massage);

        btn_submit = findViewById(R.id.btn_submit);

        contact = new Contact();
    }

    public void Save(View view){

        db = FirebaseDatabase.getInstance().getReference().child("Contact");


        if(TextUtils.isEmpty(et_name.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"please enter the name", Toast.LENGTH_LONG).show();

        }else if(TextUtils.isEmpty(et_email.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"please enter the email", Toast.LENGTH_LONG).show();

        }else if(TextUtils.isEmpty(et_massage.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "please enter the massage", Toast.LENGTH_LONG).show();
        }else {
            contact.setName(et_name.getText().toString().trim());
            contact.setEmail(et_email.getText().toString().trim());
            contact.setMessege(et_massage.getText().toString().trim());

            db.push().setValue(contact);

            Toast.makeText(getApplicationContext(),"successful insertion", Toast.LENGTH_LONG).show();
        }

    }
}





