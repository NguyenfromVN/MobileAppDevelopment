package com.ygaps.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.FeedBack;

import java.util.List;

public class CustomListReviewStopPointAdapter extends ArrayAdapter<FeedBack> {
    private Context mCtx;
    private int resource;
    private List<FeedBack> itemList;

    public CustomListReviewStopPointAdapter(Context mCtx, int resource, List<FeedBack> itemList){
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
        TextView feedBack=view.findViewById(R.id.txtRv);
        RatingBar ratingBar=view.findViewById(R.id.ratingBar);

        FeedBack item=itemList.get(position);

        String tmp;//temporary string variable

        textViewName.setText(item.getName());
        tmp=item.getFeedback();
        feedBack.setText(tmp);
        tmp=item.getPoint();
        int rate = Integer.parseInt(tmp);
        ratingBar.setRating(rate);


        return view;
    }
}
