package com.example.myapplicationtutorial;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class ComplaintList extends ArrayAdapter<Complaint> {
    private Activity context;
    List<Complaint> complaints;

    public ComplaintList(Activity context, List<Complaint> complaints){
        super(context, R.layout.complaint_list, complaints);
        this.context = context;
        this.complaints = complaints;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.complaint_list, null, true);

        TextView chefUsername = (TextView) listViewItem.findViewById(R.id.chefUsername);
        TextView clientUsername = (TextView) listViewItem.findViewById(R.id.clientUsername);
        TextView description = (TextView) listViewItem.findViewById(R.id.description);
        Complaint complaint = complaints.get(position);
        chefUsername.setText(complaint.getChefUsername());
        clientUsername.setText(clientUsername.getText().toString() + complaint.getClientUsername());
        description.setText(complaint.getDescription());

        return listViewItem;
    }


}
