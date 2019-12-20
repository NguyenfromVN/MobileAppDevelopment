package com.ygaps.travelapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();

    private final  List<String> fragmentName = new ArrayList<>();


    public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public SectionsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String fragmentN)
    {
        fragmentName.add(fragmentN);
        fragmentList.add(fragment);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentName.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
