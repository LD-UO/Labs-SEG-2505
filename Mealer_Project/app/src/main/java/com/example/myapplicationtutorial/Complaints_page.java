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

public class Complaints_page extends AppCompatActivity {

    ListView complaintlist;
    DatabaseReference complaint_reference;
    ArrayList<Complaint> complaints = new ArrayList<Complaint>();
    ArrayAdapter<Complaint> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_page);

        complaint_reference = FirebaseDatabase.getInstance().getReference("Complaint");

        complaintlist = (ListView) findViewById(R.id.complaint_list);
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
                ComplaintList complaintAdapter = new ComplaintList(Complaints_page.this, complaints);
                complaintlist.setAdapter(complaintAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}