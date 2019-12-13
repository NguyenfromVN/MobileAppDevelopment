package com.example.projectapplication.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.projectapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class DetailStopPointActivity extends AppCompatActivity {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stop_point);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        setUpViewPaper(viewPager);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPaper(ViewPager viewPaper){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        DetailFragment detail = new DetailFragment();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        detail.setArguments(bundle);
        adapter.addFragment(detail, "Detail");
        adapter.addFragment(new ReviewFragment(), "Reviews");
        viewPager.setAdapter(adapter);
    }

}