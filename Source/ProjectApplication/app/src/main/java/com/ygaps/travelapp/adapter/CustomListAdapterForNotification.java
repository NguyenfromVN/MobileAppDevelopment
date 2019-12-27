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
import com.ygaps.travelapp.model.NotificationItem;
import com.ygaps.travelapp.view.NotificationTab;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CustomListAdapterForNotification extends ArrayAdapter<NotificationItem> {

    private Context mCtx;
    private int resource;
    private List<NotificationItem> itemList;

    public CustomListAdapterForNotification(Context mCtx, int resource, List<NotificationItem> itemList){
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

        NotificationItem item=itemList.get(position);
        String tmp;//temporary string variable

        tmp=item.getHostName()+" sent you an invitation for tour "+item.getName();
        ((TextView)view.findViewById(R.id.textViewContent)).setText(tmp);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ((TextView)view.findViewById(R.id.textViewDate)).setText(df.format(item.getCreatedOn()));

        //handle for confirming invitation
        view.findViewById(R.id.buttonConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationTab.confirm(position,true);
            }
        });

        //handle for deleting invitation
        view.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationTab.confirm(position,false);
            }
        });

        return view;
    }
}
