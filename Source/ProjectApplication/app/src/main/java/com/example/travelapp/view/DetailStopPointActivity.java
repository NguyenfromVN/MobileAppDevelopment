package com.example.travelapp.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.travelapp.R;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


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
        ReviewFragment reviewFragment= new ReviewFragment();
        reviewFragment.setArguments(bundle);
        adapter.addFragment(detail, "Detail");
        adapter.addFragment(reviewFragment, "Reviews");
        viewPager.setAdapter(adapter);
    }

}