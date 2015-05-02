package com.example.administrator.jxue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jxue.BitmapHelper;
import com.example.administrator.jxue.R;
import com.example.administrator.jxue.bean.Cous;
import com.example.administrator.jxue.bean.Rank;
import com.example.administrator.jxue.data.Lists;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2015-5-1.
 */
public class RankAdapter extends BaseAdapter{
    private Context context;
    private List<Rank> list;

    public RankAdapter(Context context, List<Rank> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.boutiqueitem, parent, false);
            convertView.setTag(new BoutiqueHolder(convertView));
        }
        BoutiqueHolder holder= ((BoutiqueHolder) convertView.getTag());
        Rank rank= Lists.ranks.get(position);
        holder.listprice.setVisibility(View.GONE);
        holder.view2.setVisibility(View.GONE);
        holder.marks.setVisibility(View.GONE);
        String imgUrl=rank.getBgUrl();
        if(imgUrl!=null){
            String replace=null;
            if(imgUrl.contains("")){
                replace=imgUrl.replace(" ", "%20");
            }else{
                String substring=imgUrl.substring(imgUrl.lastIndexOf("/")+1,imgUrl.lastIndexOf("."));
                String encode = URLEncoder.encode(substring);
                replace=imgUrl.replace(substring, encode);
            }
            BitmapHelper.getUtils().display(holder.image,replace);

        }

//        if (rank.getListPrice()!=null){
//            holder.listprice.setVisibility(View.VISIBLE);
//            holder.view2.setVisibility(View.VISIBLE);
//            holder.listprice.setText("￥"+rank.getListPrice()/100);
//        }
        if (rank.getMarks().size()!=0){
            holder.marks.setVisibility(View.VISIBLE);
            BitmapHelper.getUtils().display(holder.marks,rank.getMarks().get(0).getImageUrl());
        }
        if (rank.getPrice()==0){
            holder.price.setText("免费");
        }else{
            holder.price.setText("￥"+rank.getPrice()/100);
        }
        holder.user.setText(rank.getEnrollNum()+"");
        holder.author.setText(rank.getProviderName());
        holder.title.setText(rank.getTitle());
        return convertView;
    }

    class BoutiqueHolder{
        TextView title,author,user,listprice,price;
        ImageView image,marks;
        View view2;
        public BoutiqueHolder(View view){
            title= ((TextView) view.findViewById(R.id.boutique_title));
            author= ((TextView) view.findViewById(R.id.boutique_author));
            user= ((TextView) view.findViewById(R.id.boutique_user));
            listprice= ((TextView) view.findViewById(R.id.boutique_listprice));
            price= ((TextView) view.findViewById(R.id.boutique_price));
            image= ((ImageView) view.findViewById(R.id.boutique_image));
            view2=view.findViewById(R.id.boutique_view2);
            marks= ((ImageView) view.findViewById(R.id.boutique_marks));
        }
    }
}
