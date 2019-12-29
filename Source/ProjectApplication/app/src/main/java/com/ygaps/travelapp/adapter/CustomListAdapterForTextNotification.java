package com.ygaps.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.TextNotification;

import java.util.List;

public class CustomListAdapterForTextNotification extends ArrayAdapter<TextNotification> {

    private Context mCtx;
    private int resource;
    private List<TextNotification> itemList;

    public CustomListAdapterForTextNotification(Context mCtx, int resource, List<TextNotification> itemList){
        super(mCtx,resource,itemList);
        this.mCtx=mCtx;
        this.resource=resource;
        this.itemList=itemList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(resource, null);

        ((TextView)view.findViewById(R.id.textViewName)).setText(itemList.get(position).getName());
        ((TextView)view.findViewById(R.id.textViewText)).setText(itemList.get(position).getNotification());

        return view;
    }
}
