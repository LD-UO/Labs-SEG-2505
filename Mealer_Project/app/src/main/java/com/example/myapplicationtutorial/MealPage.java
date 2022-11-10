package com.example.myapplicationtutorial;

import android.os.Bundle;
import android.widget.ArrayAdapter;
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

                    Meal meal = new Meal(mealName, mealType, cuisineType, allergens, onMenu, price, chefUsername, description, ingredients);
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
}
