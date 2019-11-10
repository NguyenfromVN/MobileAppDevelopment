package com.example.projectapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectapplication.R;
import com.example.projectapplication.model.RegisterRequest;
import com.example.projectapplication.network.MyAPIClient;
import com.example.projectapplication.network.UserService;

import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity {

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
        final String fullname = fullName.getText().toString();
        final String pass = fullName.getText().toString();
        final String email1 = fullName.getText().toString();
        final String add1 = fullName.getText().toString();
        final String dob1 = fullName.getText().toString();
        final String phone1 = fullName.getText().toString();
        final String gender1 = fullName.getText().toString();
        int gender2 = Integer.parseInt(gender1);

        final RegisterRequest request = new RegisterRequest();
        request.setAddress(add1);
        request.setDob(dob1);
        request.setEmail(email1);
        request.setGender(gender2);
        request.setFullName(fullname);
        request.setPassword(pass);
        request.setPhone(phone1);

       userService.register(request);



    }
}
