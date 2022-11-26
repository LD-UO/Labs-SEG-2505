package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Welcomeback_client extends AppCompatActivity {
    ImageView log_off_btn;
    Button order;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_welcomeback);
        log_off_btn = (ImageView) findViewById(R.id.imageView4);
        order = (Button) findViewById(R.id.ordermeal);

        String name = intent.getStringExtra("user");

        TextView subheading = (TextView) findViewById(R.id.textView7);
        subheading.setText(name);


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderMeal = new Intent(Welcomeback_client.this,  OrderMeal.class);
                startActivity(orderMeal);
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









