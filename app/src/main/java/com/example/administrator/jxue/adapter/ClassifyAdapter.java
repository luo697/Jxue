package com.example.administrator.jxue.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.jxue.Helper.BitmapHelper;
import com.example.administrator.jxue.R;
import com.example.administrator.jxue.bean.Assortment;

import java.util.List;

/**
 * Created by JQ on 2015/4/30.
 */
public class ClassifyAdapter extends BaseAdapter {
    private Context context;
    private List<Assortment> assortments;

    public ClassifyAdapter(Context context, List<Assortment> assortments) {
        this.context = context;
        this.assortments = assortments;
        Log.d("=======assortments===========", assortments.toString());
    }

    @Override
    public int getCount() {
        return assortments.size();
    }

    @Override
    public Object getItem(int i) {
        return assortments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view= LayoutInflater.from(context).inflate(R.layout.classifyitem,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(assortments.get(i).getName());
        holder.image.setImageResource(R.drawable.ic_launcher);
        BitmapHelper.getUtils().display(holder.image, assortments.get(i).getIconUrl());
        return view;
    }

    public class ViewHolder {
        ImageView image;
        TextView text;

        public ViewHolder(View v) {
            image = (ImageView) v.findViewById(R.id.classifyitem_icon);
            text = (TextView) v.findViewById(R.id.classifyitem_text);
        }
    }
}
