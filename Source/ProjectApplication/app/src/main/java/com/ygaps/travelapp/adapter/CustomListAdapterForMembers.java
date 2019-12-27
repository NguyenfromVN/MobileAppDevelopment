package com.ygaps.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.FeedBack;
import com.ygaps.travelapp.model.Member;

import java.util.List;

public class CustomListAdapterForMembers extends ArrayAdapter<Member> {
    private Context mCtx;
    private int resource;
    private List<Member> itemList;

    public CustomListAdapterForMembers(Context mCtx, int resource, List<Member> itemList){
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

        TextView textViewName=view.findViewById(R.id.txtName);
        TextView phoneNumber=view.findViewById(R.id.txtRv);
        CheckBox host = view.findViewById(R.id.checkBoxIsPrivate);

        Member item=itemList.get(position);

        String tmp;//temporary string variable

        textViewName.setText(item.getName());
        tmp=item.getPhone();
        phoneNumber.setText(tmp);
        boolean is=item.isHost();
        host.setChecked(is);


        return view;
    }

}
