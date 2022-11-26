package com.example.myapplicationtutorial;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderMeal extends AppCompatActivity {
    ListView mealList;
    DatabaseReference meal_reference;
    ArrayList<Meal> meals = new ArrayList<>();
    MenuList menuAdapter;
    SearchView searchBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        getSupportActionBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_meal_page);

        meal_reference = FirebaseDatabase.getInstance().getReference("Meal");

        mealList = (ListView) findViewById(R.id.results_list);

        // Calling the on start method to populate the elements in the list
        onStart();

        searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            // This method takes care of updating the adapter with what values to filter
            public boolean onQueryTextChange(String s) {
                menuAdapter.getFilter().filter(s);
                return false;
            }
        });
        // Need to add back functionality
    }

    protected void onStart(){
        super.onStart();
        meal_reference.addValueEventListener(new ValueEventListener() {
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
                    String id = mealSnapshot.child("id").getValue(String.class);
                    String mealUsername = mealSnapshot.child("chefUsername").getValue(String.class);
                    // Should only be visible to the client if the chef has it on their offered meals list
                    if(onMenu) {
                        Meal meal = new Meal(mealName, mealType, cuisineType, allergens, onMenu, price, chefUsername, description, ingredients, id);
                        meals.add(meal);
                    }
                }

                // This might need changing, but for the time being just using the same adapter as the one that chefs use to view their meals
                menuAdapter = new MenuList(OrderMeal.this, meals);
                mealList.setAdapter(menuAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
