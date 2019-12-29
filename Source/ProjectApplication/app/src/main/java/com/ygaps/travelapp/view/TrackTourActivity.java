package com.ygaps.travelapp.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.CustomListAdapterForTextNotification;
import com.ygaps.travelapp.model.AddStopPointResponse;
import com.ygaps.travelapp.model.CreateNotificationOnRoad;
import com.ygaps.travelapp.model.CreateTextNotification;
import com.ygaps.travelapp.model.GetLocationRequest;
import com.ygaps.travelapp.model.GetLocationResponse;
import com.ygaps.travelapp.model.LoadSpeedLimitNotificationResponse;
import com.ygaps.travelapp.model.LoadTextNotificationResponse;
import com.ygaps.travelapp.model.Member;
import com.ygaps.travelapp.model.SpeedLimitNotification;
import com.ygaps.travelapp.model.StopPointForTour;
import com.ygaps.travelapp.model.TextNotification;
import com.ygaps.travelapp.model.TourInforResponse;
import com.ygaps.travelapp.network.LocationService;
import com.ygaps.travelapp.network.MyAPIClient;
import com.ygaps.travelapp.network.UserService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackTourActivity extends AppCompatActivity implements OnMapReadyCallback {

    private int count=-1;
    private LatLng currentPosition;
    private GoogleMap mMap;
    private boolean isMoving=false;
    private Intent serviceIntent;
    private int id;
    private String token;
    private int userId;
    private UserService userService;
    private List<GetLocationResponse> list=new ArrayList<>();
    private List<Member> itemList=new ArrayList<>();
    private List<StopPointForTour> listStopPoints=new ArrayList<>();
    private List<SpeedLimitNotification> listSpeedLimit=new ArrayList<>();
    private ArrayAdapter<TextNotification> adapter;
    private ListView listView;
    private List<TextNotification> notiList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_tour);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        //get tour id and login token
        Intent activityThatCalled=getIntent();
        token=activityThatCalled.getExtras().getString("token");
        id=activityThatCalled.getExtras().getInt("id");

        //get user id
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId=sharedPref.getInt("id",0);

        //load member list
        loadMember();

        //start track location service
        serviceIntent=new Intent(getApplicationContext(), LocationService.class);
        startService(serviceIntent);

        //receive data from service
        LocalBroadcastManager.getInstance(TrackTourActivity.this).registerReceiver(
                mMessageReceiver, new IntentFilter("GPSLocationUpdates"));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //set event handler for focus button
        findViewById(R.id.fabFocus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,15),1000,null);
            }
        });

        //event handler for text notification button
        findViewById(R.id.fabText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopupForTextNotification();
            }
        });
    }

    public void createPopupForTextNotification(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Text notification");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_layout_for_text_notification, null);

        builder.setView(customLayout);

        // add OK button
        builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CreateTextNotification request=new CreateTextNotification();
                request.setUserId(userId);
                request.setTourId(id);
                request.setNoti(((EditText)customLayout.findViewById(R.id.editTextText)).getText().toString());

                Call<JSONObject> Call = userService.createTextNotification(token,request);

                Call.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(retrofit2.Call<JSONObject> call, Response<JSONObject> response) {
                        if(response.isSuccessful()) {
                            //do nothing
                        }
                        else{
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(TrackTourActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(TrackTourActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<JSONObject> call, Throwable t) {
                        Toast.makeText(TrackTourActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //add Cancel button
        builder.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        //set view for list view
        listView=dialog.findViewById(R.id.listView);
        adapter=new CustomListAdapterForTextNotification(this,
                R.layout.custom_listview_item_text_notification,notiList);
        listView.setAdapter(adapter);

        //load text notifications
        Call<LoadTextNotificationResponse> Call = userService.loadTextNotification(id,1,1000,token);

        Call.enqueue(new Callback<LoadTextNotificationResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoadTextNotificationResponse> call, Response<LoadTextNotificationResponse> response) {
                if(response.isSuccessful()) {
                    notiList.clear();
                    notiList.addAll(response.body().getNotiList());
                    adapter.notifyDataSetChanged();
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TrackTourActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(TrackTourActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<LoadTextNotificationResponse> call, Throwable t) {
                Toast.makeText(TrackTourActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //move camera to HCMC
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.823089,106.629577),10));

        //clear all marker when camera starts moving
        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                //prevent lag when dragging on map
                mMap.clear();
                isMoving=true;
            }
        });

        //set isMoving to false after camera stops moving
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                isMoving=false;
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(TrackTourActivity.this, marker.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        //long click handler for creating speed limit notification
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                createSpeedLimitForm(latLng);
            }
        });
    }

    private void createSpeedLimitForm(final LatLng position){
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Speed limit notification");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_layout_for_speed_limit, null);
        builder.setView(customLayout);

        // add SEND button
        builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int speed=Integer.valueOf(((TextView)customLayout.findViewById(R.id.editTextSpeedLimit)).getText().toString());

                CreateNotificationOnRoad request=new CreateNotificationOnRoad();
                request.setLat(position.latitude);
                request.setLong(position.longitude);
                request.setTourId(id);
                request.setUserId(userId);
                request.setNotificationType(3);
                request.setSpeed(speed);

                Call<JSONObject> Call = userService.createSpeedLimitNotification(token,request);

                Call.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(retrofit2.Call<JSONObject> call, Response<JSONObject> response) {
                        if(response.isSuccessful()) {
                            //do nothing
                        }
                        else{
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(TrackTourActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(TrackTourActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<JSONObject> call, Throwable t) {
                        Toast.makeText(TrackTourActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //add CLOSE button
        builder.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            double Lat=intent.getDoubleExtra("lat",0.0);
            double Long=intent.getDoubleExtra("long",0.0);
            currentPosition =new LatLng(Lat,Long);

            //your location will be renewed after 1s
            //others location will be renewed after 10s
            count=(count+1)%10;

            if (count==0){
                //update for location
                requestForLocation();

                //update for speed limit notification
                loadSpeedLimitNotifications();
            }

            mMap.clear();
            if (!isMoving){
                int height = 100;
                int width = 94;
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.person);
                BitmapDrawable bitmapdraw2=(BitmapDrawable)getResources().getDrawable(R.drawable.you);
                BitmapDrawable bitmapdraw3=(BitmapDrawable)getResources().getDrawable(R.drawable.warning);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap b2=bitmapdraw2.getBitmap();
                Bitmap b3=bitmapdraw3.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                Bitmap smallMarker2 = Bitmap.createScaledBitmap(b2, width, height, false);
                Bitmap waringMarker = Bitmap.createScaledBitmap(b3, 50, 50, false);

                //add markers for stop points
                for (int i=0; i<listStopPoints.size(); i++)
                    mMap.addMarker((new MarkerOptions()
                            .zIndex(1)
                            .title("Stop point "+listStopPoints.get(i).getName())
                            .position(new LatLng(listStopPoints.get(i).getLat(),
                            listStopPoints.get(i).getLong()))));

                //add others marker to map
                for (int i=0; i<list.size(); i++)
                    for (int j=0; j<itemList.size(); j++)
                        if (list.get(i).getId()==itemList.get(j).getId() &&
                                list.get(i).getId()!=userId)
                            mMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i)
                                    .getLat(),list.get(i).getLong()))
                                    .title("User "+itemList.get(j).getName())
                                    .zIndex(2)
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                //add your marker
                mMap.addMarker(new MarkerOptions().position(currentPosition)
                        .title("You")
                        .zIndex(3)
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker2)));

                //add marker for speed limit
                for (int i=0; i<listSpeedLimit.size(); i++)
                    mMap.addMarker((new MarkerOptions()
                            .zIndex(0)
                            .icon(BitmapDescriptorFactory.fromBitmap(waringMarker))
                            .title("Speed limit at "+listSpeedLimit.get(i).getSpeed()+" km/h")
                            .position(new LatLng(listSpeedLimit.get(i).getLat(),
                                    listSpeedLimit.get(i).getLong()))));
            }
        }
    };

    public void loadSpeedLimitNotifications(){

        Call<LoadSpeedLimitNotificationResponse> Call = userService.loadSpeedLimitNotification(id,1,1000,token);

        Call.enqueue(new Callback<LoadSpeedLimitNotificationResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoadSpeedLimitNotificationResponse> call, Response<LoadSpeedLimitNotificationResponse> response) {
                if(response.isSuccessful()) {
                    listSpeedLimit.clear();
                    listSpeedLimit.addAll(response.body().getNotiList());
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TrackTourActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(TrackTourActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<LoadSpeedLimitNotificationResponse> call, Throwable t) {
                Toast.makeText(TrackTourActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void requestForLocation(){

        GetLocationRequest request=new GetLocationRequest();
        request.setUserId(userId);
        request.setTourId(id);
        request.setLat(currentPosition.latitude);
        request.setLong(currentPosition.longitude);

        Call<List<GetLocationResponse>> Call = userService.getLocation(token,request);

        Call.enqueue(new Callback<List<GetLocationResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<GetLocationResponse>> call, Response<List<GetLocationResponse>> response) {
                if(response.isSuccessful()) {
                    list=new ArrayList<>();
                    for (int i=0; i<response.body().size(); i++){
                        GetLocationResponse x=response.body().get(i);
                        GetLocationResponse tmp=new GetLocationResponse();
                        tmp.setId(x.getId());
                        tmp.setLat(x.getLat());
                        tmp.setLong(x.getLong());
                        list.add(tmp);
                    }
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TrackTourActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(TrackTourActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<GetLocationResponse>> call, Throwable t) {
                Toast.makeText(TrackTourActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadMember() {
        Call<TourInforResponse> call = userService.getTourDetail(id, token);
        call.enqueue(new Callback<TourInforResponse>() {
            @Override
            public void onResponse(Call<TourInforResponse> call, Response<TourInforResponse> response) {
                if(response.isSuccessful()){
                    itemList.clear();
                    List<Member> listMems=response.body().getMembers();
                    for (int i=0; i<listMems.size(); i++){
                        itemList.add(listMems.get(i));
                    }

                    listStopPoints.addAll(response.body().getStopPoints());
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TrackTourActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(TrackTourActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }
}
