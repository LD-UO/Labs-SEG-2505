package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewClient extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        //creating database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Client");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newclient);

        Button loginButton = (Button)findViewById(R.id.login2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fullnamenewclient = (EditText) findViewById(R.id.fullnamenewclient);
                EditText usernamenewclient = (EditText) findViewById(R.id.usernamenewclient);
                EditText passwordnewclient = (EditText) findViewById(R.id.passwordnewclient);
                EditText confirmpasswordnewclient = (EditText) findViewById(R.id.confirmpasswordnewclient);
                if (fullnamenewclient.getText().toString().trim().isEmpty() || usernamenewclient.getText().toString().trim().isEmpty() ||
                        passwordnewclient.getText().toString().trim().isEmpty() || confirmpasswordnewclient.getText().toString().trim().isEmpty()) {
                    Log.d("TAG","Username or Password cannot be empty");
                    //Toast.makeText(GeneralLogin.this, "Username or Password cannot be empty", Toast.LENGTH_LONG).show();
                } else{
                    // "The code below will need to be implemented correctly"
                    //Intent intent = new Intent(GeneralLogin.this, WelcomebackAdminActivity.class);
                    // Will need to change the text here to reflect database access!
                    //intent.putExtra("userType", "Undecided");
                    //startActivity(intent);
                }

            }
        });
    }
    protected void uploadData(){
        //Assumes chef attributes are correct

        Client newClient = new Client(databaseReference.push().getKey(),username,password);

        databaseReference.child(newClient.getId()).setValue(newClient);
    }
}