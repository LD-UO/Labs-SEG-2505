package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WelcomebackAdminActivity extends AppCompatActivity {

    ListView complaintlist;
    DatabaseReference complaint_reference;
    ArrayList<Complaint> complaints = new ArrayList<Complaint>();
    ArrayAdapter<Complaint> adapter;
    Button comp_button;
    Button log_off_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomeback_admin);


        comp_button = (Button) findViewById(R.id.button4);
        log_off_button = (Button) findViewById(R.id.button2);
        comp_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent com_page_intent = new Intent(WelcomebackAdminActivity.this,Complaints_page.class);
                startActivity(com_page_intent);
            }
        });

        log_off_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent comp_button_intent = new Intent(WelcomebackAdminActivity.this,GeneralLogin.class);
                startActivity(comp_button_intent);

            }
        });
    }

    }

