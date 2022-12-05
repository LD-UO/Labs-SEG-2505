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

            //TextView chefUsername = (TextView) listViewItem.findViewById(R.id.chefUsername);
            TextView name = (TextView) listViewItem.findViewById(R.id.meal_name);
            ImageView start = (ImageView) listViewItem.findViewById(R.id.imageView15);
            TextView offered = (TextView) listViewItem.findViewById(R.id.isOfferedText);
            Meal meal = meals.get(position);
            meal.getCuisine();
            //chefUsername.setText(meal.getChefUsername());
            name.setText(meal.getName());
           if (meal.getOnMenu() == true){
              start.setImageResource(R.drawable.star_icon);
           }
           else{
               if(meal.getOnMenu()== false){
                   start.setImageResource(R.drawable.x_symbol);
               }
           }
            offered.setText("");

            return listViewItem;
        }



    }


