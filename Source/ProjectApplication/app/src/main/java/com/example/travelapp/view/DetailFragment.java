package com.example.travelapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapp.R;

public class DetailFragment  extends Fragment {
    private static String TAG="Detail";
    private TextView name, contact, minCost, maxCost, service, address;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);


        name = (TextView)view.findViewById(R.id.txtName);
        contact = (TextView)view.findViewById(R.id.txtContact);
        minCost = (TextView)view.findViewById(R.id.minCost);
        maxCost = (TextView)view.findViewById(R.id.maxCost);
        service = (TextView)view.findViewById(R.id.service);
        address = (TextView)view.findViewById(R.id.txtAddress);

        name.setText(getArguments().getString("Name"));
        contact.setText(getArguments().getString("Contact"));
        address.setText(getArguments().getString("Address"));
        minCost.setText(getArguments().getInt("minCost")+"");
        maxCost.setText(getArguments().getInt("maxCost")+"");
        service.setText(getArguments().getString("Service"));





        return view;
    }
}
