package com.example.projectapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectapplication.R;

public class SearchExploreActivity extends AppCompatActivity {

    private Button btnSearch;
    private EditText txtSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_explore);
        loadSearchStopPoint();
    }

    private void loadSearchStopPoint() {
    }
}
