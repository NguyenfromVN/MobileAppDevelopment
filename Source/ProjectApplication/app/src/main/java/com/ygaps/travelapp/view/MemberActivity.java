package com.ygaps.travelapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.CustomListAdapterForMembers;
import com.ygaps.travelapp.adapter.ListReviewTourAdapter;
import com.ygaps.travelapp.model.Member;
import com.ygaps.travelapp.model.Review;
import com.ygaps.travelapp.model.TourInforResponse;
import com.ygaps.travelapp.model.TourRvStatResponse;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberActivity extends AppCompatActivity {

    private UserService userService;
    private String token;
    private int id;
    ListView lw;
    boolean isInvited;
    private List<Member> itemList=new ArrayList<>();
    private CustomListAdapterForMembers adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);
        Intent intent = getIntent();
        token = intent.getExtras().getString("token");
        id = intent.getExtras().getInt("tourId");
        Log.d("Members", "onResponse: "+id);

        lw = (ListView)findViewById(R.id.listViewMember);

        loadMember(token, id);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.floatingActionButtonRv);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberActivity.this, SearchUserActivity.class);
                intent.putExtra("tourId", id);
                intent.putExtra("isInvited", isInvited);
                startActivity(intent);

            }


        });



    }

    private void loadMember(String token, int id) {
        Call<TourInforResponse> call = userService.getTourDetail(id, token);
        call.enqueue(new Callback<TourInforResponse>() {
            @Override
            public void onResponse(Call<TourInforResponse> call, Response<TourInforResponse> response) {
                if(response.isSuccessful()){
                    itemList.clear();
                    List<Member> listStopPoints=response.body().getMembers();
                    for (int i=0; i<listStopPoints.size(); i++){
                        itemList.add(listStopPoints.get(i));

                    }

                    adapter = new CustomListAdapterForMembers(MemberActivity.this,R.layout.member_item,itemList);
                    lw.setAdapter(adapter);;
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MemberActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MemberActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TourInforResponse> call, Throwable t) {
                Log.d("List Stop Point", t.getMessage());
            }
        });

    }
}
