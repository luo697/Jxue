package com.example.administrator.jxue.fragment;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.jxue.HttpHelper;
import com.example.administrator.jxue.MyViewPager;
import com.example.administrator.jxue.R;
import com.example.administrator.jxue.ShowItemActivity;
import com.example.administrator.jxue.adapter.BoutiqueAdapter;
import com.example.administrator.jxue.adapter.BoutiqueVPAdapter;
import com.example.administrator.jxue.adapter.ClassifyAdapter;
import com.example.administrator.jxue.adapter.RankAdapter;
import com.example.administrator.jxue.bean.Cous;
import com.example.administrator.jxue.bean.Marks;
import com.example.administrator.jxue.bean.Rank;
import com.example.administrator.jxue.bean.boutiquevpdata;
import com.example.administrator.jxue.data.Lists;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-4-30.
 */
public class FoundVPfragment extends Fragment implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener {
    public static final String KEY = "position";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    boutiqueadapter.Add(Lists.Jplist);
                    //                  Toast.makeText(getActivity(),"下啦刷新",Toast.LENGTH_SHORT).show();
                    boutiqueVP.setAdapter(new BoutiqueVPAdapter(getFragmentManager()));
                    list_boutique.onRefreshComplete();
                    break;
                case 1:
                    rankadapter.Add(Lists.ranks);
                    list_Ranking.onRefreshComplete();
                    break;
            }
        }
    };
    private ImageView[] ivs=new ImageView[5];
    private PullToRefreshListView list_boutique;
    private PullToRefreshListView list_Ranking;
    private BoutiqueAdapter boutiqueadapter;
    private int startboutique = 0;
    private int endboutique = 20;
    private ViewPager boutiqueVP;
    private int startrank = 0;
    private int endrank = 20;
    private RankAdapter rankadapter;

    public static FoundVPfragment getFoundVPfragment(int position) {
        FoundVPfragment fragment = new FoundVPfragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        int position = getArguments().getInt(KEY);
        if (Lists.Jplist != null) {
            if (position == 0) {
                view = inflater.inflate(R.layout.classifylist, container, false);
                ListView list_classify = ((ListView) view.findViewById(R.id.classify_list));
                list_classify.setAdapter(new ClassifyAdapter(getActivity(), Lists.assortments));
            }
            if (position == 1) {
                view = inflater.inflate(R.layout.boutiquelist, container, false);
                list_boutique = ((PullToRefreshListView) view.findViewById(R.id.boutique_list));
                boutiqueadapter = new BoutiqueAdapter(getActivity(), Lists.Jplist);
                list_boutique.setAdapter(boutiqueadapter);
                //给PullToRefresh添加头部
                ListView listView = list_boutique.getRefreshableView();
                View view1 = View.inflate(getActivity(), R.layout.boutiqueviewpager, null);
                boutiqueVP = ((ViewPager) view1.findViewById(R.id.boutique_vp));
                boutiqueVP.setAdapter(new BoutiqueVPAdapter(getFragmentManager()));
                init(view1);
                boutiqueVP.setOnPageChangeListener(this);
                listView.addHeaderView(view1);
                list_boutique.setMode(PullToRefreshBase.Mode.BOTH);
                list_boutique.setOnItemClickListener(this);
                //监听上拉加载，下拉刷新
                list_boutique.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    private Cous cous;
                    private Marks marks;
                    private boutiquevpdata boutiquevpdata;

                    //下啦刷新
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                        startboutique = 0;
                        endboutique = 20;
                        boutiqueadapter.Clear();
                        Lists.Jplist = new ArrayList<Cous>();
                        Lists.boutiquevps = new ArrayList<boutiquevpdata>();
                        cous = null;
                        marks = null;
                        boutiquevpdata = null;
                        String path = "http://course.jaxus.cn/api/category//53e19aea81cc22417c36c1f0/courses?platform=1&channel=xiaomi&start=0&end=20&version=2";
                        HttpHelper.getUtils().send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                                try {
                                    //                    Log.d("----------aaaa----------", objectResponseInfo.result);
                                    JSONObject js = new JSONObject(objectResponseInfo.result);
                                    JSONArray arr = js.getJSONArray("courses");

                                    for (int i = 0; i < arr.length(); i++) {
                                        cous = new Cous();
                                        JSONObject json1 = arr.getJSONObject(i);
                                        cous.setBgUrl(json1.getString("bgUrl"));
                                        cous.setEnrollNum(json1.getInt("enrollNum"));
                                        cous.setIconUrl(json1.getString("iconUrl"));


//                        if ()
//                        double listPrice = json1.getDouble("listPrice");
//                        if(listPrice!=0){
//                            cous.setListPrice(json1.getDouble("listPrice"));
//
//                        }
                                        JSONArray arr2 = json1.getJSONArray("marks");
                                        List<Marks> listmarks = new ArrayList<Marks>();
                                        for (int j = 0; j < arr2.length(); j++) {
                                            marks = new Marks();
                                            JSONObject obj1 = arr2.getJSONObject(j);
                                            marks.setColor(obj1.getString("color"));
                                            marks.setImageUrl(obj1.getString("imageUrl"));
                                            marks.setTitle(obj1.getString("title"));

                                            listmarks.add(marks);
                                        }
                                        cous.setMarks(listmarks);
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
                                Toast.makeText(getActivity(), "连接失败请检查网络连接", Toast.LENGTH_SHORT).show();
                            }
                        });

                        String path4 = "http://course.jaxus.cn/api/category//53e19aea81cc22417c36c1f0/advs?channel=xiaomi&version=3";
                        HttpHelper.getUtils().send(HttpRequest.HttpMethod.GET, path4, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                                try {
                                    JSONObject object = new JSONObject(objectResponseInfo.result);
                                    JSONArray advs = object.getJSONArray("advs");
                                    for (int i = 0; i < advs.length(); i++) {
                                        boutiquevpdata = new boutiquevpdata();
                                        JSONObject object1 = advs.getJSONObject(i);
                                        boutiquevpdata.setImageUrl(object1.getString("imageUrl"));
                                        JSONArray arr2 = object1.getJSONArray("marks");
                                        List<Marks> listmarksss = new ArrayList<Marks>();
                                        for (int j = 0; j < arr2.length(); j++) {
                                            marks = new Marks();
                                            JSONObject obj1 = arr2.getJSONObject(j);
                                            marks.setColor(obj1.getString("color"));
                                            marks.setImageUrl(obj1.getString("imageUrl"));
                                            marks.setTitle(obj1.getString("title"));
                                            listmarksss.add(marks);
                                        }
                                        boutiquevpdata.setMarks(listmarksss);
                                        boutiquevpdata.setTitle(object1.getString("title"));
                                        Lists.boutiquevps.add(boutiquevpdata);
                                    }
                                    handler.sendEmptyMessage(0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(getActivity(), "连接失败请检查网络连接", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    //上拉加载
                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                        startboutique += 20;
                        endboutique += 20;
                        Lists.Jplist = new ArrayList<Cous>();
                        cous = null;
                        marks = null;
                        String path = "http://course.jaxus.cn/api/category//53e19aea81cc22417c36c1f0/courses?platform=1&channel=xiaomi&start=" + startboutique + "&end=" + endboutique + "&version=2";
                        HttpHelper.getUtils().send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                                try {
                                    //                    Log.d("----------aaaa----------", objectResponseInfo.result);
                                    JSONObject js = new JSONObject(objectResponseInfo.result);
                                    JSONArray arr = js.getJSONArray("courses");

                                    for (int i = 0; i < arr.length(); i++) {
                                        cous = new Cous();
                                        JSONObject json1 = arr.getJSONObject(i);
                                        cous.setBgUrl(json1.getString("bgUrl"));
                                        cous.setEnrollNum(json1.getInt("enrollNum"));
                                        cous.setIconUrl(json1.getString("iconUrl"));
                                        if (!json1.isNull("listPrice")) {
                                            cous.setListPrice(json1.getDouble("listPrice"));
                                        }

                                        JSONArray arr2 = json1.getJSONArray("marks");
                                        List<Marks> listmarks = new ArrayList<Marks>();
                                        for (int j = 0; j < arr2.length(); j++) {
                                            marks = new Marks();
                                            JSONObject obj1 = arr2.getJSONObject(j);
                                            marks.setColor(obj1.getString("color"));
                                            marks.setImageUrl(obj1.getString("imageUrl"));
                                            marks.setTitle(obj1.getString("title"));

                                            listmarks.add(marks);
                                        }
                                        cous.setMarks(listmarks);
                                        cous.setPrice(json1.getDouble("price"));
                                        cous.setProviderName(json1.getString("providerName"));
                                        cous.setRate(json1.getInt("rate"));
                                        cous.setTitle(json1.getString("title"));
                                        Lists.Jplist.add(cous);
                                    }
                                    handler.sendEmptyMessage(0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(getActivity(), "连接失败请检查网络连接", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


//                list.addHeaderView(View);
            }
            if (position == 2) {
                view = inflater.inflate(R.layout.boutiquelist, container, false);
                list_Ranking = ((PullToRefreshListView) view.findViewById(R.id.boutique_list));
                list_Ranking.setOnItemClickListener(this);
                rankadapter = new RankAdapter(getActivity(), Lists.ranks);
                list_Ranking.setAdapter(rankadapter);
                list_Ranking.setMode(PullToRefreshBase.Mode.BOTH);
                list_Ranking.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    private Rank rank;
                    private Marks marks;

                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                        startrank = 0;
                        endrank = 20;
                        rankadapter.Clear();
                        Lists.ranks = new ArrayList<Rank>();
                        rank = null;
                        marks = null;
                        String path3 =
                                "http://course.jaxus.cn/api/category//53e19aea81cc22417c36c1f0/courses?platform=1&sort=enrollNum&channel=xiaomi&start=" + startrank + "&end=" + endrank + "&version=2";


                        HttpHelper.getUtils().send(HttpRequest.HttpMethod.GET, path3, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                try {
                                    JSONObject js1 = new JSONObject(responseInfo.result);
                                    JSONArray arr = js1.getJSONArray("courses");
                                    for (int i = 0; i < arr.length(); i++) {
                                        rank = new Rank();
                                        JSONObject json1 = arr.getJSONObject(i);
                                        rank.setBgUrl(json1.getString("bgUrl"));
                                        rank.setEnrollNum(json1.getInt("enrollNum"));
                                        rank.setIconUrl(json1.getString("iconUrl"));
                                        if (!json1.isNull("listPrice")) {
                                            rank.setListPrice(json1.getDouble("listPrice"));
                                        }
//                        cous.setListPrice(json1.getDouble("listPrice"));
                                        JSONArray arr2 = json1.getJSONArray("marks");
                                        List<Marks> listmarkss = new ArrayList<Marks>();
                                        for (int j = 0; j < arr2.length(); j++) {
                                            marks = new Marks();
                                            JSONObject obj1 = arr2.getJSONObject(j);
                                            marks.setColor(obj1.getString("color"));
                                            marks.setImageUrl(obj1.getString("imageUrl"));
                                            marks.setTitle(obj1.getString("title"));
                                            listmarkss.add(marks);
                                        }
                                        rank.setMarks(listmarkss);
                                        rank.setPrice(json1.getDouble("price"));
                                        rank.setProviderName(json1.getString("providerName"));
                                        rank.setRate(json1.getInt("rate"));
                                        rank.setTitle(json1.getString("title"));
                                        Lists.ranks.add(rank);
                                    }
                                    handler.sendEmptyMessage(1);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(getActivity(), "连接失败请检查网络连接", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    //上拉加载
                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                        startrank += 20;
                        endrank += 20;
                        Lists.ranks = new ArrayList<Rank>();
                        rank = null;
                        marks = null;
                        String path3 =
                                "http://course.jaxus.cn/api/category//53e19aea81cc22417c36c1f0/courses?platform=1&sort=enrollNum&channel=xiaomi&start=" + startrank + "&end=" + endrank + "&version=2";
                        HttpHelper.getUtils().send(HttpRequest.HttpMethod.GET, path3, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                try {
                                    JSONObject js1 = new JSONObject(responseInfo.result);
                                    JSONArray arr = js1.getJSONArray("courses");
                                    for (int i = 0; i < arr.length(); i++) {
                                        rank = new Rank();
                                        JSONObject json1 = arr.getJSONObject(i);
                                        rank.setBgUrl(json1.getString("bgUrl"));
                                        rank.setEnrollNum(json1.getInt("enrollNum"));
                                        rank.setIconUrl(json1.getString("iconUrl"));
                                        if (!json1.isNull("listPrice")) {
                                            rank.setListPrice(json1.getDouble("listPrice"));
                                        }
//                        cous.setListPrice(json1.getDouble("listPrice"));
                                        JSONArray arr2 = json1.getJSONArray("marks");
                                        List<Marks> listmarkss = new ArrayList<Marks>();
                                        for (int j = 0; j < arr2.length(); j++) {
                                            marks = new Marks();
                                            JSONObject obj1 = arr2.getJSONObject(j);
                                            marks.setColor(obj1.getString("color"));
                                            marks.setImageUrl(obj1.getString("imageUrl"));
                                            marks.setTitle(obj1.getString("title"));
                                            listmarkss.add(marks);
                                        }
                                        rank.setMarks(listmarkss);
                                        rank.setPrice(json1.getDouble("price"));
                                        rank.setProviderName(json1.getString("providerName"));
                                        rank.setRate(json1.getInt("rate"));
                                        rank.setTitle(json1.getString("title"));
                                        Lists.ranks.add(rank);
                                    }
                                    handler.sendEmptyMessage(1);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(getActivity(), "连接失败请检查网络连接", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        } else {

        }

        return view;
    }

    private void init(View view) {
        ivs[0]= ((ImageView) view.findViewById(R.id.boutique_vpiv1));
        ivs[1]= ((ImageView) view.findViewById(R.id.boutique_vpiv2));
        ivs[2]= ((ImageView) view.findViewById(R.id.boutique_vpiv3));
        ivs[3]= ((ImageView) view.findViewById(R.id.boutique_vpiv4));
        ivs[4]= ((ImageView) view.findViewById(R.id.boutique_vpiv5));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        intent = new Intent(getActivity(), ShowItemActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for(int i=0;i<ivs.length;i++){
            if(position==i){
                ivs[i].setImageResource(R.drawable.point_grey);
            }else{
                ivs[i].setImageResource(R.drawable.page_now);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
