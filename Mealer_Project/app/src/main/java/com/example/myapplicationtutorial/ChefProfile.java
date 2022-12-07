package com.example.myapplicationtutorial;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChefProfile extends AppCompatActivity {
    String chefUsername;
    Chef chef;
    DatabaseReference chef_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chef_profile);
        chef_reference = FirebaseDatabase.getInstance().getReference("Chef");
        chefUsername = getIntent().getStringExtra("username");
    }

    protected void onStart() {
        super.onStart();
        chef_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chefSnapshot : snapshot.getChildren()
                ) {
                    if (chefUsername.equals(chefSnapshot.child("username").getValue(String.class))) {
                        String description = chefSnapshot.child("description").getValue(String.class);
                        String fullname = chefSnapshot.child("fullname").getValue(String.class);
                        String id = chefSnapshot.child("id").getValue(String.class);
                        String numberOfRatings = chefSnapshot.child("numberOfRatings").getValue(String.class);
                        String password = chefSnapshot.child("password").getValue(String.class);
                        boolean suspended = chefSnapshot.child("suspended").getValue(Boolean.class);
                        String totalRating = chefSnapshot.child("totalRating").getValue(String.class);
                        String address = chefSnapshot.child("address").getValue(String.class);

                        chef = new Chef(id, chefUsername, password, fullname);
                        chef.setNumberOfRatings(numberOfRatings);
                        chef.setTotalRating(totalRating);
                        chef.setSuspended(suspended);
                        chef.setAddress(address);
                        chef.setDescription(description);

                        setTextViews();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setTextViews() {

        TextView username = findViewById(R.id.chefUsername);
        TextView password = findViewById(R.id.chefPassword);
        TextView suspended = findViewById(R.id.chefSuspended);
        TextView address = findViewById(R.id.chefAddress);
        TextView description = findViewById(R.id.chefDescription);
        TextView name = findViewById(R.id.chefName);
        TextView numberOfOrders = findViewById(R.id.chefNumberOfOrders);
        TextView rating = findViewById(R.id.chefRating);

        username.setText(username.getText() + chef.getUsername());
        password.setText(password.getText() + chef.getPassword());
        suspended.setText(suspended.getText() + chef.getSuspended().toString());
        address.setText(address.getText() + chef.getAddress());
        description.setText(description.getText() + chef.getDescription());
        name.setText(name.getText() + chef.getFullname());
        numberOfOrders.setText(numberOfOrders.getText() + chef.getNumberOfRatings());
        rating.setText(rating.getText() + (Math.round((Double.parseDouble(chef.getTotalRating()) / Double.parseDouble(chef.getNumberOfRatings()))) + ""));

    }
}
