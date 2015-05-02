package com.example.administrator.jxue.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jxue.BitmapHelper;
import com.example.administrator.jxue.ImageViewWithWidth;
import com.example.administrator.jxue.R;
import com.example.administrator.jxue.bean.boutiquevpdata;
import com.example.administrator.jxue.data.Lists;

/**
 * Created by Administrator on 2015-5-2.
 */
public class BoutiqueVPFragment extends Fragment{
    public static final String KEY="position";
    public static BoutiqueVPFragment getBoutiqueVPFragment(int position){
        BoutiqueVPFragment fragment=new BoutiqueVPFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(KEY,position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int position=getArguments().getInt(KEY);
        boutiquevpdata bo=Lists.boutiquevps.get(position);
        View view=inflater.inflate(R.layout.boutiquevpitem,container,false);
        ImageViewWithWidth imageView = (ImageViewWithWidth) view.findViewById(R.id.boutique_vpimage);
        TextView type = (TextView) view.findViewById(R.id.boutique_tvtype);
        TextView title = (TextView) view.findViewById(R.id.boutique_tvtitle);
        type.setVisibility(View.GONE);
        BitmapHelper.getUtils().display(imageView,bo.getImageUrl());
        if(bo.getMarks().size()!=0){
            type.setVisibility(View.VISIBLE);
            type.setText("[ "+bo.getMarks().get(0).getTitle()+" ]");
        }
        title.setText(bo.getTitle());

        return view;
    }
}
