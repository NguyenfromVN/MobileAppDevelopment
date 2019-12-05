package com.example.projectapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectapplication.R;
import com.example.projectapplication.manager.MyApplication;
import com.example.projectapplication.model.UpdatePasswordRequest;
import com.example.projectapplication.network.MyAPIClient;
import com.example.projectapplication.network.UserService;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePassActivity extends AppCompatActivity {

    private Button btnUpPass;

    private     EditText oldPass, newPass;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);

        btnUpPass = (Button)findViewById(R.id.btnPass);
        oldPass = (EditText)findViewById(R.id.edtCurrent);
        newPass =(EditText)findViewById(R.id.edtNew);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);
        MyApplication app = (MyApplication) UpdatePassActivity.this.getApplication();
        String token=app.loadToken();

        btnUpPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePasswordRequest request = new UpdatePasswordRequest();

                request.setCurrentPassword(oldPass.getText().toString());
                request.setNewPassword(newPass.getText().toString());
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int id = sharedPref.getInt(getString(R.string.saved_id), 0);
                request.setUserId(id);
                MyApplication app = (MyApplication) UpdatePassActivity.this.getApplication();
                String token=app.loadToken();

                Call<JSONObject> call = userService.updatePassword(token,request);
                call.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if(response.isSuccessful())
                        {
                        }
                        else{
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Log.d("EditProfile", "onResponse: "+jObjError.getString("message"));
                                Toast.makeText(UpdatePassActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(UpdatePassActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                Log.d("EditProfile", "onResponse: "+e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });

            }
        });
    }
}
