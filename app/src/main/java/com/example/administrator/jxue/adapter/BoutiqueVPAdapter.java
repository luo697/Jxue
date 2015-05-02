package com.example.administrator.jxue.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.jxue.fragment.BoutiqueVPFragment;

/**
 * Created by Administrator on 2015-5-2.
 */
public class BoutiqueVPAdapter extends FragmentPagerAdapter{
    public BoutiqueVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return BoutiqueVPFragment.getBoutiqueVPFragment(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
