package com.example.projectapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectapplication.R;
import com.example.projectapplication.model.LoginRequest;
import com.example.projectapplication.model.RegisterRequest;
import com.example.projectapplication.model.RegisterResponse;
import com.example.projectapplication.network.MyAPIClient;
import com.example.projectapplication.network.UserService;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    public static String TAG  = "RegisterActivity";
    EditText fullName, dob,gender,add,password, email, phone;
    Button reg;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        //set up
        fullName = (EditText)findViewById(R.id.edtFullName);
        dob = (EditText)findViewById(R.id.edtDob);
        gender = (EditText)findViewById(R.id.edtGender);
        add = (EditText)findViewById(R.id.edtAdd);
        password = (EditText)findViewById(R.id.edtPass);
        email = (EditText)findViewById(R.id.edtEmail);
        phone = (EditText)findViewById(R.id.edtPhone);
        reg = (Button)findViewById(R.id.button);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReg();
            }
        });


    }

    private void setReg() {
        boolean cancel = false;

        // Kiểm tra input
        if(fullName.getText().toString().length()<=0)
        {
            fullName.setError("Vui lòng nhập tên");
            cancel = true;
        }
        else fullName.setError(null);

        if(email.getText().toString().length()<=0)
        {
            email.setError("Vui lòng nhập email");
            cancel = true;
        }
        else email.setError(null);

        if(dob.getText().toString().length()<=0)
        {
            dob.setError("Vui lòng nhập ngày sinh");
            cancel = true;
        }
        else dob.setError(null);

        if(gender.getText().toString().length()<=0 || (gender.getText().toString()=="2" && gender.getText().toString()!="1"))
        {
            gender.setError("Vui lòng nhập đúng giới tính theo yêu cầu");
            cancel = true;
        }
        else gender.setError(null);

        if(gender.getText().toString().length()<=0 || (gender.getText().toString()=="2" && gender.getText().toString()!="1"))
        {
            gender.setError("Vui lòng nhập đúng giới tính theo yêu cầu");
            cancel = true;
        }
        else gender.setError(null);

        if(gender.getText().toString().length()<=0 || (gender.getText().toString()=="2" && gender.getText().toString()!="1"))
        {
            gender.setError("Vui lòng nhập đúng giới tính theo yêu cầu");
            cancel = true;
        }
        else gender.setError(null);

        if(phone.getText().toString().length()<=0 )
        {
            phone.setError("Vui lòng nhập số điện thoại");
            cancel = true;
        }
        else phone.setError(null);

        if(password.getText().toString().length()<=0 )
        {
            password.setError("Vui lòng nhập password");
            cancel = true;
        }
        else password.setError(null);

        if(add.getText().toString().length()<=0 )
        {
            add.setError("Vui lòng nhập địa chỉ");
            cancel = true;
        }
        else add.setError(null);

        if(cancel==false) {
        final String fullname = fullName.getText().toString();
        final String pass = password.getText().toString();
        final String email1 = email.getText().toString();
        final String add1 = add.getText().toString();
        final String dob1 = dob.getText().toString();
        final String phone1 = phone.getText().toString();
        final String gender1 = gender.getText().toString();
        final int gender2 = Integer.parseInt(gender1);


        final RegisterRequest request = new RegisterRequest();
        request.setAddress(add1);
        request.setDob(dob1);
        request.setEmail(email1);
        request.setGender(gender2);
        request.setFullName(fullname);
        request.setPassword(pass);
        request.setPhone(phone1);


            Call<RegisterResponse> call = userService.register(request);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.code() == 200) {

                        Log.d(TAG, "onResponse: success");
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        RegisterActivity.this.finish();

                    } else {
                        try {

                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            JSONArray jsonArray = (JSONArray) jObjError.get("message");
                            String str = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = (JSONObject) jsonArray.get(i);
                                str += object.getString("msg") + "";
                            }
                            Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        }



    }
}
