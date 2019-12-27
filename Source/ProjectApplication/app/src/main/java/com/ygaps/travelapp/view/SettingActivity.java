package com.ygaps.travelapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.manager.MyApplication;
import com.ygaps.travelapp.network.MyAPIClient;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;


public class SettingActivity extends AppCompatActivity {
    TextView name, email, phone, dob, gender, add;
    ImageView avt;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);





        // Set display user infor
        name = (TextView)findViewById(R.id.txtName);
        phone = (TextView)findViewById(R.id.phoneSt);
        email = (TextView)findViewById(R.id.emailSt);
        dob = (TextView)findViewById(R.id.dobSt);
        gender = (TextView)findViewById(R.id.genderSt);
        add = (TextView)findViewById(R.id.addSt);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        name.setText(sharedPref.getString(getString(R.string.saved_name),null));
        phone.setText("Phone: "+sharedPref.getString(getString(R.string.saved_phone),null));
        email.setText("Email: "+sharedPref.getString(getString(R.string.saved_email),null));
        dob.setText("Day of birth: "+sharedPref.getString(getString(R.string.saved_dob), null));
        final int gender1 = sharedPref.getInt(getString(R.string.saved_gender),0);
        if(gender1==1)
           gender.setText("Gender: Male");
        else gender.setText("Gender: Female");
        add.setText("Address: "+sharedPref.getString(getString(R.string.saved_address),null) );

        // Pick avt in device
        avt = (ImageView)findViewById(R.id.imageAvt);
        avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pick from gallery
                pickFromGallery();
            }
        });


        if(sharedPref.getString(getString(R.string.saved_avt),null)!=null){
            String avt = sharedPref.getString(getString(R.string.saved_avt),null);
        // set Avt
        new DownloadImageTask((ImageView) findViewById(R.id.imageAvt))
                .execute(avt);
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navi);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.history:
                        Intent intent2 = new Intent(SettingActivity.this, HistoryActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        break;
                    case R.id.list:
                        Intent intent = new Intent(SettingActivity.this, ListTours.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.explore:
                        Intent intent1 = new Intent(SettingActivity.this, ExploreActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        break;
                    case R.id.noti:
                        Intent intent3 = new Intent(SettingActivity.this, NotificationTab.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
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
               intent.putExtra("name", name.getText().toString());
               intent.putExtra("dob", dob.getText().toString());
               intent.putExtra("gender", gender1);
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

    private void pickFromGallery() {
            Intent intent = new Intent(Intent.ACTION_PICK);

            intent.setType("image/*");

            String[] mimeType = {"image/jpeg","image/png"};

            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType);
            if(isStoragePermissionGranted()==true){
                startActivityForResult(intent, PICK_IMAGE);
            }

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case PICK_IMAGE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bm =  BitmapFactory.decodeFile(imgDecodableString);
                    try {
                         bm = decodeUri(this, selectedImage, 50);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    avt.setImageBitmap(bm);
                    break;

            }
    }

    // permission
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission","Permission is granted");
                return true;
            } else {

                Log.v("Permission","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permission","Permission is granted");
            return true;
        }
    }



        // Class download avt
        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }

        //resize image
    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth
                , height_tmp = o.outHeight;
        int scale = 1;

        while(true) {
            if(width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }



}
