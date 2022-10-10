package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GeneralLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_login);


        Button loginButton = (Button)findViewById(R.id.login3);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneralLogin.this, Welcomeback.class);
                // Will need to change the text here to reflect database access!
                intent.putExtra("userType", "Undecided");
                startActivity(intent);
            }
        });
    }
}