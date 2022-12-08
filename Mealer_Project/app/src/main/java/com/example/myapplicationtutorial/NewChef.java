package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NewChef extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private DatabaseReference chefInfo;
    private DatabaseReference clientInfo;
    private DatabaseReference adminInfo;

    private String username;
    private String password;
    private String fullname;
    List<String> usernames;

    private ImageButton takePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //creating database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Chef");
        chefInfo = FirebaseDatabase.getInstance().getReference("Chef");
        clientInfo = FirebaseDatabase.getInstance().getReference("Client");
        adminInfo = FirebaseDatabase.getInstance().getReference("Admin");

        usernames = new ArrayList<String>();

        setContentView(R.layout.activity_newchef);
        takePhoto = (ImageButton) findViewById(R.id.takePhoto);
        Button loginButton = (Button)findViewById(R.id.login);
        onStart();
        TextView signininstead = (TextView) findViewById(R.id.textView15);
        signininstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goback = new Intent(NewChef.this,GeneralLogin.class);
                startActivity(goback);

            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fullnamenewchef = (EditText) findViewById(R.id.fullnamenewclient);
                EditText usernamenewchef = (EditText) findViewById(R.id.usernamenewclient);
                EditText passwordnewchef = (EditText) findViewById(R.id.passwordnewclient);
                EditText confirmpasswordnewchef = (EditText) findViewById(R.id.confirmpasswordnewclient);
                EditText description = (EditText)findViewById(R.id.chefDescription);
                EditText address = (EditText)findViewById(R.id.chefAddress);
                if (takePhoto.getDrawable()!=null||fullnamenewchef.getText().toString().trim().isEmpty() || usernamenewchef.getText().toString().trim().isEmpty() ||
                        passwordnewchef.getText().toString().trim().isEmpty() || confirmpasswordnewchef.getText().toString().trim().isEmpty()
                        ||description.getText().toString().trim().isEmpty()||address.getText().toString().trim().isEmpty()) {

                    Toast.makeText(NewChef.this, "Username or Password cannot be empty", Toast.LENGTH_LONG).show();
                } else if (passwordnewchef.getText().toString().equals(confirmpasswordnewchef.getText().toString()) == false){
                    Snackbar passwordsDontMatch = Snackbar.make(view, "Passwords do not match", Snackbar.LENGTH_LONG);
                    passwordsDontMatch.show();

                }else{
                    username = usernamenewchef.getText().toString().trim();
                    password = passwordnewchef.getText().toString().trim();
                    fullname = fullnamenewchef.getText().toString().trim();
                    boolean addData = true;
                    for (int i = 0; i < usernames.size(); i++){
                        if (username.equals(usernames.get(i))){
                            Snackbar usernameInUse = Snackbar.make(view, "Username is already in use", Snackbar.LENGTH_LONG);
                            usernameInUse.show();
                            // Make sure that the user does not get to sign up with a duplicate username
                            addData = false;
                        }
                    }

                    if (addData) {
                        uploadData(description.getText().toString(), address.getText().toString());
                        finish();
                    }

                }
            }
        });
    }
    protected void uploadData(String description, String address){
        //Assumes chef attributes are correct

        Chef newChef = new Chef(databaseReference.push().getKey(),username,password,fullname);
        newChef.setDescription(description);
        newChef.setAddress(address);
        databaseReference.child(newChef.getId()).setValue(newChef);
    }


    protected void onStart(){
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usernames.clear();
                chefInfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot chef) {

                        for (DataSnapshot chefShot : chef.getChildren()){
                            // Copies two of them for some reason
                            String test = chefShot.child("username").getValue().toString();
                            usernames.add(test);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                clientInfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot client) {
                        for (DataSnapshot clientShot : client.getChildren()){
                            // Copies two of them for some reason
                            String test = clientShot.child("username").getValue().toString();

                            usernames.add(test);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                adminInfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot admin) {
                        for (DataSnapshot adminShot : admin.getChildren()){
                            // Copies two of them for some reason
                            String test = adminShot.child("username").getValue().toString();
                            usernames.add(test);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    takePhoto.setImageURI(selectedImage);
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    takePhoto.setImageURI(selectedImage);
                }
                break;
        }
    }
}