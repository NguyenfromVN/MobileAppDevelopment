package com.ygaps.travelapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.manager.MyApplication;
import com.ygaps.travelapp.model.ListToursResponse;
import com.ygaps.travelapp.model.RegisterFirebaseRequest;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.adapter.MyCustomListAdapter;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;
import com.ygaps.travelapp.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTours extends AppCompatActivity {
    public String TAG  = "ListTours";

    private UserService userService;
    private String android_id;

    private int page=1;
    private int per_page=10;
    private int total_pages=1;
    private String token;

    private List<Item> itemList=new ArrayList<>();
    private ListView listView;
    private MyCustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tours);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);
        MyApplication app = (MyApplication) ListTours.this.getApplication();

        BottomNavigationView bottomNav= (BottomNavigationView) findViewById(R.id.navi);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.history:
                        Intent intent2 = new Intent(ListTours.this, HistoryActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        break;
                    case R.id.setting:
                        Intent intent = new Intent(ListTours.this, SettingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.explore:
                        Intent intent1 = new Intent(ListTours.this, ExploreActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });

        //load token from shared preferences
        token=app.loadToken();

        //load device_id
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        //get fcm token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        //save fcm token to shared preferences
                        MyApplication app = (MyApplication) ListTours.this.getApplication();
                        app.setFcmToken(token);

                        //register for receiving notification form firebase
                        registerFirebaseToken(token);
                    }
                });

        //send request for list tours
        loadPage(token,page,per_page);

        listView=findViewById(R.id.listView);

        adapter=new MyCustomListAdapter(this,
                R.layout.custom_listview_item,itemList);

        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(ListTours.this, CreateTour.class);
                startActivity(reg);
            }
        });

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
    }

    public void registerFirebaseToken(String fcmToken){

        RegisterFirebaseRequest request=new RegisterFirebaseRequest();
        request.setFcmToken(fcmToken);
        request.setDeviceId(android_id);
        request.setPlatform(1);
        request.setAppVersion("1.0");

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);
        Call<JSONObject> call = userService.registerFirebase(request,token);

        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.isSuccessful()) {
                    //do nothing
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
                Log.d(TAG,t.getMessage());
            }
        });
    }

    public void loadPage(String token, int page, final int per_page){
        itemList.clear();

        TextView textViewCurrentPage=findViewById(R.id.textViewCurrentPage);
        textViewCurrentPage.setText("Page\n"+page);

        Call<ListToursResponse> call = userService.listTours(per_page,page,token);

        call.enqueue(new Callback<ListToursResponse>() {
            @Override
            public void onResponse(Call<ListToursResponse> call, Response<ListToursResponse> response) {
                if(response.isSuccessful()){
                    List<Tour> tours=response.body().getTours();
                    total_pages=((int)Math.ceil(response.body().getTotal()*1.0/per_page));
                    for (int i=0; i<tours.size(); i++){
                        Tour x=tours.get(i);
                        Item tmp=new Item(
                                0,0,"","","",
                                "","",0,0,
                                true,""
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
                        tmp.setIsPrivate(x.getIsPrivate());
                        tmp.setAvatar(x.getAvatar());

                        itemList.add(tmp);
                        adapter.notifyDataSetChanged();
                    }

                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ListTours.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ListTours.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
