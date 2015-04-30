package com.example.administrator.jxue.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jxue.R;
import com.example.administrator.jxue.foundVPadapter;

/**
 * Created by Administrator on 2015-4-30.
 */
public class FoundFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager fondviewpager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_found,container,false);
        init(view);
        return view;
    }

    private void init(View view) {
        fondviewpager = ((ViewPager) view.findViewById(R.id.foundViewpager));
        fondviewpager.setAdapter(new foundVPadapter(getFragmentManager()));
        fondviewpager.setOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
