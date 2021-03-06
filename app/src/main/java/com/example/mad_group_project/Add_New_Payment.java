package com.example.mad_group_project;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
public class Add_New_Payment extends AppCompatActivity {


    private RadioGroup radioGroup;
    private RadioButton radioButton;
    EditText AC_Card_Owner,AC_Card_Number,AC_Card_Date;
    ImageButton image_btn;
    Button AC_BTN_Submit;
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_payment);

        image_btn=(ImageButton) findViewById(R.id.imgbtn);

        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent = new Intent(write_review.this, customer_reviews.class);
//                startActivity(intent);
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
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
                RegexTemplate.NOT_EMPTY,R.string.invalid_name2);
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

                    // get selected radio button from radioGroup
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    radioButton = (RadioButton) findViewById(selectedId);

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
        String CardImg = "";
        Toast.makeText(Add_New_Payment.this,
                radioButton.getText(), Toast.LENGTH_SHORT).show();
        Map<String, Object> map = new HashMap<>();
        Log.d("CardType", radioButton.getText().toString());
        if(radioButton.getText().toString().equalsIgnoreCase("Master card")){

            CardImg = "https://wallpapercave.com/wp/wp4750374.png";
            map.put("image","https://wallpapercave.com/wp/wp4750374.png");
            map.put("CardType", "Master Card");
        }

        else if(radioButton.getText().toString().equalsIgnoreCase("Visa") ){

            CardImg = "https://banner2.cleanpng.com/20180203/yve/kisspng-visa-credit-card-gift-card-payment-cheque-mastercard-cliparts-5a7671934cdd86.2689827715177117633149.jpg";
            map.put("image", "https://banner2.cleanpng.com/20180203/yve/kisspng-visa-credit-card-gift-card-payment-cheque-mastercard-cliparts-5a7671934cdd86.2689827715177117633149.jpg");
            map.put("CardType", "Visa");

        }




        map.put("name", AC_Card_Owner.getText().toString());
        map.put("number", (AC_Card_Number.getText().toString()));
        map.put("date", AC_Card_Date.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Card Details").child("Cus1").push()
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