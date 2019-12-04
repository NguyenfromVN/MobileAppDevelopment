package com.example.projectapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapplication.R;
import com.example.projectapplication.manager.MyApplication;
import com.example.projectapplication.model.UserInforResponse;
import com.example.projectapplication.network.MyAPIClient;
import com.example.projectapplication.network.UserService;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingActivity extends AppCompatActivity {
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        name = (TextView)findViewById(R.id.txtName);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        name.setText(sharedPref.getString(getString(R.string.saved_name),null));
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navi);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.list:
                        Intent intent = new Intent(SettingActivity.this, ListTours.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        break;
                }
                return false;
            }
        });

        Button edtProf = (Button)findViewById(R.id.edtpro);
        edtProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(SettingActivity.this, EditProfileActivity.class);
               startActivity(intent);
            }
        });

        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAPIClient.getInstance().setAccessToken(null);
                LoginManager.getInstance().logOut();


                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                long time = 0;
                editor.clear();

//                editor.putString(getString(R.string.saved_access_token), null);
//                editor.putLong(getString(R.string.saved_access_token_time), time);
//                editor.putString(getString(R.string.saved_name),null);
//                editor.putString(getString(R.string.saved_phone), null);
//                editor.putString(getString(R.string.saved_email), null);
//                editor.putString(getString(R.string.saved_dob), null);
//                editor.putLong(getString(R.string.saved_gender), -1);
//                editor.putString(getString(R.string.saved_address), null);
                editor.commit();

                MyApplication app = (MyApplication) SettingActivity.this.getApplication();
                app.setToken(null);
                Intent intent = new Intent(SettingActivity.this, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });

        Button btnPass = (Button)findViewById(R.id.btnpass);
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UpdatePassActivity.class);
                startActivity(intent);
            }
        });
    }



}
