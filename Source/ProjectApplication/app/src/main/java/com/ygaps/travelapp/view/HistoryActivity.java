package com.ygaps.travelapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.MyCustomListAdapter;
import com.ygaps.travelapp.manager.MyApplication;
import com.ygaps.travelapp.model.GetHistoryByStatusResponse;
import com.ygaps.travelapp.model.Item;
import com.ygaps.travelapp.model.ListToursResponse;
import com.ygaps.travelapp.model.StatusTotal;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private UserService userService;
    private String token;
    private List<Item> itemList=new ArrayList<>();
    private ListView listView;
    private MyCustomListAdapter adapter;
    private String TAG="HistoryActivity";
    private int per_page=5;
    private int total_pages=1;
    private int page=1;
    private int total=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //navigate bottom bar
        BottomNavigationView bottomNav=findViewById(R.id.navi);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.list:
                        Intent intent2 = new Intent(HistoryActivity.this, ListTours.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        break;
                    case R.id.setting:
                        Intent intent = new Intent(HistoryActivity.this, SettingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.explore:
                        Intent intent1 = new Intent(HistoryActivity.this, ExploreActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        break;
                    case R.id.noti:
                        Intent intent3 = new Intent(HistoryActivity.this, NotificationTab.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        //load token from shared preferences
        MyApplication app = (MyApplication) HistoryActivity.this.getApplication();
        token=app.loadToken();

        //send request for list tours of user
        loadPage(token,1,per_page);

        //send request for statistics
        loadstatistics(token);

        listView=findViewById(R.id.listViewUserTours);

        adapter=new MyCustomListAdapter(this,
                R.layout.custom_listview_item,itemList);

        listView.setAdapter(adapter);

        //handle page down button click event
        ImageView backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (page-1>=1){
                    page-=1;
                    loadPage(token,page,per_page);
                }
            }
        });

        //handle page up button click event
        ImageView forwardButton=findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (page+1<=total_pages){
                    page+=1;
                    loadPage(token,page,per_page);
                }
            }
        });

        //handle list view item click event
        ((ListView)findViewById(R.id.listViewUserTours)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent tourDetail = new Intent(HistoryActivity.this, TourDetail.class);
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
    }

    public void loadstatistics(String token){
        Call<GetHistoryByStatusResponse> call = userService.getHistoryUserByStatus(token);

        call.enqueue(new Callback<GetHistoryByStatusResponse>() {
            @Override
            public void onResponse(Call<GetHistoryByStatusResponse> call, Response<GetHistoryByStatusResponse> response) {
                if(response.isSuccessful()){
                    for (int i=0; i<response.body().getTotalToursGroupedByStatus().size(); i++){
                        StatusTotal x=response.body().getTotalToursGroupedByStatus().get(i);
                        switch (x.getStatus()){
                            case -1: ((TextView)findViewById(R.id.cancelled)).setText(""+x.getTotal());
                                break;
                            case 0: ((TextView)findViewById(R.id.news)).setText(""+x.getTotal());
                                break;
                            case 1: ((TextView)findViewById(R.id.started)).setText(""+x.getTotal());
                                break;
                            case 2: ((TextView)findViewById(R.id.completed)).setText(""+x.getTotal());
                                break;
                        }
                    }
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(HistoryActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(HistoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetHistoryByStatusResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public void loadPage(String token, int page, final int per_page){

        itemList.clear();

        TextView textViewCurrentPage=findViewById(R.id.textViewCurrentPage);
        textViewCurrentPage.setText("Page\n"+page);

        Call<ListToursResponse> call = userService.loadListToursOfUser(page,per_page,token);

        call.enqueue(new Callback<ListToursResponse>() {
            @Override
            public void onResponse(Call<ListToursResponse> call, Response<ListToursResponse> response) {
                if(response.isSuccessful()){
                    total=response.body().getTotal();

                    ((TextView)findViewById(R.id.total)).setText(""+total);

                    total_pages=((int)Math.ceil(total*1.0/per_page));
                    List<Tour> tours=response.body().getTours();
                    for (int i=0; i<tours.size(); i++){
                        Tour x=tours.get(i);
                        Item tmp=new Item(
                                0,0,"","","",
                                "","",0,0,
                                false,""
                        );
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
                        adapter.notifyDataSetChanged();
                    }
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(HistoryActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(HistoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListToursResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
