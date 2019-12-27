package com.ygaps.travelapp.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.CustomListReviewStopPointAdapter;
import com.ygaps.travelapp.adapter.ListReviewTourAdapter;
import com.ygaps.travelapp.model.FeedBack;
import com.ygaps.travelapp.model.Review;
import com.ygaps.travelapp.model.ReviewResponse;
import com.ygaps.travelapp.model.ReviewTourResponse;
import com.ygaps.travelapp.model.ReviewsRequest;
import com.ygaps.travelapp.model.SendReviewTour;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.TourRvStatResponse;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewTourActivity extends AppCompatActivity {

    private static String TAG="Review";
    private UserService userService;
    private String token;
    int id;
    int per_page=1000;
    int total_pages=1;
    int page=1;
    private List<Review> itemList=new ArrayList<>();
    private ListReviewTourAdapter adapter;
    private ListView lw;
    private FloatingActionButton fab;
    private TextView sao1, sao2, sao3, sao4, sao5, trungbinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_tour);
        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);


        sao1 = (TextView)findViewById(R.id.st1);
        sao2 = (TextView)findViewById(R.id.st2);
        sao3 = (TextView)findViewById(R.id.st3);
        sao4 = (TextView)findViewById(R.id.st4);
        sao5 = (TextView)findViewById(R.id.st5);
        trungbinh = (TextView)findViewById(R.id.trungbinh);



        lw=(ListView)findViewById(R.id.listViewReviewTour);

        Intent intent = getIntent();
        token = intent.getExtras().getString("token");
        id = intent.getExtras().getInt("tourId");
        loadStats(token, id);
        loadReviews(token,page,per_page);

        // Send review stop point when click fab button
        fab = (FloatingActionButton)findViewById(R.id.floatingActionButtonRv);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogReview();

            }


        });



    }

    private void loadStats(String token, int id) {
        Call<TourRvStatResponse> call = userService.statRvTour(token, id);

        call.enqueue(new Callback<TourRvStatResponse>() {
            @Override
            public void onResponse(Call<TourRvStatResponse> call, Response<TourRvStatResponse> response) {
                if(response.isSuccessful()){
                    sao1.setText("1 Sao: "+response.body().getList().get(0).getTotal());
                    sao2.setText("2 Sao: "+response.body().getList().get(1).getTotal());
                    sao3.setText("3 Sao: "+response.body().getList().get(2).getTotal());
                    sao4.setText("4 Sao: "+response.body().getList().get(3).getTotal());
                    sao5.setText("5 Sao: "+response.body().getList().get(4).getTotal());
                    long total = 0;
                    long pointTotal=0;
                    for(int i = 0; i<response.body().getList().size(); i++){
                        total+=Integer.parseInt(response.body().getList().get(i).getTotal());
                        Log.d(TAG, "onResponse: "+total);
                        pointTotal+=Integer.parseInt(response.body().getList().get(i).getTotal())*(i+1);
                        Log.d(TAG, "onResponse: "+pointTotal);
                    }
                    trungbinh.setText("Trung bình: "+Long.toString(pointTotal/total));

                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ReviewTourActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ReviewTourActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TourRvStatResponse> call, Throwable t) {

            }
        });


    }

    private void dialogReview(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_review, null);
        final EditText txtRv = (EditText)alertLayout.findViewById(R.id.review);
        final RatingBar ratingBar = (RatingBar)alertLayout.findViewById(R.id.ratingBar2);
        final int userId = id;


        AlertDialog.Builder alert = new AlertDialog.Builder(ReviewTourActivity.this);
        alert.setTitle("Write your review");
        alert.setView(alertLayout);
        alert.setCancelable(true);



        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });


        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(txtRv.getText().toString().length()==0){
                    txtRv.setError("Please write reviews");
                }
                else{
                    txtRv.setError(null);

                    SendReviewTour request = new SendReviewTour();
                    request.setTourId(id);
                    request.setReview(txtRv.getText().toString());
                    request.setPoint((int)ratingBar.getRating());

                    Call<JSONObject> call = userService.sendReviewTour(token,request);

                    call.enqueue(new Callback<JSONObject>() {
                        @Override
                        public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(ReviewTourActivity.this, "Success", Toast.LENGTH_LONG).show();
                                loadReviews(token,1,per_page);
                                loadStats(token, id);

                            }else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(ReviewTourActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(ReviewTourActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<JSONObject> call, Throwable t) {

                        }
                    });
                }
            }
        });

        alert.create();
        alert.show();
    }

    private void loadReviews(String token, int page, int pageSize) {
        itemList.clear();


        Call<ReviewTourResponse> call = userService.loadReviewTour(token, id, page, pageSize);

        call.enqueue(new Callback<ReviewTourResponse>() {
            @Override
            public void onResponse(Call<ReviewTourResponse> call, Response<ReviewTourResponse> response) {
                if(response.isSuccessful()){
                    List<Review> listStopPoints=response.body().getReviewList();
                    for (int i=0; i<listStopPoints.size(); i++){
                        itemList.add(listStopPoints.get(i));

                    }
                    Log.d(TAG, "onResponse: ");
                    adapter = new ListReviewTourAdapter(ReviewTourActivity.this,R.layout.item_review,itemList);
                    lw.setAdapter(adapter);

                }
                else{ try {
                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                    Toast.makeText(ReviewTourActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(ReviewTourActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }}
            }

            @Override
            public void onFailure(Call<ReviewTourResponse> call, Throwable t) {

            }
        });
    }
}
