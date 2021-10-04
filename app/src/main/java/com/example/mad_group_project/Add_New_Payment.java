package com.example.mad_group_project;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class Add_New_Payment extends AppCompatActivity {

    RadioButton AC_Radio_BTN1,AC_Radio_BTN2;
    EditText AC_Card_Owner,AC_Card_Number,AC_Card_Date;
    Button AC_BTN_Submit;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_payment);

        AC_Radio_BTN1=(RadioButton) findViewById(R.id.AC_Radio_BTN1);
        AC_Radio_BTN2= (RadioButton) findViewById(R.id.AC_Radio_BTN2);

        AC_Card_Owner=(EditText) findViewById(R.id.AC_Card_Owner);
        AC_Card_Number=(EditText) findViewById(R.id.AC_Card_Number);
        AC_Card_Date=(EditText) findViewById(R.id.AC_Card_Date);

        AC_BTN_Submit=(Button) findViewById(R.id.AC_BTN_Submit);

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        //Validate AC_Card_Number
        awesomeValidation.addValidation(this,R.id.AC_Card_Number,
                "[0-9]{16}$",R.string.invalid_number);
        //Validate AC_Card_Owner
        awesomeValidation.addValidation(this,R.id.AC_Card_Owner,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        //Validate AC_Card_Date
        awesomeValidation.addValidation(this,R.id.AC_Card_Date,
                RegexTemplate.NOT_EMPTY,R.string.invalid_date);


        AC_BTN_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check Validation
                if (awesomeValidation.validate()) {
                    //On Success
                    Toast.makeText(getApplicationContext(), "Data Validated Successfully!!",Toast.LENGTH_SHORT).show();
                    insertData();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Data Validation Failed!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void ACClearALL(){

        AC_Card_Owner.setText("");
        AC_Card_Number.setText("");
        AC_Card_Date.setText("");

    }

    private  void insertData()
    {
        Map<String, Object> map = new HashMap<>();

        map.put("name", AC_Card_Owner.getText().toString());
        map.put("number", Long.parseLong(AC_Card_Number.getText().toString()));
        map.put("date", AC_Card_Date.getText().toString());
        map.put("image", "data:image/png;base64,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");

        FirebaseDatabase.getInstance().getReference().child("Card Details").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(Add_New_Payment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        ACClearALL();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {

                        Toast.makeText(Add_New_Payment.this, "Error while Insertion", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}