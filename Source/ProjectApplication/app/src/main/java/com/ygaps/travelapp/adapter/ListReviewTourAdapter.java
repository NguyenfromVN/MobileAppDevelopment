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
import com.ygaps.travelapp.model.Review;

import java.util.List;

public class ListReviewTourAdapter extends ArrayAdapter<Review> {
    private Context mCtx;
    private int resource;
    private List<Review> itemList;

    public ListReviewTourAdapter(Context mCtx, int resource, List<Review> itemList){
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

        Review item=itemList.get(position);

        String tmp;//temporary string variable

        textViewName.setText(item.getName());
        tmp=item.getReview();
        feedBack.setText(tmp);
        ratingBar.setRating(item.getPoint());


        return view;
    }
}
