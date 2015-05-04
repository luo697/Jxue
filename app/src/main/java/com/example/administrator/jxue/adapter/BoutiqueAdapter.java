package com.example.administrator.jxue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jxue.Helper.BitmapHelper;
import com.example.administrator.jxue.R;
import com.example.administrator.jxue.bean.Cous;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2015-5-1.
 */
public class BoutiqueAdapter extends BaseAdapter{
    private Context context;
    private List<Cous> list;

    public BoutiqueAdapter(Context context, List<Cous> list) {
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

    public void Clear(){
        list.clear();
        notifyDataSetChanged();
    }
    public void Add(List<Cous> lists){
        list.addAll(lists);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.boutiqueitem, parent, false);
            convertView.setTag(new BoutiqueHolder(convertView));
        }
        BoutiqueHolder holder= ((BoutiqueHolder) convertView.getTag());
        Cous cous= list.get(position);
        holder.listprice.setVisibility(View.GONE);
        holder.view2.setVisibility(View.GONE);
        holder.marks.setVisibility(View.GONE);
        String imgUrl=cous.getBgUrl();
        if(imgUrl!=null){
            String replace=null;
            String replace1=null;
            if(imgUrl.contains("")){
                replace1=imgUrl.replace(" ", "%20");
            }
            String substring=replace1.substring(imgUrl.lastIndexOf("/")+1,imgUrl.lastIndexOf("."));
            String encode = URLEncoder.encode(substring);
            replace=replace1.replace(substring, encode);
            BitmapHelper.getUtils().display(holder.image,replace);

        }

        if (cous.getListPrice()!=null){
            holder.listprice.setVisibility(View.VISIBLE);
            holder.view2.setVisibility(View.VISIBLE);
            holder.listprice.setText("￥"+cous.getListPrice()/100);
        }
        if (cous.getMarks().size()!=0){
            holder.marks.setVisibility(View.VISIBLE);
            BitmapHelper.getUtils().display(holder.marks,cous.getMarks().get(0).getImageUrl());
         }
        if (cous.getPrice()==0){
            holder.price.setText("免费");
        }else{
            holder.price.setText("￥"+cous.getPrice()/100);
        }
        holder.user.setText(cous.getEnrollNum()+"");
        holder.author.setText(cous.getProviderName());
        holder.title.setText(cous.getTitle());
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
