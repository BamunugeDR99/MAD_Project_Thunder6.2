package com.example.mad_group_project;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_profile extends AppCompatActivity {

    TextView username, name, email, number, address;
    Uri filepath;
    ImageView img;

    Button btnUpdate, btnDelete;
    ImageButton edit;
    Bitmap bitmap;

    DatabaseReference dbRef;

    StorageReference storageReference;

    User acc;

    int TAKE_IMAGE_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.p_name);
        email = findViewById(R.id.p_email);
        number = findViewById(R.id.p_number);
        address = findViewById(R.id.p_address);
        username = findViewById(R.id.p_username);
//        username = findViewById(R.id.edit_fn);
//        edit_ln = findViewById(R.id.edit_ln);
//        edit_email= findViewById(R.id.edit_email);
//        edit_add = findViewById(R.id.edit_add);
//        edit_phone = findViewById(R.id.edit_phone);
//        current_password = findViewById(R.id.current_password);
//        new_password = findViewById(R.id.new_password);
//        confirm_password = findViewById(R.id.confirm_password);
//        profile_image = findViewById(R.id.profile_image);
//        update_btn = findViewById(R.id.update_btn);
//        delete_btn = findViewById(R.id.deletePbtn);
//        Upload_image = findViewById(R.id.Upload_image);

//        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("aAMe4dwdPdZsqgNTdWztSBaTD512");// id **
//        storageReference = FirebaseStorage.getInstance().getReference();
    }
}