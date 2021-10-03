package com.example.mad_group_project;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import java.io.InputStream;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class sign_up_page extends AppCompatActivity {

    EditText firstname, lastname, input_email, postal_address, phone_number, password, et_confirm_password;
    Button button_signup;
    ImageButton Upload_image;
    CircleImageView profile_image;
//    Customer CusObj;
    DatabaseReference dbRef;
    //    FirebaseDatabase database;
    FirebaseAuth auth;
    CheckBox terms;
    FirebaseUser firebaseUser;
    Uri filepath;
    Bitmap bitmap;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        Toast.makeText(this, "FireBase Connection Success", Toast.LENGTH_SHORT).show();

        button_signup = findViewById(R.id.button_signup);
        Upload_image = findViewById(R.id.Upload_image);
        profile_image = findViewById(R.id.profile_image);


        Upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openFileChooser();
                Dexter.withActivity(sign_up_page.this)
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

        //Initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //add validation for first name
        awesomeValidation.addValidation(this,R.id.firstname, RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        //add validation for last name
        awesomeValidation.addValidation(this,R.id.lastname, RegexTemplate.NOT_EMPTY,R.string.invalidL_name);

        //add validation for email
        awesomeValidation.addValidation(this,R.id.input_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        //add validation for phone number
        awesomeValidation.addValidation(this,R.id.phone_number,"[0-9]{3}[0-9]{3}[0-9]{4}$",R.string.invali_mobile);

        //add validation for address
        awesomeValidation.addValidation(this,R.id.postal_address, RegexTemplate.NOT_EMPTY,R.string.invalid_address);

        //add validation for password
        awesomeValidation.addValidation(this,R.id.password,"^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",R.string.invalid_password);

        //add validation to check passwords are same
        awesomeValidation.addValidation(this,R.id.et_confirm_password, R.id.password ,R.string.invalid_Cpassword);




        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()){

                        uploadtofirebase();

                }else{

                    Toast.makeText(getApplicationContext(),
                    "Validation Failed", Toast.LENGTH_SHORT).show();
                }



            }
        });


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

    private void uploadtofirebase() {

        if(filepath == null){

            Toast.makeText(this, "Please Select a Profile Image", Toast.LENGTH_SHORT).show();

        }else {

               final ProgressDialog dialog = new ProgressDialog(this);
               dialog.setTitle("File Uploader");
               dialog.show();


               firstname = findViewById(R.id.firstname);
               lastname = findViewById(R.id.lastname);
               input_email = findViewById(R.id.input_email);
               postal_address = findViewById(R.id.postal_address);
               phone_number = findViewById(R.id.phone_number);
               password = findViewById(R.id.password);
               et_confirm_password = findViewById(R.id.et_confirm_password);
               terms = findViewById(R.id.terms);


//        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer");
               auth = FirebaseAuth.getInstance();

               FirebaseStorage storage = FirebaseStorage.getInstance();
               final StorageReference uploader = storage.getReference("Image1" + new Random().nextInt(50));

               uploader.putFile(filepath)
                       .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {

                                       dialog.dismiss();
                                       FirebaseDatabase db = FirebaseDatabase.getInstance();
                                       DatabaseReference dbref = db.getReference("Customer");
                                       String Email = input_email.getText().toString().trim();
                                       String Password = password.getText().toString().trim();
                                       auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                           @Override
                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                               if (task.isSuccessful()) {
                                                   Toast.makeText(sign_up_page.this, "User created", Toast.LENGTH_SHORT).show();
                                                   Customer obj = new Customer(firstname.getText().toString(), lastname.getText().toString(), input_email.getText().toString(), postal_address.getText().toString(), phone_number.getText().toString(), password.getText().toString(), uri.toString());
                                                   firebaseUser = auth.getCurrentUser();
                                                   dbref.child(firebaseUser.getUid()).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if (task.isSuccessful()) {
                                                               Toast.makeText(sign_up_page.this, "User added", Toast.LENGTH_SHORT).show();
                                                               String ConfirmPassword = et_confirm_password.getText().toString();

                                                               firstname.setText("");
                                                               lastname.setText("");
                                                               input_email.setText("");
                                                               postal_address.setText("");
                                                               phone_number.setText("");
                                                               password.setText("");
                                                               et_confirm_password.setText("");

                                                               profile_image.setImageResource(R.drawable.ic_launcher_background);
                                                               //Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
                                                           }
                                                       }
                                                   });
                                               }
                                           }
                                       });


                                   }
                               });
                           }
                       })
                       .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                               float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                               dialog.setMessage("Uploaded :" + (int) percent + " %");
                           }
                       });
           }


    }

    public void login(View view) {
        Intent intent = new Intent(sign_up_page.this,login.class);
        startActivity(intent);
    }


//    protected void onResume(){
//
//        super.onResume();
//        button_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(terms.isChecked()){
//
//                    String FirstName = firstname.getText().toString().trim();
//                    String LastName = lastname.getText().toString().trim();
//                    String Email = input_email.getText().toString().trim();
//                    String Address = postal_address.getText().toString().trim();
//
//                    /////// input only string value don't input integer values///////////////
//                    String PhoneNumber = phone_number.getText().toString().trim();
//
//                    /////////////////////////////////
//                    String Password = password.getText().toString().trim();
//
//                    //phone number
//                    String ConfirmPassword = et_confirm_password.getText().toString();
//                    auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(sign_up_page.this, "User Created", Toast.LENGTH_SHORT).show();
//                                firebaseUser = auth.getCurrentUser();
//
//
//                                CusObj = new Customer(FirstName,LastName,Email,Address,PhoneNumber,Password);
//
//                                dbRef.child(firebaseUser.getUid()).setValue(CusObj).addOnCompleteListener(new OnCompleteListener<Void>() {
//
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if(task.isSuccessful()){
//                                            Toast.makeText(sign_up_page.this, "Customer Added Successfully", Toast.LENGTH_SHORT).show();
//
//                                        }else{
//                                            Toast.makeText(sign_up_page.this, "Customer Added Failed", Toast.LENGTH_SHORT).show();
//                                            ClearControls(); }
//                                    }
//                                });
//                            }else {
//                                Toast.makeText(sign_up_page.this, "User Create failed", Toast.LENGTH_SHORT).show(); }
//                        }
//                    });
//
//                }else{
//
//                    Toast.makeText(sign_up_page.this, "You should agree with our terms and conditions", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });



}