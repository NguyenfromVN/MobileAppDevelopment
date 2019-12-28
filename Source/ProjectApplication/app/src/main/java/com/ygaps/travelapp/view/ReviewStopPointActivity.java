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
import com.ygaps.travelapp.model.FeedBack;
import com.ygaps.travelapp.model.ReviewResponse;
import com.ygaps.travelapp.model.ReviewsRequest;
import com.ygaps.travelapp.model.TourRvStatResponse;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewStopPointActivity extends AppCompatActivity {

    private static String TAG="Review";
    private UserService userService;
    private String token;
    int id;
    int per_page=1000;
    int total_pages=1;
    int page=1;
    private List<FeedBack> itemList=new ArrayList<>();
    private CustomListReviewStopPointAdapter adapter;
    private ListView lw;
    private View view;
    private FloatingActionButton fab;

    private TextView sao1, sao2, sao3, sao4, sao5, trungbinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_stop_point);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        lw=(ListView)findViewById(R.id.listViewReview);

        sao1 = (TextView)findViewById(R.id.st1);
        sao2 = (TextView)findViewById(R.id.st2);
        sao3 = (TextView)findViewById(R.id.st3);
        sao4 = (TextView)findViewById(R.id.st4);
        sao5 = (TextView)findViewById(R.id.st5);
        trungbinh = (TextView)findViewById(R.id.textViewAverage);

        Intent intent =getIntent();
        id = Integer.parseInt(intent.getExtras().getString("stopPointId"));
        token = intent.getExtras().getString("token");

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

        Call<TourRvStatResponse> call = userService.statSpTour(token, id);

        call.enqueue(new Callback<TourRvStatResponse>() {
            @Override
            public void onResponse(Call<TourRvStatResponse> call, Response<TourRvStatResponse> response) {
                if(response.isSuccessful()){
                    sao1.setText(response.body().getList().get(0).getTotal());
                    sao2.setText(response.body().getList().get(1).getTotal());
                    sao3.setText(response.body().getList().get(2).getTotal());
                    sao4.setText(response.body().getList().get(3).getTotal());
                    sao5.setText(response.body().getList().get(4).getTotal());
                    long total = 0;
                    long pointTotal=0;
                    for(int i = 0; i<response.body().getList().size(); i++){
                        total+=Integer.parseInt(response.body().getList().get(i).getTotal());
                        Log.d(TAG, "onResponse: "+total);
                        pointTotal+=Integer.parseInt(response.body().getList().get(i).getTotal())*(i+1);
                        Log.d(TAG, "onResponse: "+pointTotal);
                    }
                    double avg=0;
                    if (total>0)
                        avg=pointTotal*1.0/total;

                    trungbinh.setText(String.format("%.1f",avg));
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ReviewStopPointActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ReviewStopPointActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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


        AlertDialog.Builder alert = new AlertDialog.Builder(ReviewStopPointActivity.this);
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

                    ReviewsRequest request = new ReviewsRequest();
                    request.setId(userId);
                    request.setFeedback(txtRv.getText().toString());
                    request.setPoint((int)ratingBar.getRating());

                    Call<JSONObject> call = userService.addReview(request,token);

                    call.enqueue(new Callback<JSONObject>() {
                        @Override
                        public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(ReviewStopPointActivity.this, "Success", Toast.LENGTH_LONG).show();
                                loadReviews(token,1,per_page);
                                loadStats(token, id);

                            }else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(ReviewStopPointActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(ReviewStopPointActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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


        Call<ReviewResponse> call = userService.loadReview(page,pageSize,id,token);

        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()){
                    List<FeedBack> listStopPoints=response.body().getFeedBackList();
                    for (int i=0; i<listStopPoints.size(); i++){
                        itemList.add(listStopPoints.get(i));

                    }
                    Log.d(TAG, "onResponse: ");
                    total_pages=(int)Math.round(response.body().getTotal()+0.5);
                    adapter = new CustomListReviewStopPointAdapter(ReviewStopPointActivity.this,R.layout.item_review,itemList);
                    lw.setAdapter(adapter);

                }
                else{ try {
                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                    Toast.makeText(ReviewStopPointActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(ReviewStopPointActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }}
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });
    }
}

