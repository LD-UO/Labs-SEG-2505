package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Welcomeback extends AppCompatActivity {
    ArrayList<Complaint> complaints = new ArrayList<Complaint>();
    ArrayAdapter<Complaint> adapter;
    ListView complaintlist;
    DatabaseReference complaint_reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeback2);
        Intent intent = getIntent();
        String userType = intent.getStringExtra("usertype");
        //TextView welcomeBackMessage = (TextView) findViewById(R.id.textView2);
        //TextView subheading = (TextView) findViewById(R.id.textView8);
        //subheading.setText("Welcome! You are signed in as a " + userType);



        complaintlist = (ListView) findViewById(R.id.complaint_list);
        complaint_reference = FirebaseDatabase.getInstance().getReference("Complaint");

        onStart();
    }
    protected void onStart(){
        super.onStart();
        complaint_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                for (DataSnapshot complaintSnapshot: snapshot.getChildren()){
                    boolean addressed = complaintSnapshot.child("addressed").getValue(Boolean.class);
                    String chefUsername = complaintSnapshot.child("chefUsername").getValue(String.class);
                    String clientUsername = complaintSnapshot.child("clientUsername").getValue(String.class);
                    String description = complaintSnapshot.child("description").getValue(String.class);
                    String endDate = complaintSnapshot.child("endDate").getValue(String.class);
                    Complaint complaint = new Complaint(description, chefUsername, clientUsername, endDate);
                    complaints.add(complaint);
                }
                //adapter = new ArrayAdapter<Complaint>(getApplicationContext(), android.R.layout.simple_list_item_1, complaints);
                //complaintlist.setAdapter(adapter);
                ComplaintList complaintAdapter = new ComplaintList(Welcomeback.this, complaints);
                complaintlist.setAdapter(complaintAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    public void logoff(){
        finish();
    }
}