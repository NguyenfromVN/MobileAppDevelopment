package com.ygaps.travelapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.manager.MyApplication;
import com.ygaps.travelapp.model.InvitedRequest;
import com.ygaps.travelapp.model.ListStopSearch;
import com.ygaps.travelapp.model.SearchUserResponse;
import com.ygaps.travelapp.model.StopPointSearch;
import com.ygaps.travelapp.model.UserInforResponse;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserActivity extends AppCompatActivity {

    private Button btnSearch;
    private EditText txtSearch;
    private Spinner spinner;
    private ListView lw;
    private String name;
    private int idPronvice = -1;
    private List<UserInforResponse> itemList=new ArrayList<>();
    private ArrayAdapter<UserInforResponse> adapter;
    private UserService userService;
    int page=1;
    String key;
    int per_page=10;
    int total_pages=1;
    String token;
    int tourId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        Intent intent1 = getIntent();

        tourId = intent1.getExtras().getInt("tourId");

        init();

        //spinner.setOnItemSelectedListener(this);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ID123", "onClick: "+idPronvice+" "+name);
                closeKeyBoard();
//                if (idPronvice == -1)
//                    Toast.makeText(SearchExploreActivity.this, "Please select a province", Toast.LENGTH_LONG).show();
//                else {
                key = txtSearch.getText().toString();
                if(key.length()!=0){
                    page=1;
                    loadUser(key,per_page,page,token);}
                else
                    Toast.makeText(SearchUserActivity.this, "Please fill search key", Toast.LENGTH_LONG).show();

                //}
            }
        });

        ImageView backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (page-1>=1){
                    page-=1;
                    loadUser(key,per_page, page,token);
                }
            }
        });

        ImageView forwardButton=findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (page+1<=total_pages){
                    page+=1;
                    loadUser(key,per_page, page,token);
                }
            }
        });

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final InvitedRequest request = new InvitedRequest();
                request.setInvited(true);
                request.setInvitedUserId(itemList.get(position).getId());
                request.setTourId(tourId);
                Log.d("invited", "onItemClick: "+itemList.get(position).getId()+" "+tourId +" "+token);
                Call<JSONObject> call = userService.addMember(token, request);
                call.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(SearchUserActivity.this, "Successfully Invited", Toast.LENGTH_LONG).show();
                        }
                        else{
                            try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(SearchUserActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(SearchUserActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }}
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void init() {

        btnSearch=(Button)findViewById(R.id.btnSearch);
        lw = (ListView)findViewById(R.id.listViewStop);
        txtSearch = (EditText)findViewById(R.id.search);
        MyApplication app = (MyApplication) SearchUserActivity.this.getApplication();
        token=app.loadToken();


    }

    private void loadUser(String key,final int per_page, int page, String token) {
        itemList.clear();
        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        TextView textViewCurrentPage=findViewById(R.id.textViewCurrentPage);
        textViewCurrentPage.setText("Page\n"+page);

        Call<SearchUserResponse> call = userService.searchUser(key,page,per_page,token);
        call.enqueue(new Callback<SearchUserResponse>() {
            @Override
            public void onResponse(Call<SearchUserResponse> call, Response<SearchUserResponse> response) {
                if(response.isSuccessful())
                {
                    List<UserInforResponse> listStopPoints=response.body().getUsers();
                    for (int i=0; i<listStopPoints.size(); i++){
                        itemList.add(listStopPoints.get(i));

                    }
                    total_pages=((int)Math.ceil(response.body().getTotal()*1.0/per_page));
                    adapter = new ArrayAdapter<>(SearchUserActivity.this, android.R.layout.simple_list_item_1, itemList);
                    lw.setAdapter(adapter);
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(SearchUserActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(SearchUserActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }}
            }

            @Override
            public void onFailure(Call<SearchUserResponse> call, Throwable t) {
                Log.d("Search", t.getMessage());

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
