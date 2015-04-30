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


public class FoundFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager fondviewpager;
    private View[] views=new View[3];

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_found,container,false);
        init(view);
        return view;
    }

    private void init(View view) {
        fondviewpager = ((ViewPager) view.findViewById(R.id.foundViewpager));
        fondviewpager.setAdapter(new foundVPadapter(getFragmentManager()));
        fondviewpager.setCurrentItem(1);
        fondviewpager.setOnPageChangeListener(this);
        views[0] = view.findViewById(R.id.foundview1);
        views[1] = view.findViewById(R.id.foundview2);
        views[2] = view.findViewById(R.id.foundview3);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for(int i=0;i<views.length;i++){
            if(position==i){
                views[i].setVisibility(View.VISIBLE);
            }else{
                views[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
