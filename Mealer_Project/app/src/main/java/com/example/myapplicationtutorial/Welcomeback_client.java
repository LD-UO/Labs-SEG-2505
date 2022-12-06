package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcomeback_client extends AppCompatActivity {
    ImageView log_off_btn;
    Button order;
    Button viewOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_welcomeback);
        log_off_btn = (ImageView) findViewById(R.id.imageView4);
        order = (Button) findViewById(R.id.ordermeal);
        viewOrder = (Button) findViewById(R.id.orderhistory);

        String name = intent.getStringExtra("username");

        TextView subheading = (TextView) findViewById(R.id.textView7);
        subheading.setText(name);


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderMeal = new Intent(Welcomeback_client.this,  OrderMeal.class);
                orderMeal.putExtra("username",name);
                startActivity(orderMeal);
            }
        });

        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderHistory = new Intent(Welcomeback_client.this, OrderHistoryClient.class);
                orderHistory.putExtra("username", name);
                startActivity(orderHistory);
            }
        });
        log_off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_back = new Intent(Welcomeback_client.this,GeneralLogin.class);
                startActivity(go_back);

            }
        });

    }
}









