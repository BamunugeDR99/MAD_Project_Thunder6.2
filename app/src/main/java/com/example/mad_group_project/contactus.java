package com.example.mad_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class contactus extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    EditText et_name, et_email, et_massage;
    Button btn_submit;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    Contact contact;

    DatabaseReference db;


    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_massage = findViewById(R.id.et_massage);
        btn_submit = findViewById(R.id.btn_submit);

        contact = new Contact();

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

//        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_logout).setVisible(false);

//        navigationView.bringChildToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_contact);


        //Initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //add validation for first name
        awesomeValidation.addValidation(this,R.id.et_name, RegexTemplate.NOT_EMPTY,R.string.invalid_contact_name);

        //add validation for last name
        awesomeValidation.addValidation(this,R.id.et_massage, RegexTemplate.NOT_EMPTY,R.string.invalid_mesaage);

        //add validation for email
        awesomeValidation.addValidation(this,R.id.et_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(contactus.this, HomeUI.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(contactus.this, user_profile.class);
                startActivity(intent1);
                break;
            case R.id.nav_wishlist:
                Intent intent2 = new Intent(contactus.this, WishList.class);
                startActivity(intent2);
                break;
            case R.id.nav_purchases:
                Intent intent3 = new Intent(contactus.this, myPurchases.class);
                startActivity(intent3);
                break;
            case R.id.nav_cart:
                Intent intent7 = new Intent(contactus.this, edit_cart.class);
                startActivity(intent7);
                break;
            case R.id.nav_reviews:
                Intent intent4 = new Intent(contactus.this, your_reviews.class);
                startActivity(intent4);
                break;
            case R.id.nav_contact:
//                Intent intent5 = new Intent(contactus.this, contactus.class);
//                startActivity(intent5);
                break;
            case R.id.nav_logout:
                Intent intent6 = new Intent(contactus.this, login.class);
                startActivity(intent6);
                break;
        }
        return true;
    }

}







