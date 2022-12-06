package com.example.myapplicationtutorial;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryClient extends AppCompatActivity {
    DatabaseReference order_reference;
    DatabaseReference chef_reference;
    String clientUsername;
    ListView orderList;
    List<Order> orders;
    String chefID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);
        clientUsername = getIntent().getStringExtra("username");
        order_reference = FirebaseDatabase.getInstance().getReference("Order");
        chef_reference = FirebaseDatabase.getInstance().getReference("Chef");
        orderList = (ListView) findViewById(R.id.orderHistoryList);
        orders = new ArrayList<Order>();
        onItemClick();
        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();

        order_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    String username = orderSnapshot.child("clientUsername").getValue(String.class);
                    if (username.equals(clientUsername)) {
                        //Log.d("Meal", orderSnapshot.child("meal").child("chefUsername").toString());

                        // These are the values we will be displaying in the listview
                        String chefUsername = orderSnapshot.child("meal").child("chefUsername").getValue(String.class);
                        String name = orderSnapshot.child("meal").child("name").getValue(String.class);
                        String cuisine = orderSnapshot.child("meal").child("cuisine").getValue(String.class);
                        String ingredients = orderSnapshot.child("meal").child("ingredients").getValue(String.class);
                        String type = orderSnapshot.child("meal").child("type").getValue(String.class);

                        // Some miscellaneous values
                        boolean onMenu = (boolean) orderSnapshot.child("meal").child("onMenu").getValue();
                        String description = orderSnapshot.child("meal").child("description").getValue(String.class);
                        String id = orderSnapshot.child("meal").child("id").getValue(String.class);
                        String allergens = orderSnapshot.child("meal").child("allergens").getValue(String.class);
                        String price = orderSnapshot.child("meal").child("price").getValue(String.class);
                        String status = orderSnapshot.child("status").getValue(String.class);

                        Meal meal = new Meal(name, type, cuisine, allergens, onMenu, price, chefUsername, description, ingredients, id);
                        Order order = new Order(username, meal, id);

                        chef_reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot chefSnapshot : snapshot.getChildren()) {
                                    String username = chefSnapshot.child("username").getValue(String.class);
                                    if (username.equals(chefUsername)) {
                                        chefID = chefSnapshot.child("id").getValue(String.class);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        if (!status.equals("pending")) {
                            order.setStatus(status);
                        }

                        orders.add(order);
                    }
                }

                OrderList orderAdapter = new OrderList(OrderHistoryClient.this, orders);
                orderList.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onItemClick() {
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
                Order order = orders.get(i);
                // Checking the conditions for rating
                if (order.getStatus().equals("approved") && !order.isRated()) {
                    // Call the method here to open the dialog box that will allow users to rate
                    showRate(order);
                }
            }
        });
    }

    private void showRate(Order order) {
        String[] ratings = {"1", "2", "3", "4", "5"};

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rate_chef_dialog, null);
        dialogBuilder.setView(dialogView);

        Button buttonConfirm = (Button) dialogView.findViewById(R.id.buttonConfirm);

        Spinner rating = (Spinner) dialogView.findViewById(R.id.spinnerRating);
        ArrayAdapter<String> adapterRatings = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ratings);
        adapterRatings.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rating.setAdapter(adapterRatings);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRating(Integer.parseInt(((TextView) rating.getSelectedView()).getText().toString()), order);
                b.dismiss();
            }
        });
    }

    private void updateRating(int rating, Order order) {
        String username = order.getChefUsername();
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Chef").child(chefID).child("totalRating");


        dR.addValueEventListener(new ValueEventListener() {
            int totalRating;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               totalRating = Integer.parseInt(dataSnapshot.getValue(String.class));
                Log.d("TEST", totalRating+"");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        order.setRated(true);

        //push order to firebase
    }
}
