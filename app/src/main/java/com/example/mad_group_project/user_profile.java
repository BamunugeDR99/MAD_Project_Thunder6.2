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

    EditText username,name, email,number, address;
    Uri filepath;
    ImageView img;

    Button btnUpdate, btnDelete ;
    ImageButton edit;
    Bitmap bitmap;

    DatabaseReference db;

    StorageReference storageReference;

    User acc;

    int TAKE_IMAGE_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.p_name);
        email=findViewById(R.id.p_email);
        number=findViewById(R.id.p_number);
        address=findViewById(R.id.p_address);
        username=findViewById(R.id.p_username);



        img=(ImageView)findViewById(R.id.p_image);
        edit=(ImageButton)findViewById(R.id.edit_img);

        btnUpdate=(Button) findViewById(R.id.btn_up);

        btnDelete=findViewById(R.id.btn_del);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dexter.withActivity(user_profile.this)
                                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        Intent intent = new Intent(Intent.ACTION_PICK);
                                        intent.setType("image/*");
                                        startActivityForResult(Intent.createChooser(intent, "Select Image File"),1);
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {

                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();
                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadtofirebase();
                    }
                });

//        acc = new User();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode == RESULT_OK){
            filepath=data.getData();
            try {
                InputStream inputStream= getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadtofirebase() {

        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();

        name=findViewById(R.id.p_name);
        email=findViewById(R.id.p_email);
        number=findViewById(R.id.p_number);
        address=findViewById(R.id.p_address);
        username=findViewById(R.id.p_username);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root =db.getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("Image1"+new Random().nextInt(50));

        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                dialog.dismiss();
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root= db.getReference("Account");

                                User obj = new User(name.getText().toString(),username.getText().toString(),email.getText().toString(),address.getText().toString(),number.getText().toString(),uri.toString());
                                root.child(name.getText().toString()).setValue(obj);

//                                name.setText("");
//                                email.setText("");
//                                number.setText("");
//                                address.setText("");
//                                username.setText("");
                                img.setImageResource(R.drawable.ic_launcher_background);
                                Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded :"+(int)percent+" %");
                    }
                });
    }

    //Method to clear all user Inputs
//    public void clearFields(){
//
//        name.setText("");
//        email.setText("");
//        number.setText("");
//        address.setText("");
//        username.setText("");
//
//    }
/////show/////


    public void Show(View view){

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Account").child("abc");

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
                storageReference = FirebaseStorage.getInstance().getReference().child("Image131.jpeg");

                try{
                    final File localFile = File.createTempFile("Image131","jpeg");
                    storageReference.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(user_profile.this, "Pictured Retrieved", Toast.LENGTH_SHORT).show();
                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    ((ImageView)findViewById(R.id.p_image)).setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(user_profile.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (IOException e){
                    e.printStackTrace();
                }


    }

    ///Update//////


//    public void Update(View view){
//
//        DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("Account");
//        upRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if(snapshot.hasChild("AC3")){
//                    try{
//                        acc.setName(name.getText().toString().trim());
//                        acc.setEmail(email.getText().toString().trim());
//                        acc.setAddress(address.getText().toString().trim());
//                        acc.setUsername(username.getText().toString().trim());
//                        acc.setNumber(Integer.parseInt(number.getText().toString().trim()));
//
//                        db = FirebaseDatabase.getInstance().getReference().child("Account").child("AC3");
//                        db.setValue(acc);
//                        clearFields();
//                        Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
//
//                    }catch(NumberFormatException e){
//                        Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull  DatabaseError error) {
//            }
//        });
//    }


    ////Delete/////


    public void Delete(View view){
        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Account");
        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                if (snapshot.hasChild(("AC5"))){
                    db = FirebaseDatabase.getInstance().getReference().child("Account").child("AC3");
                    db.removeValue();
//                    clearFields();
                    Toast.makeText(getApplicationContext(), "Data Deleted Successfully" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(user_profile.this, registration.class);
                        startActivity(intent);
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
/////select photo/////


//    public void handleImageClick(View view) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(intent.resolveActivity(getPackageManager())!=null){
//            startActivityForResult(intent, TAKE_IMAGE_CODE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==TAKE_IMAGE_CODE){
//            switch (requestCode){
//                case RESULT_OK:
//                    Bitmap bitmap =(Bitmap) data.getExtras().get("data");
//                    img.setImageBitmap(bitmap);
//                    handleUpload(bitmap);
//            }
//        }
//    }
//
//    private void handleUpload(Bitmap bitmap) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        StorageReference reference = FirebaseStorage.getInstance().getReference()
//                .child("profileImages")
//                .child(uid + ".jpeg");
//        reference.putBytes(byteArrayOutputStream.toByteArray())
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        getDownloadUrl(reference);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG, "onFailure: ", e.getCause());
//                    }
//                });
//    }
//    private void getDownloadUrl(StorageReference reference){
//        reference.getDownloadUrl()
//                .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Log.d(TAG, "onSuccess: " + uri);
//                        setUserProfile(uri);
//                    }
//                });
//    }
//    private void setUserProfile(Uri uri){
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
//                .setPhotoUri(uri)
//                .build();
//
//        user.updateProfile(request)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(user_profile.this, "", Toast.LENGTH_SHORT).show();
////                        Toast.makeText(ProfileActivity, "Profile image successful", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(user_profile.this, "Profile image failed", Toast.LENGTH_SHORT).show();
////                        Toast.makeText(ProfileActivity.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

}