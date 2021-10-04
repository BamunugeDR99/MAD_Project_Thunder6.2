package com.example.mad_group_project;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView username, name, email, number, address;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    CircleImageView profile_image;

    User acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.p_name);
        email = findViewById(R.id.p_email);
        number = findViewById(R.id.p_number);
        address = findViewById(R.id.p_address);
        username = findViewById(R.id.p_username);
        profile_image = findViewById(R.id.profile_image);

//        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("aAMe4dwdPdZsqgNTdWztSBaTD512");// id **
//        storageReference = FirebaseStorage.getInstance().getReference();

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("aAMe4dwdPdZsqgNTdWztSBaTD512");

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()){

                    username.setText(dataSnapshot.child("firstname").getValue().toString());
                    name.setText(dataSnapshot.child("lastname").getValue().toString());
                    address.setText(dataSnapshot.child("postal_address").getValue().toString());
                    number.setText(dataSnapshot.child("phone_number").getValue().toString());
                    email.setText(dataSnapshot.child("input_email").getValue().toString());
                    String link = dataSnapshot.child("profile_image").getValue(String.class);
                    Picasso.get().load(link).into(profile_image);

                }
                else{

                    Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


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

        navigationView.setCheckedItem(R.id.nav_profile);
    }

    public void edit(View view) {
        Intent intent = new Intent(user_profile.this, edit_profile.class);
               startActivity(intent);
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
                Intent intent = new Intent(user_profile.this, HomeUI.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
//                Intent intent1 = new Intent(user_profile.this, edit_profile.class);
//                startActivity(intent1);
                break;
            case R.id.nav_wishlist:
                Intent intent2 = new Intent(user_profile.this, WishList.class);
                startActivity(intent2);
                break;
            case R.id.nav_cart:
                Intent intent3 = new Intent(user_profile.this, myPurchases.class);
                startActivity(intent3);
                break;
            case R.id.nav_reviews:
                Intent intent4 = new Intent(user_profile.this, your_reviews.class);
                startActivity(intent4);
                break;
            case R.id.nav_contact:
                Intent intent5 = new Intent(user_profile.this, contactus.class);
                startActivity(intent5);
                break;
            case R.id.nav_logout:
                Intent intent6 = new Intent(user_profile.this, login.class);
                startActivity(intent6);
                break;
        }
        return true;
    }

}