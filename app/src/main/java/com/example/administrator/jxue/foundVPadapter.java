package com.example.administrator.jxue;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.jxue.fragment.FoundVPfragment;

/**
 * Created by Administrator on 2015-4-30.
 */
public class foundVPadapter extends FragmentPagerAdapter{
    public foundVPadapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FoundVPfragment.getFoundVPfragment(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
