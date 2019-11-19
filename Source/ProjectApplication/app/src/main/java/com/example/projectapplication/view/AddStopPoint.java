package com.example.projectapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import java.util.ArrayList;
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
    private boolean flagStart=false;
    private boolean flagEnd=false;
    private String token;
    private UserService userService;
    private String TAG="AddStopPoint";

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

        // Add a HCMUS 2 marker
        LatLng hcmus2 = new LatLng(10.875801, 106.799116);
        mMap.addMarker(new MarkerOptions().position(hcmus2));

        // Add a dormitory marker
        LatLng dom = new LatLng(10.883057, 106.781703);
        mMap.addMarker(new MarkerOptions().position(dom));

        // Add a HCMUS marker
        LatLng hcmus = new LatLng(10.762917, 106.682155);
        mMap.addMarker(new MarkerOptions().position(hcmus));

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

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //show pop up when clicking on marker
                LatLng position=marker.getPosition();
                Toast.makeText(AddStopPoint.this, "Position: "+position.latitude+
                        ", "+position.longitude, Toast.LENGTH_LONG).show();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,15),1000,null);

                VisibleRegion visibleRegion=mMap.getProjection().getVisibleRegion();

                LatLng nearLeft=visibleRegion.nearLeft;
                LatLng nearRight=visibleRegion.nearRight;
                LatLng farLeft=visibleRegion.farLeft;
                LatLng farRight=visibleRegion.farRight;

                return true;
            }
        });
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
                    //add marker when view is changed
                    for (int i=0; i<listStopPoints.size(); i++){
                        LatLng position=new LatLng(listStopPoints.get(i).getLat(),listStopPoints.get(i).getLong());
                        mMap.addMarker(new MarkerOptions().position(position));
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