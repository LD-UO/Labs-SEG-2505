package com.example.myapplicationtutorial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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


import org.w3c.dom.Text;

import java.util.ArrayList;

public class MealPage extends AppCompatActivity {
    ListView menulist;
    DatabaseReference menu_reference;
    ArrayList<Meal> meals = new ArrayList<>();
    ArrayAdapter<Meal> adapter;
    ImageView back;
    String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupage);

        menu_reference = FirebaseDatabase.getInstance().getReference("Meal");

        menulist = (ListView) findViewById(R.id.menu_list);
        final ImageView logoff = (ImageView) findViewById(R.id.logoffchef);
        final Button buttonAdd = (Button) findViewById(R.id.add_item);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        Log.d("usernameOnCreate", username);
        onItemClick();

        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddMealDialog();
            }
        });

    }

    protected void onStart() {
        super.onStart();
        menu_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meals.clear();
                for (DataSnapshot mealSnapshot : snapshot.getChildren()) {
                    String allergens = mealSnapshot.child("allergens").getValue(String.class);
                    String cuisineType = mealSnapshot.child("cuisine").getValue(String.class);
                    String description = mealSnapshot.child("description").getValue(String.class);
                    String ingredients = mealSnapshot.child("ingredients").getValue(String.class);
                    String mealName = mealSnapshot.child("name").getValue(String.class);
                    String mealType = mealSnapshot.child("type").getValue(String.class);
                    boolean onMenu = (boolean) mealSnapshot.child("onMenu").getValue();
                    String price = mealSnapshot.child("price").getValue(String.class);
                    String chefUsername = mealSnapshot.child("chefUsername").getValue(String.class);

                    Meal meal = new Meal(mealName, mealType, cuisineType, allergens, onMenu, price, chefUsername, description, ingredients, "tmp id");
                    meals.add(meal);
                    // We need some code in the dialog box if it's on the menu
                    MenuList menuAdapter = new MenuList(MealPage.this, meals);
                    menulist.setAdapter(menuAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onItemClick() {
        menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
                Meal meal = meals.get(i);
                showViewOrDelete(meal.getOnMenu(), meal.getType(), meal.getName(), meal.getCuisine(), meal.getAllergens(), meal.getIngredients(), meal.getPrice(), meal.getDescription(), meal.getId());
            }
        });
    }

    private  void showAddMealDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_to_menu_dialog, null);
        dialogBuilder.setView(dialogView);

        Button buttonConfirm = (Button) dialogView.findViewById(R.id.buttonConfirm);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mealType = (EditText) dialogView.findViewById(R.id.type);
                EditText mealName = (EditText) dialogView.findViewById(R.id.name);
                EditText cuisineType = (EditText) dialogView.findViewById(R.id.cuisine);
                EditText allergensText = (EditText) dialogView.findViewById(R.id.allergens);
                EditText ingredientsText = (EditText) dialogView.findViewById(R.id.ingredients);
                EditText priceText = (EditText) dialogView.findViewById(R.id.price);
                EditText descriptionText = (EditText) dialogView.findViewById(R.id.mealdescription);

                addMeal(mealName.getText().toString(),mealType.getText().toString(),cuisineType.getText().toString(),allergensText.getText().toString(),priceText.getText().toString(),username,descriptionText.getText().toString(),ingredientsText.getText().toString());
            }
        });

        final AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void showViewOrDelete(Boolean onMenu, String type, String name, String cuisine, String allergens, String ingredients, String price, String description, String id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.view_menu_dialog, null);
        dialogBuilder.setView(dialogView);

        // Getting the textviews
        final TextView mealType = (TextView) dialogView.findViewById(R.id.type);
        final TextView mealName = (TextView) dialogView.findViewById(R.id.name);
        final TextView cuisineType = (TextView) dialogView.findViewById(R.id.cuisine);
        final TextView allergensText = (TextView) dialogView.findViewById(R.id.allergens);
        final TextView ingredientsText = (TextView) dialogView.findViewById(R.id.ingredients);
        final TextView priceText = (TextView) dialogView.findViewById(R.id.price);
        final TextView descriptionText = (TextView) dialogView.findViewById(R.id.mealdescription);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        // Setting the text in the text views
        // Descriptor is not working for the time being, I'll worry about it later
        mealType.setText(mealType.getText().toString() + type);
        mealName.setText(mealName.getText().toString() + name);
        cuisineType.setText(cuisineType.getText().toString() + cuisine);
        allergensText.setText(allergensText.getText().toString() + allergens);
        ingredientsText.setText(ingredientsText.getText().toString() + ingredients);
        priceText.setText(priceText.getText().toString() + price);
        descriptionText.setText(descriptionText.getText().toString() + description);

        final AlertDialog b = dialogBuilder.create();
        b.show();
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!onMenu) {
                    if(deleteMeal(id)){
                        Toast.makeText(getApplicationContext(), "Meal Deleted", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error Could Not Delete", Toast.LENGTH_LONG).show();
                    }
                    b.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(), "Meal is on special menu", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    private boolean addMeal(String name,String type,String cuisine, String allergens, String price, String chefUsername, String description, String ingredients) {

        Meal meal = new Meal( name, type, cuisine,  allergens,  false,  price,  chefUsername,  description, ingredients, menu_reference.push().getKey());
        menu_reference.child(meal.getId()).setValue(meal);

        return true;
    }

    private boolean deleteMeal(String id){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Meal").child(id);
        databaseReference.removeValue();
        return true;
    }
}


