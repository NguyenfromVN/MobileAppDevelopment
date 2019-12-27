package com.ygaps.travelapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.CustomListAdapterForComment;
import com.ygaps.travelapp.adapter.CustomListAdapterForMembers;
import com.ygaps.travelapp.model.CommentResponse;
import com.ygaps.travelapp.model.Comments;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;

import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {


    private UserService userService;
    private String token;
    private int id;
    ListView lw;
    private List<Comments> itemList=new ArrayList<>();
    private CustomListAdapterForComment adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);
        Intent intent = getIntent();
        token = intent.getExtras().getString("token");
        id = intent.getExtras().getInt("tourId");
        lw = (ListView)findViewById(R.id.listViewCommentTour);

        loadComment(token, id);

    }

    private void loadComment(String token, int id) {
        Call<CommentResponse> call = userService.getListCmt(token, id, 1000, 1);
        Log.d("Comment", "onResponse: "+token+id);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if(response.isSuccessful()){
                    itemList.clear();
                    List<Comments> listStopPoints=response.body().getCommentList();
                    ;
                    for (int i=0; i<listStopPoints.size(); i++){
                        itemList.add(listStopPoints.get(i));
                        Log.d("Comment", "onResponse: "+itemList.get(i).getComment());
                    }

                    Log.d("Comment", "onResponse: "+listStopPoints.size());
                    adapter = new CustomListAdapterForComment(CommentActivity.this,R.layout.member_item,itemList);
                    lw.setAdapter(adapter);;
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(CommentActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(CommentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {

            }
        });

    }
}
