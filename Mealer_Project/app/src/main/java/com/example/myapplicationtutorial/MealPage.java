package com.example.myapplicationtutorial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

public class MealPage extends AppCompatActivity {
    ListView menulist;
    DatabaseReference menu_reference;
    ArrayList<Meal> meals = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupage);

        menu_reference = FirebaseDatabase.getInstance().getReference("Meal");

        menulist = (ListView) findViewById(R.id.menu_list);
        ImageView logoff = (ImageView) findViewById(R.id.logoffchef);

        onItemClick();

        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void onStart(){
        super.onStart();
        menu_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meals.clear();
                for (DataSnapshot mealSnapshot : snapshot.getChildren()){
                    String allergens = mealSnapshot.child("Allergens").getValue(String.class);
                    String cuisineType = mealSnapshot.child("CuisineType").getValue(String.class);
                    String description = mealSnapshot.child("Description").getValue(String.class);
                    String ingredients = mealSnapshot.child("Ingredients").getValue(String.class);
                    String mealName = mealSnapshot.child("MealName").getValue(String.class);
                    String mealType = mealSnapshot.child("MealType").getValue(String.class);
                    boolean onMenu = (Boolean) mealSnapshot.child("OnMenu").getValue();
                    String price = mealSnapshot.child("Price").getValue(String.class);
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

    private void onItemClick(){
        menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int i, long l){
                Meal meal = meals.get(i);
                showViewOrDelete(meal.getType(), meal.getName(), meal.getCuisine(), meal.getAllergens(), meal.getIngredients(), meal.getPrice(), meal.getDescription());
            }
        });
    }

    private void showViewOrDelete(String type, String name, String cuisine, String allergens, String ingredients, String price, String description){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_menu_dialog, null);
        dialogBuilder.setView(dialogView);

        // Getting the textviews
        final TextView mealType = (TextView) dialogView.findViewById(R.id.type);
        final TextView mealName = (TextView) dialogView.findViewById(R.id.name);
        final TextView cuisineType = (TextView) dialogView.findViewById(R.id.cuisine);
        final TextView allergensText = (TextView) dialogView.findViewById(R.id.allergens);
        final TextView ingredientsText = (TextView) dialogView.findViewById(R.id.ingredients);
        final TextView priceText = (TextView) dialogView.findViewById(R.id.price);
        final TextView descriptionText = (TextView) dialogView.findViewById(R.id.mealdescription);

        // Setting the text in the text views
        mealType.setText(mealType.getText().toString() + type);
        mealName.setText(mealName.getText().toString() + name);
        cuisineType.setText(cuisineType.getText().toString() + cuisine);
        allergensText.setText(allergensText.getText().toString() + allergens);
        ingredientsText.setText(ingredientsText.getText().toString() + ingredients);
        priceText.setText(priceText.getText().toString() + price);
        descriptionText.setText(descriptionText.getText().toString() + description);

        final AlertDialog b = dialogBuilder.create();
        b.show();
    }



}