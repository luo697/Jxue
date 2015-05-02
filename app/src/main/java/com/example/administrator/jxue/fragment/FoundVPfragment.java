package com.example.administrator.jxue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.jxue.R;
import com.example.administrator.jxue.adapter.BoutiqueAdapter;
import com.example.administrator.jxue.data.Lists;

/**
 * Created by Administrator on 2015-4-30.
 */
public class FoundVPfragment extends Fragment{
    public static final String KEY="position";
    public static FoundVPfragment getFoundVPfragment(int position){
        FoundVPfragment fragment=new FoundVPfragment();
        Bundle bundle=new Bundle();
        bundle.putInt(KEY,position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=null;
        int position = getArguments().getInt(KEY);
        if(Lists.Jplist!=null){
            if (position==0){

            }
            if (position==1){
                view=inflater.inflate(R.layout.boutiquelist,container,false);
                ListView list = ((ListView) view.findViewById(R.id.boutique_list));
                list.setAdapter(new BoutiqueAdapter(getActivity(),Lists.Jplist));
            }
            if(position==2){

            }
        }else{

        }

        return view;
    }
}
