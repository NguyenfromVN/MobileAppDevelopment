package com.example.projectapplication.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapplication.R;
import com.example.projectapplication.manager.MyApplication;
import com.example.projectapplication.model.CoordinateSet;
import com.example.projectapplication.model.LatLong;
import com.example.projectapplication.model.LoadListStopPointRequest;
import com.example.projectapplication.model.LoadListStopPointResponse;
import com.example.projectapplication.model.StopPoint;
import com.example.projectapplication.model.StopPointForTour;
import com.example.projectapplication.network.MyAPIClient;
import com.example.projectapplication.network.UserService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStopPoint extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String name;
    private long startDate;
    private long endDate;
    private int adults;
    private int childs;
    private int minCost;
    private int maxCost;
    private boolean isPrivate;
    private String token;
    private UserService userService;
    private String TAG="AddStopPoint";
    private List<StopPointForTour> currentListStopPoints=new ArrayList<>();
    private List<StopPointForTour> finalListStopPoints=new ArrayList<>();
    private static int arriveDay,arriveMonth,arriveYear;
    private static int leaveDay,leaveMonth,leaveYear;
    private long leaveDate, arriveDate;
    private static boolean flagArriveDatePicker=false;
    private static boolean flagLeaveDatePicker=false;
    private static TextView textViewArriveDate;
    private static TextView textViewLeaveDate;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_stop_point);

        //get information for tour
        Intent activityThatCalled=getIntent();
        name=activityThatCalled.getExtras().getString("name");
        startDate=activityThatCalled.getExtras().getLong("startDate");
        endDate=activityThatCalled.getExtras().getLong("endDate");
        adults=activityThatCalled.getExtras().getInt("adults");
        childs=activityThatCalled.getExtras().getInt("childs");
        minCost=activityThatCalled.getExtras().getInt("minCost");
        maxCost=activityThatCalled.getExtras().getInt("maxCost");
        isPrivate=activityThatCalled.getExtras().getBoolean("isPrivate");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show list of current stop points
                Toast.makeText(AddStopPoint.this, "Empty Function", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //move camera to HCMC
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.823089,106.629577),10));

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                VisibleRegion visibleRegion=mMap.getProjection().getVisibleRegion();

                LatLng nearLeft=visibleRegion.nearLeft;
                LatLng nearRight=visibleRegion.nearRight;
                LatLng farLeft=visibleRegion.farLeft;
                LatLng farRight=visibleRegion.farRight;

                loadAvailableStopPoints(nearLeft,farLeft,nearRight,farRight);
            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                //prevent lag when dragging on map
                mMap.clear();
            }
        });

        //show pop up when clicking on marker
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //get id of selected marker in currentListStopPoints
                int id=Integer.valueOf(marker.getTag().toString());

                //create form for selected stop point
                createAddStopPointForm(id);

                return true;
            }
        });
    }

    //create an alert dialog as a form for user to fill up
    private void createAddStopPointForm(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stop point information");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_layout, null);

        builder.setView(customLayout);

        // add OK button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //convert date to number
                String strDate = getDate(arriveDay,arriveMonth,arriveYear);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = sdf.parse(strDate);
                    arriveDate=date.getTime();
                }
                catch(Exception e) {
                    System.out.println(e);
                }
                strDate = getDate(leaveDay,leaveMonth,leaveYear);
                try {
                    Date date = sdf.parse(strDate);
                    leaveDate=date.getTime();
                }
                catch(Exception e) {
                    System.out.println(e);
                }

                StopPointForTour tmp=new StopPointForTour();
                tmp.setName(currentListStopPoints.get(id).getName());
                tmp.setServiceTypeId((currentListStopPoints.get(id).getServiceTypeId()));
                tmp.setAddress(currentListStopPoints.get(id).getAddress());
                tmp.setProvinceId(currentListStopPoints.get(id).getProvinceId());
                tmp.setMinCost(currentListStopPoints.get(id).getMinCost());
                tmp.setMaxCost(currentListStopPoints.get(id).getMaxCost());
                tmp.setArrivalAt(arriveDate);
                tmp.setLeaveAt(leaveDate);
                tmp.setLat(currentListStopPoints.get(id).getLat());
                tmp.setLong(currentListStopPoints.get(id).getLong());
                finalListStopPoints.add(tmp);

                //move camera focus to selected marker
                LatLng position=new LatLng(currentListStopPoints.get(id).getLat(),
                        currentListStopPoints.get(id).getLong());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,15),1000,null);
            }
        });

        //add Cancel button
        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddStopPoint.this, "New stop point was cancelled",
                        Toast.LENGTH_LONG).show();

                //move camera focus to selected marker
                LatLng position=new LatLng(currentListStopPoints.get(id).getLat(),
                        currentListStopPoints.get(id).getLong());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,15),1000,null);
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

        ((EditText)dialog.findViewById(R.id.editTextStopPointName)).setText(currentListStopPoints.get(id).getName());
        String tmp="";
        switch (currentListStopPoints.get(id).getServiceTypeId()){
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
        ((EditText)dialog.findViewById(R.id.editTextAddress)).setText(currentListStopPoints.get(id).getAddress());
        ((EditText)dialog.findViewById(R.id.editTextProvince)).setText(
                listProvinces[currentListStopPoints.get(id).getProvinceId()]);
        ((EditText)dialog.findViewById(R.id.editTextMinCostStopPoint)).setText(String.valueOf(minCost));
        ((EditText)dialog.findViewById(R.id.editTextMaxCostStopPoint)).setText(String.valueOf(maxCost));

        textViewArriveDate=dialog.findViewById(R.id.textViewArriveAt);
        textViewLeaveDate=dialog.findViewById(R.id.textViewLeaveAt);
    }

    private String getDate(int day, int month, int year){
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
            if (flagArriveDatePicker){
                arriveDay=day;
                arriveMonth=month;
                arriveYear=year;
                String str=arriveDay+"/"+arriveMonth+"/"+arriveYear;
                textViewArriveDate.setText(str);
                flagArriveDatePicker=false;
            }
            else {
                leaveDay=day;
                leaveMonth=month;
                leaveYear=year;
                String str=leaveDay+"/"+leaveMonth+"/"+leaveYear;
                textViewLeaveDate.setText(str);
                flagLeaveDatePicker=false;
            }
        }
    }

    public void loadAvailableStopPoints(LatLng nearLeft,LatLng farLeft,LatLng nearRight,LatLng farRight){
        //load token from shared preferences
        MyApplication app = (MyApplication) AddStopPoint.this.getApplication();
        token=app.loadToken();

        CoordinateSet coordinateSet=new CoordinateSet();
        List<CoordinateSet> coordList=new ArrayList<>();

        List<LatLong> tmp=new ArrayList<>();

        tmp.add(new LatLong(nearLeft));
        tmp.add(new LatLong(farLeft));

        coordinateSet.setListLatLong(tmp);
        coordList.add(coordinateSet);

        tmp.clear();
        tmp.add(new LatLong(farRight));
        tmp.add(new LatLong(nearRight));

        coordinateSet.setListLatLong(tmp);
        coordList.add(coordinateSet);

        //load stop point request
        LoadListStopPointRequest request=new LoadListStopPointRequest();
        request.setHasOneCoordinate(false);
        request.setCoordList(coordList);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        Call<LoadListStopPointResponse> call = userService.loadListStopPoint(request,token);

        call.enqueue(new Callback<LoadListStopPointResponse>() {
            @Override
            public void onResponse(Call<LoadListStopPointResponse> call, Response<LoadListStopPointResponse> response) {
                if(response.isSuccessful()) {
                    List<StopPoint> listStopPoints=response.body().getStopPoints();
                    currentListStopPoints.clear();
                    //add marker when view is changed
                    for (int i=0; i<listStopPoints.size(); i++){
                        LatLng position=new LatLng(listStopPoints.get(i).getLat(),
                                listStopPoints.get(i).getLong());
                        mMap.addMarker((new MarkerOptions().position(position))).setTag(i);

                        StopPointForTour tmp=new StopPointForTour();
                        tmp.setName(listStopPoints.get(i).getName());
                        tmp.setAddress(listStopPoints.get(i).getAddress());
                        tmp.setProvinceId(listStopPoints.get(i).getProvinceId());
                        tmp.setLat(listStopPoints.get(i).getLat());
                        tmp.setLong(listStopPoints.get(i).getLong());
                        tmp.setServiceTypeId(listStopPoints.get(i).getServiceTypeId());
                        currentListStopPoints.add(tmp);
                    }
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(AddStopPoint.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(AddStopPoint.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<LoadListStopPointResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}