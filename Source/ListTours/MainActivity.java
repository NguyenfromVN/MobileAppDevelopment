package com.example.deadline0;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //global variables for list tours screen
    List<Item> itemList=new ArrayList<>();
    ListView listView;
    MyCustomListAdapter adapter;
    int page=1;
    int per_page=10;
    int total_pages=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadPage(page,per_page);

        listView=findViewById(R.id.listView);

        adapter=new MyCustomListAdapter(this,
                R.layout.custom_listview_item,itemList);

        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (page-1>=1){
                    page-=1;
                    loadPage(page,per_page);
                }
            }
        });

        ImageView forwardButton=findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (page+1<=total_pages){
                    page+=1;
                    loadPage(page,per_page);
                }
            }
        });
    }

    private void loadPage(int page, final int per_page){
        itemList.clear();

        OkHttpClient client=new OkHttpClient();

        String url="http://35.197.153.192:3000/tour/list?pageNum="+page+"&rowPerPage="+per_page;

        String myToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxNDMiLCJwaG9uZSI6IjEwMjkzODQ3NTYiLCJlbWFpbCI6ImxvbGxvbEBnbWFpbC5jb20iLCJleHAiOjE1NzU4OTE2MTU4OTYsImFjY291bnQiOiJ1c2VyIiwiaWF0IjoxNTczMjk5NjE1fQ.6KP7h3DrpYU816iOPYPSyCcNm-vXy8dmda2B_U6zDrw";

        Request request=new Request.Builder()
                .header("Authorization", myToken)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse=response.body().string();

                    try {
                        final JSONObject jsonObj = new JSONObject(myResponse);
                        JSONArray listTours=jsonObj.getJSONArray("tours");

                        total_pages=((int)Math.ceil(jsonObj.getInt("total")*1.0/per_page));

                        for (int i=0; i<listTours.length(); i++){
                            final Item tmp=new Item(
                                    0,0,"","","",
                                    "","",0,0,
                                    true,""
                            );

                            JSONObject x=listTours.getJSONObject(i);
                            tmp.setId(x.getInt("id"));
                            tmp.setStatus(x.getInt("status"));
                            tmp.setName(x.getString("name"));
                            tmp.setMinCost(x.getString("minCost"));
                            tmp.setMaxCost(x.getString("maxCost"));
                            tmp.setStartDate(x.getString("startDate"));
                            tmp.setEndDate(x.getString("endDate"));
                            tmp.setAdults(x.getInt("adults"));
                            tmp.setChilds(x.getInt("childs"));
                            tmp.setIsPrivate(x.getBoolean("isPrivate"));
                            tmp.setAvatar(x.getString("avatar"));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    itemList.add(tmp);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    } catch (final JSONException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }
}