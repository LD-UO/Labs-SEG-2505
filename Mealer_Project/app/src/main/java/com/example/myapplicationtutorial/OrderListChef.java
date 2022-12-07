package com.example.myapplicationtutorial;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrderListChef extends ArrayAdapter<Order> {

    private Activity context;
    private List<Order> orders;

    public OrderListChef(Activity context, List<Order> orders){

        super(context, R.layout.order_list_chef, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.order_list_chef, null, true);

        TextView name = (TextView) listViewItem.findViewById(R.id.ordersChef);
        Order order = orders.get(position);

        name.setText(order.getMeal().getName());

        return listViewItem;
    }
}
