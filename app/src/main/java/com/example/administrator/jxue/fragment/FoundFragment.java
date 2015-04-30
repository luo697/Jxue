package com.example.administrator.jxue.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.jxue.R;
import com.example.administrator.jxue.adapter.foundVPadapter;
import com.example.administrator.jxue.bean.Assortment;
import com.example.administrator.jxue.bean.Cous;
import com.example.administrator.jxue.bean.Marks;
import com.example.administrator.jxue.bean.Rank;
import com.example.administrator.jxue.data.Lists;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FoundFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private HttpUtils utils;
    private Marks marks;
    private Cous cous;
    private Assortment assortment;
    private Rank rank;
    private ViewPager fondviewpager;
    private View[] views=new View[3];
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    fondviewpager.setAdapter(new foundVPadapter(getFragmentManager()));
                    break;
            }
        }
    };
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_found,container,false);
        fondviewpager = ((ViewPager) view.findViewById(R.id.foundViewpager));
        getJsonData();
        init(view);
        return view;
    }

    private void init(View view) {
        fondviewpager = ((ViewPager) view.findViewById(R.id.foundViewpager));
        fondviewpager.setCurrentItem(1);
        fondviewpager.setOnPageChangeListener(this);
        views[0] = view.findViewById(R.id.foundview1);
        views[1] = view.findViewById(R.id.foundview2);
        views[2] = view.findViewById(R.id.foundview3);


    }

   //请求数据
    public void getJsonData() {
        String path = "http://course.jaxus.cn/api/category//53e19aea81cc22417c36c1f0/courses?platform=1&channel=xiaomi&start=0&end=20&version=2";
        utils = new HttpUtils();
        Lists.Jplist = new ArrayList<Cous>();
        Lists.markss = new ArrayList<Marks>();
        Lists.ranks=new ArrayList<Rank>();
        Lists.assortments = new ArrayList<Assortment>();
        Lists.markseses=new ArrayList<Marks>();
        assortment=null;
        cous = null;
        marks = null;
        rank=null;
        utils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> stringResponseInfo) {
                //精品
                try {
                    Log.d("----------aaaa----------", stringResponseInfo.result);
                    JSONObject js = new JSONObject(stringResponseInfo.result);
                    JSONArray arr = js.getJSONArray("courses");

                    for (int i = 0; i < arr.length(); i++) {
                        cous = new Cous();
                        JSONObject json1 = arr.getJSONObject(i);
                        cous.setBgUrl(json1.getString("bgUrl"));
                        cous.setEnrollNum(json1.getInt("enrollNum"));
                        cous.setIconUrl(json1.getString("iconUrl"));

                        JSONArray arr2 = json1.getJSONArray("marks");
                        for (int j = 0; j < arr2.length(); j++) {
                            marks = new Marks();
                            JSONObject obj1 = arr2.getJSONObject(j);
                            marks.setColor(obj1.getString("color"));
                            marks.setImageUrl(obj1.getString("imageUrl"));
                            marks.setTitle(obj1.getString("title"));
                            Lists.markss.add(marks);
                        }
                        cous.setMarks(Lists.markss);
                        cous.setPrice(json1.getDouble("price"));
                        cous.setProviderName(json1.getString("providerName"));
                        cous.setRate(json1.getInt("rate"));
                        cous.setTitle(json1.getString("title"));
                        Lists.Jplist.add(cous);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(),"连接失败请检查网络连接1",Toast.LENGTH_SHORT).show();
            }
        });
        //分类

        String path2 = "http://course.jaxus.cn/api/category/subcategories?channel=xiaomi";
        utils.send(HttpRequest.HttpMethod.GET, path2, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                utils = new HttpUtils();
                try {
                    JSONObject json1 = new JSONObject(responseInfo.result);
                    JSONArray arr = json1.getJSONArray("subcategories");
                    for (int j = 0; j < arr.length(); j++) {
                        assortment = new Assortment();
                        JSONObject jon2 = arr.getJSONObject(j);
                        assortment.setIconUrl(jon2.getString("iconUrl"));
                        assortment.setName(jon2.getString("name"));
                        Lists.assortments.add(assortment);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(),"连接失败请检查网络连接2",Toast.LENGTH_SHORT).show();
            }
        });
        //排行
        String path3 =
                "http://course.jaxus.cn/api/category//53e19aea81cc22417c36c1f0/courses?platform=1&sort=enrollNum&channel=xiaomi&start=0&end=20&version=2";


        utils.send(HttpRequest.HttpMethod.GET,path3,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject js1 = new JSONObject(responseInfo.result);
                    JSONArray arr = js1.getJSONArray("courses");
                    for (int i = 0; i < arr.length(); i++) {
                        rank=new Rank();
                        JSONObject json1 = arr.getJSONObject(i);
                        rank.setBgUrl(json1.getString("bgUrl"));
                        rank.setEnrollNum(json1.getInt("enrollNum"));
                        rank.setIconUrl(json1.getString("iconUrl"));

                        JSONArray arr2 = json1.getJSONArray("marks");
                        for (int j = 0; j < arr2.length(); j++) {
                            marks = new Marks();
                            JSONObject obj1 = arr2.getJSONObject(j);
                            marks.setColor(obj1.getString("color"));
                            marks.setImageUrl(obj1.getString("imageUrl"));
                            marks.setTitle(obj1.getString("title"));
                            Lists.markseses.add(marks);
                        }
                        rank.setMarks(  Lists.markseses);
                        rank.setPrice(json1.getDouble("price"));
                        rank.setProviderName(json1.getString("providerName"));
                        rank.setRate(json1.getInt("rate"));
                        rank.setTitle(json1.getString("title"));
                        Lists.ranks.add(rank);
                    }
                    handler.sendEmptyMessage(0);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(),"连接失败请检查网络连接3",Toast.LENGTH_SHORT).show();
            }
        });


    }
    //大ViewPager的滑动监听
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
