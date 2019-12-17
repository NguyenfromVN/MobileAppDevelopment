package com.example.projectapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapplication.R;
import com.example.projectapplication.manager.MyApplication;
import com.example.projectapplication.model.ListStopSearch;
import com.example.projectapplication.model.LoadListStopPointResponse;
import com.example.projectapplication.model.StopPoint;
import com.example.projectapplication.model.StopPointForTour;
import com.example.projectapplication.model.StopPointSearch;
import com.example.projectapplication.network.MyAPIClient;
import com.example.projectapplication.network.UserService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchExploreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnSearch;
    private EditText txtSearch;
    private Spinner spinner;
    private ListView lw;
    private String name;
    private int idPronvice = -1;
    private List<StopPointSearch> itemList=new ArrayList<>();
    private ArrayAdapter<StopPointSearch> adapter;
    private UserService userService;
    int page=1;
    String key;
    int per_page=10;
    int total_pages=1;
    String token;

    private String[] listProvinces= {
            "Choose a province",
            "Hồ Chí Minh",
            "Hà Nội",
            "Đà Nẵng",
            "Bình Dương",
            "Đồng Nai",
            "Khánh Hòa",
            "Hải Phòng",
            "Long An",
            "Quảng Nam",
            "Bà Rịa Vũng Tàu",
            "Đắk Lắk",
            "Cần Thơ",
            "Bình Thuận",
            "Lâm Đồng",
            "Thừa Thiên Huế",
            "Kiên Giang",
            "Bắc Ninh",
            "Quảng Ninh",
            "Thanh Hóa",
            "Nghệ An",
            "Hải Dương",
            "Gia Lai",
            "Bình Phước",
            "Hưng Yên",
            "Bình Định",
            "Tiền Giang",
            "Thái Bình",
            "Bắc Giang",
            "Hòa Bình",
            "An Giang",
            "Vĩnh Phúc",
            "Tây Ninh",
            "Thái Nguyên",
            "Lào Cai",
            "Nam Định",
            "Quảng Ngãi",
            "Bến Tre",
            "Đắk Nông",
            "Cà Mau",
            "Vĩnh Long",
            "Ninh Bình",
            "Phú Thọ",
            "Ninh Thuận",
            "Phú Yên",
            "Hà Nam",
            "Hà Tĩnh",
            "Đồng Tháp",
            "Sóc Trăng",
            "Kon Tum",
            "Quảng Bình",
            "Quảng Trị",
            "Trà Vinh",
            "Hậu Giang",
            "Sơn La",
            "Bạc Liêu",
            "Yên Bái",
            "Tuyên Quang",
            "Điện Biên",
            "Lai Châu",
            "Lạng Sơn",
            "Hà Giang",
            "Bắc Kạn",
            "Cao Bằng"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_explore);

        init();

        spinner.setOnItemSelectedListener(this);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ID123", "onClick: "+idPronvice+" "+name);
                closeKeyBoard();
                if (idPronvice == -1)
                    Toast.makeText(SearchExploreActivity.this, "Please select a province", Toast.LENGTH_LONG).show();
                else {
                    key = txtSearch.getText().toString();
                    if(key.length()!=0){
                        page=1;
                        loadSearchStopPoint(key, name, idPronvice,per_page,page,token);}
                    else
                        Toast.makeText(SearchExploreActivity.this, "Please fill search key", Toast.LENGTH_LONG).show();

                }
            }
        });

        ImageView backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (page-1>=1){
                    page-=1;
                    loadSearchStopPoint(key, name,idPronvice,per_page, page,token);
                }
            }
        });

        ImageView forwardButton=findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (page+1<=total_pages){
                    page+=1;
                    loadSearchStopPoint(key, name,idPronvice,per_page, page,token);
                }
            }
        });


        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchExploreActivity.this, DetailStopPointActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                bundle.putInt("Id", itemList.get(position).getId());
                bundle.putString("Name", itemList.get(position).getName());
                bundle.putString("Address",itemList.get(position).getAddress());
                bundle.putInt("minCost", itemList.get(position).getMinCost());
                bundle.putInt("maxCost", itemList.get(position).getMinCost());
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

    // close key board
    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }

    private void init() {
        btnSearch=(Button)findViewById(R.id.btnSearch);
        lw = (ListView)findViewById(R.id.listViewStop);
        txtSearch = (EditText)findViewById(R.id.search);
        spinner = (Spinner)findViewById(R.id.spinner);



        MyApplication app = (MyApplication) SearchExploreActivity.this.getApplication();
        token=app.loadToken();

        // Spinner select province
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(SearchExploreActivity.this,
                android.R.layout.simple_spinner_item,listProvinces);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }


    // Load search stop points
    private void loadSearchStopPoint(String searchKey, String province, int id, final int per_page, int page, String token) {
        itemList.clear();
        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        TextView textViewCurrentPage=findViewById(R.id.textViewCurrentPage);
        textViewCurrentPage.setText("Page\n"+page);
        Log.d("Request", "loadSearchStopPoint: "+searchKey+" "+province+" " +id+" "+per_page+" "+page+" "+token);

        Call<ListStopSearch> call = userService.loadSearchStopPoint(searchKey,id,province,page,per_page,token);
        call.enqueue(new Callback<ListStopSearch>() {
            @Override
            public void onResponse(Call<ListStopSearch> call, Response<ListStopSearch> response) {
                if(response.isSuccessful())
                {
                    List<StopPointSearch> listStopPoints=response.body().getListStop();
                    for (int i=0; i<listStopPoints.size(); i++){
                        itemList.add(listStopPoints.get(i));

                    }
                    total_pages=(int)Math.round(response.body().getTotal()+0.5);
                    adapter = new ArrayAdapter<>(SearchExploreActivity.this, android.R.layout.simple_list_item_1, itemList);
                    lw.setAdapter(adapter);
                }
                else{
                try {
                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                    Toast.makeText(SearchExploreActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(SearchExploreActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }}
            }

            @Override
            public void onFailure(Call<ListStopSearch> call, Throwable t) {
                Log.d("Search", t.getMessage());

            }
        });


    }


    // Spinner method
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0)
        {
            name = parent.getItemAtPosition(position).toString();
            idPronvice = position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
