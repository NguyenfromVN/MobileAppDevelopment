package com.ygaps.travelapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.MyCustomListAdapter;
import com.ygaps.travelapp.model.Item;
import com.ygaps.travelapp.model.ListToursResponse;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTourActivity extends AppCompatActivity {

    private UserService userService;
    private String token;
    private List<Item> itemList=new ArrayList<>();
    private ListView listView;
    private MyCustomListAdapter adapter;
    private Button btn_search;
    private EditText edt_search;
    private String TAG="SearchTour";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tour);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);


        Intent intent = getIntent();
        token = intent.getExtras().getString("token");

        edt_search = (EditText)findViewById(R.id.search);
        listView = (ListView)findViewById(R.id.listViewStop);
        adapter=new MyCustomListAdapter(this,
                R.layout.custom_listview_item,itemList);


        //handle list view item click event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent tourDetail = new Intent(SearchTourActivity.this, TourDetail.class);
                tourDetail.putExtra("id", itemList.get(position).getId());
                tourDetail.putExtra("status", itemList.get(position).getStatus());
                tourDetail.putExtra("name", itemList.get(position).getName());
                tourDetail.putExtra("minCost", Integer.valueOf(itemList.get(position).getMinCost()));
                tourDetail.putExtra("maxCost", Integer.valueOf(itemList.get(position).getMaxCost()));
                tourDetail.putExtra("startDate", Long.valueOf(itemList.get(position).getStartDate()));
                tourDetail.putExtra("endDate", Long.valueOf(itemList.get(position).getEndDate()));
                tourDetail.putExtra("adults", itemList.get(position).getAdults());
                tourDetail.putExtra("childs", itemList.get(position).getChilds());
                startActivity(tourDetail);
            }
        });


        btn_search = (Button)findViewById(R.id.btnSearch);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = edt_search.getText().toString();
                Log.d("SearchTour", "onClick: "+key);
                if(key.length()==0)
                    edt_search.setError("Vui lòng nhập từ khoá");
                else loadSearchHisto(token, key);
            }
        });

    }

    private void loadSearchHisto(String token, String key) {

        itemList.clear();

        Call<ListToursResponse> call = userService.searchTour(key,1,1000,token);

        call.enqueue(new Callback<ListToursResponse>() {
            @Override
            public void onResponse(Call<ListToursResponse> call, Response<ListToursResponse> response) {
                if(response.isSuccessful()){
                    closeKeyBoard();
                    List<Tour> tours=response.body().getTours();
                    for (int i=0; i<tours.size(); i++){
                        if(tours.get(i).getStatus()!=-1){
                            Tour x=tours.get(i);
                            Item tmp=new Item(
                                    0,0,"","","",
                                    "","",0,0,
                                    false,""
                            );
                            Log.d(TAG, "onResponse: "+x.getName());
                            tmp.setId(x.getId());
                            tmp.setStatus(x.getStatus());
                            tmp.setName(x.getName());
                            tmp.setMinCost(x.getMinCost());
                            tmp.setMaxCost(x.getMaxCost());
                            tmp.setStartDate(x.getStartDate());
                            tmp.setEndDate(x.getEndDate());
                            tmp.setAdults(x.getAdults());
                            tmp.setChilds(x.getChilds());
                            tmp.setAvatar(x.getAvatar());
                            itemList.add(tmp);
                            }
                    }
                    adapter=new MyCustomListAdapter(SearchTourActivity.this,
                            R.layout.custom_listview_item,itemList);
                    listView.setAdapter(adapter);
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(SearchTourActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(SearchTourActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListToursResponse> call, Throwable t) {

            }
        });


    }

    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }
}
