package com.example.travelapp.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.model.OTPRequest;
import com.example.travelapp.model.OTPResponse;
import com.example.travelapp.model.VerifyOTPRequest;
import com.example.travelapp.network.MyAPIClient;
import com.example.travelapp.network.UserService;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassActivity extends AppCompatActivity {

    private Button btnSendOTP;
    private EditText email;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        btnSendOTP = (Button)findViewById(R.id.btnFg);

        email = (EditText)findViewById(R.id.edtEmailFg);

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP();
            }
        });


    }

    private void sendOTP() {



        boolean cancel = false;
        if(email.getText().toString().length()==0)
        {
            email.setError("Nhập vào email");
            cancel = true;
        }else email.setError(null);
        if(cancel==false){
        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        OTPRequest request = new OTPRequest();

        request.setType("email");
        request.setValue(email.getText().toString());

        Call<OTPResponse> call = userService.sendOTP(request);
        call.enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                if(response.isSuccessful()){
                    displayAlertDialog(response.body().getUserId());
                    Toast.makeText(ForgotPassActivity.this, "Success", Toast.LENGTH_LONG).show();


                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ForgotPassActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ForgotPassActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {
                Log.d("FGPass", t.getMessage());

            }
        });}

    }

    private void displayAlertDialog(int Id) {

        boolean cancel = false;
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_verify, null);

        final int userId = Id;
        final EditText verifyCode = (EditText)alertLayout.findViewById(R.id.emailVerify);
        final EditText newPass = (EditText)alertLayout.findViewById(R.id.newPassVerify);



        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirm Verify Code");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                VerifyOTPRequest requestOTP = new VerifyOTPRequest();
                requestOTP.setUserId(userId);
                requestOTP.setNewPass(newPass.getText().toString());
                requestOTP.setVerifyCode(verifyCode.getText().toString());

                Call<JSONObject> call = userService.verifyOTP(requestOTP);

                call.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if(response.isSuccessful()){
                            Log.d("Success", "onResponse: "+response.body().toString());
                            Intent intent = new Intent(ForgotPassActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(ForgotPassActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(ForgotPassActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        Log.d("VerifyOTP", t.getMessage());

                    }
                });

            }
        });

        alert.create();
        alert.show();
    }
}
