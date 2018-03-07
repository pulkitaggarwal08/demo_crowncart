package com.innovative.crownkart.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.innovative.crownkart.activities.MyOrdersActivity;
import com.innovative.crownkart.fragments.CategoryCompleteItemsFragment;
import com.innovative.crownkart.fragments.HomeFragment;

/**
 * Created by Lavisha_User on 7/17/2017.
 */

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    int count;
    public TabsPagerAdapter(FragmentManager fm,int count) {
        super(fm);
        this.count=count;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
        return new HomeFragment();
        else if(position==1)
        return new MyOrdersActivity();
        else if(position==2)
        return new HomeFragment();
        else if(position==3)
        return new HomeFragment();
        else return new HomeFragment();
    }

    @Override
    public int getCount() {
        return count;
    }
}
