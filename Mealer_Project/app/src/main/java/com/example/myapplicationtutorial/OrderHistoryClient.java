package com.example.myapplicationtutorial;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    String clientUsername;
    ListView orderList;
    List<Order> orders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);
        clientUsername = getIntent().getStringExtra("username");
        order_reference = FirebaseDatabase.getInstance().getReference("Order");
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
                    if (username.equals(clientUsername)){
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

                        if (!status.equals("pending")){
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
                if (order.getStatus().equals("approved") && !order.isRated()){
                    // Call the method here to open the dialog box that will allow users to rate
                }
            }
        });
    }


}
