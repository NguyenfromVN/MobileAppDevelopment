package com.example.projectapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapplication.R;
import com.example.projectapplication.manager.MyApplication;
import com.example.projectapplication.model.ListToursResponse;
import com.example.projectapplication.model.Tour;
import com.example.projectapplication.adapter.MyCustomListAdapter;
import com.example.projectapplication.network.MyAPIClient;
import com.example.projectapplication.network.UserService;
import com.example.projectapplication.model.Item;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTours extends AppCompatActivity {
    public static String TAG  = "ListTours";

    private UserService userService;

    int page=1;
    int per_page=10;
    int total_pages=1;
    String token;

    List<Item> itemList=new ArrayList<>();
    ListView listView;
    MyCustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tours);


        BottomNavigationView bottomNav= (BottomNavigationView) findViewById(R.id.navi);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.list:
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

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        //load token from shared preferences
        MyApplication app = (MyApplication) ListTours.this.getApplication();
        token=app.loadToken();

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
