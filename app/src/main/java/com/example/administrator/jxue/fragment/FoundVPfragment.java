package com.example.administrator.jxue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.jxue.HttpHelper;
import com.example.administrator.jxue.MyViewPager;
import com.example.administrator.jxue.R;
import com.example.administrator.jxue.ShowItemActivity;
import com.example.administrator.jxue.adapter.BoutiqueAdapter;
import com.example.administrator.jxue.adapter.BoutiqueVPAdapter;
import com.example.administrator.jxue.adapter.ClassifyAdapter;
import com.example.administrator.jxue.adapter.RankAdapter;
import com.example.administrator.jxue.bean.boutiquevpdata;
import com.example.administrator.jxue.data.Lists;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2015-4-30.
 */
public class FoundVPfragment extends Fragment implements AdapterView.OnItemClickListener {
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
                view = inflater.inflate(R.layout.classifylist, container, false);
                ListView list_classify = ((ListView) view.findViewById(R.id.classify_list));
                list_classify.setAdapter(new ClassifyAdapter(getActivity(), Lists.assortments));
            }
            if (position==1){
                view=inflater.inflate(R.layout.boutiquelist,container,false);
                ListView list_boutique = ((ListView) view.findViewById(R.id.boutique_list));
                list_boutique.setAdapter(new BoutiqueAdapter(getActivity(),Lists.Jplist));
                View view1=View.inflate(getActivity(),R.layout.boutiqueviewpager,null);
                ViewPager boutiqueVP= ((ViewPager) view1.findViewById(R.id.boutique_vp));
                boutiqueVP.setAdapter(new BoutiqueVPAdapter(getFragmentManager()));
                list_boutique.addHeaderView(view1);


//                list.addHeaderView(View);
            }
            if(position==2){
                view = inflater.inflate(R.layout.boutiquelist, container, false);
                ListView list_Ranking = ((ListView) view.findViewById(R.id.boutique_list));
                list_Ranking.setOnItemClickListener(this);
                list_Ranking.setAdapter(new RankAdapter(getActivity(), Lists.ranks));
            }
        }else{

        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        intent = new Intent(getActivity(), ShowItemActivity.class);
        startActivity(intent);
    }
}
