package com.example.myapplicationtutorial;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class OrderHistoryChef extends AppCompatActivity {
    DatabaseReference order_reference;
    String chefUsername;
    ListView orderList;
    List<Order> orders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);
        chefUsername = getIntent().getStringExtra("username");
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
                    String username = orderSnapshot.child("chefUsername").getValue(String.class);
                    if (username.equals(chefUsername)){
                        // These are the values we will be displaying in the listview
                        String clientUsername = orderSnapshot.child("clientUsername").getValue(String.class);
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
                        Order order = new Order(clientUsername, meal, orderSnapshot.child("id").getValue(String.class));
                        if (!status.equals("pending")) {
                            order.setStatus(status);
                        }

                        // It should only add to the list of orders if the order has not been accepted/denied by the chef
                        if (order.getStatus().equals("pending")) {
                            orders.add(order);
                        }
                    }
                }

                OrderListChef orderAdapter = new OrderListChef(OrderHistoryChef.this, orders);
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
                // Need to do something to accept or deny each order
                Order order = orders.get(i);
                approveOrDeleteOrder(order);

            }
        });
    }

    private void approveOrDeleteOrder(Order order) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.approve_or_delete_order, null);
        dialogBuilder.setView(dialogView);

        Button approveButton = (Button) dialogView.findViewById(R.id.approve_button);
        Button denyButton = (Button) dialogView.findViewById(R.id.deny_button);
        TextView mealName = (TextView) dialogView.findViewById(R.id.approve_or_deny_name);
        TextView clientName = (TextView) dialogView.findViewById(R.id.approve_or_deny_client_name);

        mealName.setText(mealName.getText().toString() + order.getMeal().getName());
        clientName.setText(clientName.getText().toString() + order.getClientUsername());
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Order").child(order.getId());

        final AlertDialog b = dialogBuilder.create();
        b.show();

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // It should update the order's status in the database to be accepted
                Order updateOrder = new Order(order.getClientUsername(), order.getMeal(), order.getId());
                updateOrder.setStatus("approved");
                dR.setValue(updateOrder);
                b.dismiss();
            }
        });

        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // It should update the order's status in the database to be denied
                Order updateOrder = new Order(order.getClientUsername(), order.getMeal(), order.getId());
                updateOrder.setStatus("cancelled");
                dR.setValue(updateOrder);
                b.dismiss();
            }
        });

        onStart();
    }



}
