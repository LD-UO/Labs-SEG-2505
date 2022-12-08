package com.example.myapplicationtutorial;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import java.util.List;

public class MenuListAlt extends ArrayAdapter<Meal>{

    private Activity context;
    List<Meal> meals;

    public MenuListAlt(Activity context, List<Meal> meals){
        super(context, R.layout.menu_list_alt, meals);
        this.context = context;
        this.meals = meals;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.menu_list_alt, null, true);

        //TextView chefUsername = (TextView) listViewItem.findViewById(R.id.chefUsername);
        TextView name = (TextView) listViewItem.findViewById(R.id.meal_name2);

        Meal meal = meals.get(position);
        meal.getCuisine();
        //chefUsername.setText(meal.getChefUsername());
        name.setText(meal.getName());
        return listViewItem;
    }



}


