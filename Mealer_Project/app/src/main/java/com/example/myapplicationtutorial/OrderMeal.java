package com.example.myapplicationtutorial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderMeal extends AppCompatActivity {
    ListView mealList;
    DatabaseReference meal_reference;
    DatabaseReference order_reference;
    DatabaseReference chef_reference;
    Chef ordersChef;
    ArrayList<Meal> meals = new ArrayList<>();
    MenuList menuAdapter;
    SearchView searchBar;

    // Need some way to check if the chef has been banned
    DatabaseReference complaintReference;
    List<String> bannedChefs;
    String clientUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_meal_page);
        getSupportActionBar().hide();

        clientUsername = getIntent().getStringExtra("username");

        // This will be used as a means of specifying search criteria
        String[] searchOptions = {"Name", "Type", "Cuisine"};
        Spinner options = (Spinner) findViewById(R.id.searchOptions);
        ArrayAdapter<String> adapterOptions = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, searchOptions);
        adapterOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapterOptions);
        bannedChefs = new ArrayList<String>();
        meal_reference = FirebaseDatabase.getInstance().getReference("Meal");
        order_reference = FirebaseDatabase.getInstance().getReference("Order");
        complaintReference = FirebaseDatabase.getInstance().getReference("Complaint");
        chef_reference = FirebaseDatabase.getInstance().getReference("Chef");
        mealList = (ListView) findViewById(R.id.results_list);

        onItemClick();

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
                List<Meal> filteredMeals = new ArrayList<Meal>();
                TextView searchOption = (TextView) options.getSelectedView();

                // Changing the list of meals that should be shown, should contain all the elements if the search bar is empty
                // Should dynamically update the list being shown because the method is called on text change
                if (searchOption.getText().toString().equals("Name")) {
                    // Name
                    for (Meal meal : meals) {
                        if (meal.getName().toLowerCase().contains(s.toLowerCase())) {
                            filteredMeals.add(meal);
                        }
                    }
                    // Meal type
                } else if (searchOption.getText().toString().equals("Type")) {
                    for (Meal meal : meals) {
                        if (meal.getType().toLowerCase().contains(s.toLowerCase())) {
                            filteredMeals.add(meal);
                        }
                    }
                } else {
                    // Cuisine type
                    for (Meal meal : meals) {
                        if (meal.getCuisine().toLowerCase().contains(s.toLowerCase())) {
                            filteredMeals.add(meal);
                        }
                    }
                }

                // Updating the menu adapter and setting the listview adapter to be the new one
                menuAdapter = new MenuList(OrderMeal.this, filteredMeals);
                mealList.setAdapter(menuAdapter);
                return false;
            }
        });
        // Need to add back functionality
    }

    protected void onStart() {
        super.onStart();

        // This is to check if any of the chefs have been banned
        complaintReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot complaintSnapshot : snapshot.getChildren()) {
                    String username = complaintSnapshot.child("chefUsername").getValue().toString();
                    String endDate = complaintSnapshot.child("endDate").getValue().toString();

                    // Need to check here if chef chef has been banned or not
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
                    try {
                        Date d1 = sdf.parse(endDate);
                        Date d2 = sdf.parse(sdf.format(calendar.getTime()));
                        if (d1.after(d2)) {
                            // This means that the chef is still banned, and thus no meals from the chef should show up in the search
                            bannedChefs.add(username);
                        }
                    } catch (Exception e) {
                        // This means they are banned indefinitely, and thus no meals from the chef must show up in the search results
                        bannedChefs.add(username);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

                    // Should only add the meal to the search results if the meal is offered and the chef is not currently banned
                    if (onMenu) {
                        Meal meal = new Meal(mealName, mealType, cuisineType, allergens, onMenu, price, chefUsername, description, ingredients, id);
                        meals.add(meal);
                    }
                    chef_reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot chefSnapshot : snapshot.getChildren()) {
                                String username = chefSnapshot.child("username").getValue(String.class);
                                if (username.equals(chefUsername)) {
                                    String chefID = chefSnapshot.child("id").getValue(String.class);
                                    String orderedChefUsername = chefSnapshot.child("username").getValue(String.class);
                                    String orderedChefPassword = chefSnapshot.child("password").getValue(String.class);
                                    String orderedChefFullName = chefSnapshot.child("fullname").getValue(String.class);
                                    String totalNumberRatings = chefSnapshot.child("numberOfRatings").getValue(String.class);
                                    String orderChefDesc = chefSnapshot.child("description").getValue(String.class);
                                    String totalRatings = chefSnapshot.child("totalRating").getValue(String.class);

                                    ordersChef = new Chef(chefID, orderedChefUsername, orderedChefPassword, orderedChefFullName);
                                    ordersChef.setTotalRating(totalRatings);
                                    ordersChef.setNumberOfRatings(totalNumberRatings);
                                    ordersChef.setDescription(orderChefDesc);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // Will need to change this adapter, just using it for testing purposes
                    menuAdapter = new MenuList(OrderMeal.this, meals);
                    mealList.setAdapter(menuAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onItemClick() {
        mealList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
                Meal meal = meals.get(i);
                showMealItem(meal);
            }
        });
    }

    private void showMealItem(Meal meal) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.view_meal_item, null);
        dialogBuilder.setView(dialogView);

        TextView mealName = dialogView.findViewById(R.id.name);
        TextView ingredients = dialogView.findViewById(R.id.ingredients);
        TextView allergens = dialogView.findViewById(R.id.allergens);
        TextView type = dialogView.findViewById(R.id.type);
        TextView price = dialogView.findViewById(R.id.price);
        TextView cuisine = dialogView.findViewById(R.id.cuisine);
        TextView chefName = dialogView.findViewById(R.id.chefName);
        TextView rating = dialogView.findViewById(R.id.rating);
        TextView chefDescription = dialogView.findViewById(R.id.chefDescription);
        TextView mealDescription = dialogView.findViewById(R.id.mealdescription);

        mealName.setText(mealName.getText() + meal.getName());
        ingredients.setText(ingredients.getText() + meal.getIngredients());
        allergens.setText(allergens.getText() + meal.getAllergens());
        type.setText(type.getText() + meal.getType());
        price.setText(price.getText() + meal.getPrice());
        cuisine.setText(cuisine.getText() + meal.getCuisine());
        chefName.setText(chefName.getText() + ordersChef.getFullname());
        rating.setText(rating.getText() + (Math.round((Double.parseDouble(ordersChef.getTotalRating()) / Double.parseDouble(ordersChef.getNumberOfRatings()))) + ""));
        chefDescription.setText(chefDescription.getText() + ordersChef.getDescription());
        mealDescription.setText(mealDescription.getText() + meal.getDescription());

        final Button order = (Button) dialogView.findViewById(R.id.order);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = order_reference.push().getKey();
                Order order = new Order(clientUsername, meal, id);
                order_reference.child(order.getId()).setValue(order);

                b.dismiss();
                Toast.makeText(getApplicationContext(), "Meal Ordered", Toast.LENGTH_LONG).show();
            }
        });
    }

}
