package com.example.mad_group_project;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class edit_profile extends AppCompatActivity {

    EditText edit_fn, edit_ln, edit_email,  edit_add,  edit_phone, current_password, new_password,confirm_password;
    Button update_btn, delete_btn;
    ImageButton Upload_image;
    DatabaseReference dbRef, dbRef2;
    StorageReference storageReference;

    //    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String  currentPassword;
    String CustomerID;
    CircleImageView profile_image;
    Uri filepath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        edit_fn = findViewById(R.id.edit_fn);
        edit_ln = findViewById(R.id.edit_ln);
        edit_email= findViewById(R.id.edit_email);
        edit_add = findViewById(R.id.edit_add);
        edit_phone = findViewById(R.id.edit_phone);
        current_password = findViewById(R.id.current_password);
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        profile_image = findViewById(R.id.profile_image);
        update_btn = findViewById(R.id.update_btn);
        delete_btn = findViewById(R.id.deletePbtn);
        Upload_image = findViewById(R.id.Upload_image);


        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("YATaDgnUNUhCCMzAXWXuBz3RcVJ3");// id **
        storageReference = FirebaseStorage.getInstance().getReference();

        Upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openFileChooser();
                Dexter.withActivity(edit_profile.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
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


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){

                    edit_fn.setText(dataSnapshot.child("firstName").getValue().toString());
                    edit_ln.setText(dataSnapshot.child("lastName").getValue().toString());
                    edit_email.setText(dataSnapshot.child("email").getValue().toString());
                    edit_phone.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                    edit_add.setText(dataSnapshot.child("address").getValue().toString());
                    currentPassword = dataSnapshot.child("password").getValue().toString();
                    String link = dataSnapshot.child("pimage").getValue(String.class);
                    Picasso.get().load(link).into(profile_image);

                }else{

                    Toast.makeText(edit_profile.this, "No Source to display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("d", String.valueOf(error));
            }
        });

        //btnUpdate


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!(current_password.getText().toString().isEmpty())){
                    String enteredPassword = current_password.getText().toString();
                    if(enteredPassword.equals(currentPassword)){

                        if(new_password.getText().toString().equals(confirm_password.getText().toString())){
                            HashMap customer = new HashMap();
                            customer.put("password",new_password.getText().toString().trim());
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("YATaDgnUNUhCCMzAXWXuBz3RcVJ3");

                            dbRef.updateChildren(customer).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(edit_profile.this, "Password Changed Success", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(edit_profile.this, "Password Changed Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else
                        {
                            Toast.makeText(edit_profile.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(edit_profile.this, "Invalid Current Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    updatetofirebase();
                }
            }
        });



//        update_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!(current_password.getText().toString().isEmpty())){
//                    String enteredPassword = current_password.getText().toString();
//                    if(enteredPassword.equals(currentPassword)){
//
//                        if(new_password.getText().toString().equals(confirm_password.getText().toString())){
//                            HashMap customer = new HashMap();
//                            customer.put("password",new_password.getText().toString().trim());
//                            dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child(CustomerID);
//
//                            dbRef.updateChildren(customer).addOnCompleteListener(new OnCompleteListener() {
//                                @Override
//                                public void onComplete(@NonNull Task task) {
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(edit_profile.this, "Password Changed Success", Toast.LENGTH_SHORT).show();
//                                    }else{
//                                        Toast.makeText(edit_profile.this, "Password Changed Failed", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }else
//                        {
//                            Toast.makeText(edit_profile.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//
//                        Toast.makeText(edit_profile.this, "Invalid Current Password", Toast.LENGTH_SHORT).show();
//                    }
//
//                }else{
//
//                    String firstName = edit_fn.getText().toString().trim();
//                    String lastName  = edit_ln.getText().toString().trim();
//                    String email     = edit_email.getText().toString().trim();
//                    String address   = edit_add.getText().toString().trim();
//                    String phoneNumber =edit_phone.getText().toString().trim();
//
//                    UpdateUserProfile(firstName, lastName, email, address, phoneNumber);
//                }
//
//            }

//
//        });
//
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1  && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                profile_image.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void updatetofirebase()
    {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("File Uploader");
        pd.show();

        final StorageReference uploader=storageReference.child("Image1" +System.currentTimeMillis());
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Map<String,Object> map = new HashMap<>();

                                map.put("pimage",uri.toString());
                                map.put("firstName", edit_fn.getText().toString());
                                map.put("lastName", edit_ln.getText().toString());
                                map.put("email", edit_email.getText().toString());
                                map.put("phoneNumber", edit_phone.getText().toString());
                                map.put("address", edit_add.getText().toString());
//                                currentPassword = dataSnapshot.child("password").getValue().toString();
                                dbRef2 = FirebaseDatabase.getInstance().getReference();
                                dbRef2.child("Customer").child("YATaDgnUNUhCCMzAXWXuBz3RcVJ3").updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(edit_profile.this, "Updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                                pd.dismiss();
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded :"+(int)percent+"%");
                    }
                });

    }



    //Update Profile

//    private void UpdateUserProfile(String FirstName, String LastName, String Email, String Address, String PhoneNumber) {
//
//        HashMap customer = new HashMap();
//        customer.put("firstName", FirstName);
//        customer.put("lastName", LastName);
//        customer.put("email", Email);
//        customer.put("address", Address);
//        customer.put("phoneNumber", PhoneNumber);
//
//        //** change password? ->
//
//        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child(CustomerID); //*userID?
//        dbRef.updateChildren(customer).addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                if(task.isSuccessful()){
//
//                    Toast.makeText(edit_profile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
//
//                }else{
//
//                    Toast.makeText(edit_profile.this, "Profile Update Failed", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
//
//    }

    public void DeleteUserprofile(View view){

        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Sheheni")){ // ** userId

                    dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("Sheheni"); // ** userId
                    dbRef.removeValue();
                    Toast.makeText(getApplicationContext(), "Profile deleted Successfully", Toast.LENGTH_SHORT).show();
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