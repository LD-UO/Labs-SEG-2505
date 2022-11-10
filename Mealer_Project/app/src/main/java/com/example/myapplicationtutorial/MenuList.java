package com.example.myapplicationtutorial;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MenuList extends ArrayAdapter<Meal>{

        private Activity context;
        List<Meal> meals;

        public MenuList(Activity context, List<Meal> meals){
            super(context, R.layout.menu_list, meals);
            this.context = context;
            this.meals = meals;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.menu_list, null, true);
//
            //TextView chefUsername = (TextView) listViewItem.findViewById(R.id.chefUsername);
            TextView name = (TextView) listViewItem.findViewById(R.id.meal_name);
            Meal meal = meals.get(position);
            //chefUsername.setText(meal.getChefUsername());
            name.setText(meal.getName());

            return listViewItem;
        }



    }


