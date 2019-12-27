package com.ygaps.travelapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.CustomListAdapterForNotification;
import com.ygaps.travelapp.manager.MyApplication;
import com.ygaps.travelapp.model.AcceptInvitationRequest;
import com.ygaps.travelapp.model.LoadNotificationsResponse;
import com.ygaps.travelapp.model.NotificationItem;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationTab extends AppCompatActivity {
    private static List<NotificationItem> itemList=new ArrayList<>();
    private ListView listView;
    private static CustomListAdapterForNotification adapter;

    private static int per_page=1000;
    private static String TAG="NotificationTab";

    private static UserService userService;
    private static String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        BottomNavigationView bottomNav=findViewById(R.id.navi);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.list:
                        Intent intent2 = new Intent(NotificationTab.this, ListTours.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        break;
                    case R.id.history:
                        Intent intent1 = new Intent(NotificationTab.this, HistoryActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        break;
                    case R.id.setting:
                        Intent intent = new Intent(NotificationTab.this, SettingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.explore:
                        Intent intent3 = new Intent(NotificationTab.this, ExploreActivity.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });

        //load token from shared preferences
        MyApplication app = (MyApplication) NotificationTab.this.getApplication();
        token=app.loadToken();

        listView=findViewById(R.id.listView);
        adapter=new CustomListAdapterForNotification(this,
                R.layout.custom_listview_item_notification,itemList);
        listView.setAdapter(adapter);

        //load all notifications of user
        load(1, per_page);
    }

    public static void load(int page, int per_page){
        itemList.clear();

        Call<LoadNotificationsResponse> call = userService.loadNotifications(page,per_page,token);

        call.enqueue(new Callback<LoadNotificationsResponse>() {
            @Override
            public void onResponse(Call<LoadNotificationsResponse> call, Response<LoadNotificationsResponse> response) {
                if(response.isSuccessful()){
                    List<NotificationItem> list=response.body().getTours();

                    for (int i=0; i<list.size(); i++){
                        NotificationItem tmp=new NotificationItem();

                        tmp.setId(list.get(i).getId());
                        tmp.setHostId(list.get(i).getHostId());
                        tmp.setHostName(list.get(i).getHostName());
                        tmp.setHostPhone(list.get(i).getHostPhone());
                        tmp.setHostEmail(list.get(i).getHostEmail());
                        tmp.setStatus(list.get(i).getStatus());
                        tmp.setName(list.get(i).getName());
                        tmp.setMinCost(list.get(i).getMinCost());
                        tmp.setMaxCost(list.get(i).getMaxCost());
                        tmp.setStartDate(list.get(i).getStartDate());
                        tmp.setEndDate(list.get(i).getEndDate());
                        tmp.setAdults(list.get(i).getAdults());
                        tmp.setChilds(list.get(i).getChilds());
                        tmp.setCreatedOn(list.get(i).getCreatedOn());

                        itemList.add(tmp);
                    }

                    //sort list notifications by time, from the newest to the oldest
                    Collections.sort(itemList);

                    adapter.notifyDataSetChanged();
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d(TAG,jObjError.getString("message"));
                    } catch (Exception e) {
                        Log.d(TAG,e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoadNotificationsResponse> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
    }

    public static void confirm(int id, boolean accept){

        AcceptInvitationRequest request=new AcceptInvitationRequest();
        request.setTourId(""+itemList.get(id).getId());
        request.setAccepted(accept);

        Call<JSONObject> call = userService.confirmInvitation(token,request);

        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if(response.isSuccessful()){
                    Toast.makeText(adapter.getContext(), "Success", Toast.LENGTH_LONG).show();

                    //reload notifications list after success
                    load(1,per_page);
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d(TAG,jObjError.getString("message"));
                    } catch (Exception e) {
                        Log.d(TAG,e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
