package com.ygaps.travelapp.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.manager.MyApplication;
import com.ygaps.travelapp.model.AddStopPointRequest;
import com.ygaps.travelapp.model.AddStopPointResponse;
import com.ygaps.travelapp.model.CreateTourRequest;
import com.ygaps.travelapp.model.StopPointForTour;
import com.ygaps.travelapp.model.TourInforResponse;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourDetail extends AppCompatActivity {
    private int id;
    private int status;
    private String name;
    private int minCost;
    private int maxCost;
    private static long startDate;
    private static long endDate;
    private int adults;
    private int childs;
    private static int flagStartDate=0;
    private static int flagEndDate=0;
    private static int startYear, startMonth, startDay;
    private static int endYear, endMonth, endDay;
    private static TextView textViewStartDate, textViewEndDate;
    private UserService userService;
    private ListView listView;
    private static List<StopPointForTour> itemList=new ArrayList<>();
    private ArrayAdapter<StopPointForTour> adapter;
    private boolean isPrivate;
    private static boolean flagArriveDatePicker=false;
    private static boolean flagLeaveDatePicker=false;
    private static TextView textViewArriveDate;
    private static TextView textViewLeaveDate;
    private static int currentStopPointId;
    private String[] listProvinces= {
            "",
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
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tour_detail);

        listView=findViewById(R.id.listViewStopPoints);

        adapter = new ArrayAdapter<StopPointForTour>(TourDetail.this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        MyApplication app = (MyApplication) TourDetail.this.getApplication();
        token=app.loadToken();

        textViewStartDate = findViewById(R.id.textViewStartDate);
        textViewEndDate = findViewById(R.id.textViewEndDate);

        //get information for tour
        Intent activityThatCalled=getIntent();
        name=activityThatCalled.getExtras().getString("name");
        startDate=activityThatCalled.getExtras().getLong("startDate");
        endDate=activityThatCalled.getExtras().getLong("endDate");
        adults=activityThatCalled.getExtras().getInt("adults");
        childs=activityThatCalled.getExtras().getInt("childs");
        minCost=activityThatCalled.getExtras().getInt("minCost");
        maxCost=activityThatCalled.getExtras().getInt("maxCost");
        status=activityThatCalled.getExtras().getInt("status");
        id=activityThatCalled.getExtras().getInt("id");

        //set information for tour
        ((EditText)findViewById(R.id.editTextName)).setText(name);
        ((EditText)findViewById(R.id.editTextInputAdults)).setText(adults+"");
        ((EditText)findViewById(R.id.editTextInputChilds)).setText(childs+"");
        ((EditText)findViewById(R.id.editTextMinCost)).setText(minCost+"");
        ((EditText)findViewById(R.id.editTextMaxCost)).setText(maxCost+"");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        ((TextView)findViewById(R.id.textViewStartDate)).setText(df.format(startDate));
        ((TextView)findViewById(R.id.textViewEndDate)).setText(df.format(endDate));
        String temp="";
        switch (status){
            case -1:temp="Cancelled";
                break;
            case 0:temp="Open";
                break;
            case 1:temp="Started";
                break;
            case 2:temp="Closed";
                break;
        }
        ((TextView)findViewById(R.id.textViewStatus)).setText(temp);

        loadListStopPoints(id);

        findViewById(R.id.buttonStartDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagStartDate=1;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        findViewById(R.id.buttonEndDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagEndDate=1;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        //handle save tour event
        ((Button)findViewById(R.id.buttonSaveTour)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTour(-2);
            }
        });

        //handle del tour
        ((Button)findViewById(R.id.buttonDelTour)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTour(-1);
            }
        });

        //handle list view item click event
        ((ListView)findViewById(R.id.listViewStopPoints)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentStopPointId=position;
                createAddStopPointForm(position);
            }
        });

        findViewById(R.id.checkBoxIsPrivate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPrivate=!isPrivate;
            }
        });
    }

    //create an alert dialog as a form for user to fill up
    private void createAddStopPointForm(final int index) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stop point information");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_layout_for_stop_point, null);

        builder.setView(customLayout);

        // add delete button
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete stop point here
                deleteStopPoint(index);
            }
        });

        //add close button
        builder.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //close the form without any action
                //list view of stop points will be reloaded to make sure all changes will be discarded
                loadListStopPoints(id);
            }
        });

        //add save button
        builder.setNegativeButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //set information from view to item list
                itemList.get(index).setName(((EditText)customLayout.findViewById(R.id.editTextStopPointName)).getText().toString());
                //save changes for stop point
                updateStopPoint(index);
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        //add listener for date pickers
        dialog.findViewById(R.id.buttonArriveAt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagArriveDatePicker=true;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        dialog.findViewById(R.id.buttonLeaveAt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagLeaveDatePicker=true;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        //set information about selected stop point

        ((EditText)dialog.findViewById(R.id.editTextStopPointName)).setText(itemList.get(index).getName());
        String tmp="";
        switch (itemList.get(index).getServiceTypeId()){
            case 1: tmp="Nhà hàng";
                break;
            case 2: tmp="Khách sạn";
                break;
            case 3: tmp="Trạm nghỉ";
                break;
            case 4: tmp="Khác";
                break;
        }

        ((EditText)dialog.findViewById(R.id.editTextServiceType)).setText(tmp);
        ((EditText)dialog.findViewById(R.id.editTextAddress)).setText(itemList.get(index).getAddress());
        ((EditText)dialog.findViewById(R.id.editTextProvince)).setText(
                listProvinces[itemList.get(index).getProvinceId()]);
        ((EditText)dialog.findViewById(R.id.editTextMinCostStopPoint)).setText(String.valueOf(minCost));
        ((EditText)dialog.findViewById(R.id.editTextMaxCostStopPoint)).setText(String.valueOf(maxCost));

        textViewArriveDate=dialog.findViewById(R.id.textViewArriveAt);
        textViewLeaveDate=dialog.findViewById(R.id.textViewLeaveAt);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        textViewArriveDate.setText(df.format(itemList.get(index).getArrivalAt()));
        textViewLeaveDate.setText(df.format(itemList.get(index).getLeaveAt()));
    }

    private void updateStopPoint(int index){
        AddStopPointRequest Request=new AddStopPointRequest();
        Request.setTourId(id+"");
        List<StopPointForTour> list=new ArrayList<>();
        list.add(itemList.get(index));
        Request.setStopPoints(list);

        Call<AddStopPointResponse> Call = userService.addStopPoint(Request,token);

        Call.enqueue(new Callback<AddStopPointResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AddStopPointResponse> call, Response<AddStopPointResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(TourDetail.this, "Update stop point successfully",
                            Toast.LENGTH_LONG).show();
                    //after editing this stop point, the form will close and list view of stop points will be reloaded
                    loadListStopPoints(id);
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TourDetail.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(TourDetail.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<AddStopPointResponse> call, Throwable t) {
                Toast.makeText(TourDetail.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteStopPoint(int index) {
        int idStop =  Integer.valueOf(itemList.get(index).getId());
        Call<JSONObject> call = userService.removeStopPoint(idStop, token);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TourDetail.this, "Delete Stop Point Success", Toast.LENGTH_LONG).show();
                    //after deleting this stop point, the form will close and list view of stop points will be reloaded
                    loadListStopPoints(id);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TourDetail.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(TourDetail.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }


    // update tour khi bấm nút save
    private void updateTour(int status) {
        CreateTourRequest request = new CreateTourRequest();
        request.setName(((EditText)findViewById(R.id.editTextName)).getText().toString());
        request.setAdults(Integer.parseInt(((EditText)findViewById(R.id.editTextInputAdults)).getText().toString()));
        request.setChilds(Integer.parseInt(((EditText)findViewById(R.id.editTextInputChilds)).getText().toString()));
        request.setMaxCost(Integer.parseInt(((EditText)findViewById(R.id.editTextMaxCost)).getText().toString()));
        request.setMinCost(Integer.parseInt(((EditText)findViewById(R.id.editTextMinCost)).getText().toString()));
        request.setPrivate(isPrivate);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setId(id);
        if(status!=-2)
            request.setStatus(status);

        Call<JSONObject> call = userService.updateTour(request,token);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if(response.isSuccessful()){
                    Log.d("List Stop Point", "onResponse: ");
                    Toast.makeText(TourDetail.this,"Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TourDetail.this, HistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TourDetail.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(TourDetail.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d("List Stop Point", t.getMessage());

            }
        });
    }

    public void loadListStopPoints(int tourId){
        //load token from shared preferences


        Call<TourInforResponse> call = userService.getTourDetail(tourId,token);

        call.enqueue(new Callback<TourInforResponse>() {
            @Override
            public void onResponse(Call<TourInforResponse> call, Response<TourInforResponse> response) {
                if(response.isSuccessful()){
                    itemList.clear();

                    isPrivate=response.body().isPrivate();

                    if (isPrivate)
                        ((CheckBox)findViewById(R.id.checkBoxIsPrivate)).setChecked(true);
                    else
                        ((CheckBox)findViewById(R.id.checkBoxIsPrivate)).setChecked(false);

                    for (int i=0; i<response.body().getStopPoints().size(); i++)
                        itemList.add(response.body().getStopPoints().get(i));

                    adapter.notifyDataSetChanged();
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TourDetail.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(TourDetail.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TourInforResponse> call, Throwable t) {
                Log.d("List Stop Point", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_for_tour_of_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.track:
                //call tracking positions screen
                return true;
            case R.id.chat:
                //call chatting screen
                return true;
            case R.id.member:
                Intent intent1 = new Intent(TourDetail.this, MemberActivity.class);
                intent1.putExtra("tourId", id);
                intent1.putExtra("token", token);
                startActivity(intent1);
                return true;
            case R.id.rate:
                Intent intent = new Intent(TourDetail.this, ReviewTourActivity.class);
                intent.putExtra("tourId", id);
                intent.putExtra("token", token);
                startActivity(intent);
                return true;
            case R.id.comment:
                Intent intent2 = new Intent(TourDetail.this, CommentActivity.class);
                intent2.putExtra("tourId", id);
                intent2.putExtra("token", token);
                Log.d("TourDetail", "onOptionsItemSelected: "+token);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static String getDate(int day, int month, int year){
        String result="";
        if (day<10)
            result+="0"+day+"/";
        else
            result+=day+"/";
        if (month<10)
            result+="0"+month+"/";
        else
            result+=month+"/";
        result+=year;

        return result;
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            month+=1;
            if (flagEndDate==1 || flagStartDate==1){
                //date picker for tour
                if (flagStartDate==1){
                    startDay=day;
                    startMonth=month;
                    startYear=year;
                    String strDate = getDate(startDay,startMonth,startYear);
                    textViewStartDate.setText(strDate);
                    flagStartDate=0;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date = sdf.parse(strDate);
                        startDate=date.getTime();
                    }
                    catch(Exception e) {
                        System.out.println(e);
                    }
                }
                else {
                    endDay=day;
                    endMonth=month;
                    endYear=year;
                    String strDate = getDate(endDay,endMonth,endYear);
                    textViewEndDate.setText(strDate);
                    flagEndDate=0;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date = sdf.parse(strDate);
                        endDate=date.getTime();
                    }
                    catch(Exception e) {
                        System.out.println(e);
                    }
                }
            } else {
                //date picker for stop point
                if (flagArriveDatePicker){
                    startDay=day;
                    startMonth=month;
                    startYear=year;
                    String strDate = getDate(startDay,startMonth,startYear);
                    textViewArriveDate.setText(strDate);
                    flagArriveDatePicker=false;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date = sdf.parse(strDate);
                        itemList.get(currentStopPointId).setArrivalAt(date.getTime());
                    }
                    catch(Exception e) {
                        System.out.println(e);
                    }
                }
                else {
                    endDay=day;
                    endMonth=month;
                    endYear=year;
                    String strDate = getDate(endDay,endMonth,endYear);
                    textViewLeaveDate.setText(strDate);
                    flagLeaveDatePicker=false;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date = sdf.parse(strDate);
                        itemList.get(currentStopPointId).setLeaveAt(date.getTime());
                    }
                    catch(Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
    }
}