package com.example.projectapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectapplication.R;
import com.example.projectapplication.manager.MyApplication;
import com.example.projectapplication.model.CoordinateSet;
import com.example.projectapplication.model.LatLong;
import com.example.projectapplication.model.LoadListStopPointRequest;
import com.example.projectapplication.model.LoadListStopPointResponse;
import com.example.projectapplication.model.StopPoint;
import com.example.projectapplication.network.MyAPIClient;
import com.example.projectapplication.network.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreActivity extends AppCompatActivity {
    private ListView lw;
    private List<StopPoint> itemList=new ArrayList<>();
    private ArrayAdapter<StopPoint> adapter;
    private UserService userService;
    private Button btnSearch;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);



        // Bottom navi
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navi);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.history:
                        Intent intent2 = new Intent(ExploreActivity.this, HistoryActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        break;
                    case R.id.list:
                        Intent intent = new Intent(ExploreActivity.this, ListTours.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.setting:
                        Intent intent1 = new Intent(ExploreActivity.this, SettingActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });


        btnSearch=(Button)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExploreActivity.this, SearchExploreActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        //List stop point
        listStopPoint();
    }



    private void listStopPoint() {

        lw = (ListView)findViewById(R.id.listViewStop);

        loadStopPoint();

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ExploreActivity.this, DetailStopPointActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("Id", itemList.get(position).getId());
                bundle.putString("Name", itemList.get(position).getName());
                bundle.putString("Address",itemList.get(position).getAddress());
                bundle.putString("Contact", itemList.get(position).getContact());
                bundle.putInt("minCost", itemList.get(position).getMinCost());
                bundle.putInt("maxCost", itemList.get(position).getMinCost());
                MyApplication app = (MyApplication)ExploreActivity.this.getApplication();
                token=app.loadToken();
                Log.d("Explore", "onItemClick: "+token);
                bundle.putString("token", token);
                switch (itemList.get(position).getServiceTypeId()){
                    case 1:
                        bundle.putString("Service", "Restaurant");
                    break;
                    case 2:
                        bundle.putString("Service", "Hotel");
                        break;
                    case 3:
                        bundle.putString("Service", "Rest Station");
                        break;
                    case 4:
                        bundle.putString("Service", "Other");
                        break;
                }

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    private void loadStopPoint() {

         CoordinateSet coordinateSet =  new CoordinateSet();
        //load token from shared preferences
        MyApplication app = (MyApplication) ExploreActivity.this.getApplication();
        token=app.loadToken();

        //
        List<CoordinateSet> coordList=new ArrayList<>();

        List<LatLong> tmp=new ArrayList<>();

        tmp.add(new LatLong(23.980056,85.577677));
        tmp.add(new LatLong(23.980056,163.065945));

        coordinateSet.setListLatLong(tmp);
        coordList.add(coordinateSet);

        tmp.clear();
        tmp.add(new LatLong(-12.609835, 163.707522));
        tmp.add(new LatLong(-13.928084,75.526301));

        coordinateSet.setListLatLong(tmp);
        coordList.add(coordinateSet);

        LoadListStopPointRequest request=new LoadListStopPointRequest();
        request.setHasOneCoordinate(false);
        request.setCoordList(coordList);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        Call<LoadListStopPointResponse> call = userService.loadListStopPoint(request,token);

        call.enqueue(new Callback<LoadListStopPointResponse>() {
            @Override
            public void onResponse(Call<LoadListStopPointResponse> call, Response<LoadListStopPointResponse> response) {
                if(response.isSuccessful()){
                    List<StopPoint> listStopPoints=response.body().getStopPoints();
                    for (int i=0; i<listStopPoints.size(); i++){
                        itemList.add(listStopPoints.get(i));

                    }
                    adapter = new ArrayAdapter<StopPoint>(ExploreActivity.this, android.R.layout.simple_list_item_1, itemList);
                    lw.setAdapter(adapter);

                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ExploreActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ExploreActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<LoadListStopPointResponse> call, Throwable t) {
                Log.d("List Stop Point", t.getMessage());
            }
        });

    }

    }


