package com.blz.demo.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blz.demo.R;
import com.blz.demo.items.Comments;
import com.blz.demo.utils.CircleTansformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 卜令壮
 * on 2015/12/30
 * E-mail q137617549@qq.com
 */
public class ShowItemAdapter extends BaseAdapter {
    private Context context;
    private List<Comments.ItemsEntity> list;

    public ShowItemAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_comments,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        Comments.ItemsEntity itemsEntity = list.get(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (itemsEntity.getUser() != null) {
            holder.name.setText(itemsEntity.getUser().getLogin());
            String iconURL = getIconURL(itemsEntity.getUser().getId(), itemsEntity.getUser().getIcon());
            if (itemsEntity.getUser().getIcon().equals("")){
                holder.icon.setImageResource(R.mipmap.ic_launcher);
            }else {
                Picasso.with(context)
                        .load(iconURL)
                        .transform(new CircleTansformation())
                        .into(holder.icon);
            }
        }else {
            holder.name.setText("匿名用户");
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        holder.content.setText(itemsEntity.getContent());
        holder.floor.setText(itemsEntity.getFloor()+"楼");
        holder.like_count.setText("赞:"+itemsEntity.getLike_count());

        return convertView;
    }
    public void addAll(Collection<? extends Comments.ItemsEntity> collection){
        list.addAll(collection);
        Log.e("更新adapter", "list.size = " + list.size());
        notifyDataSetChanged();
    }
    public static String getIconURL(long id,String icon){
        String url = "http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";
        return String.format(url,id/10000,id,icon);
    }
    public static String getImageURL(String image){
        String url = "http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s";
        Pattern pattern = Pattern.compile("(\\d+)\\d{4}");
        Matcher matcher = pattern.matcher(image);
        matcher.find();
        return String.format(url,matcher.group(1),matcher.group(),"medium",image);
    }
    private static class ViewHolder{
        private ImageView icon;
        private TextView name;
        private TextView content;
        private TextView floor;
        private TextView like_count;

        public ViewHolder(View itemView){
            icon = (ImageView) itemView.findViewById(R.id.adapter_comments_icon);
            name = (TextView) itemView.findViewById(R.id.adapter_comments_name);
            content = (TextView) itemView.findViewById(R.id.adapter_comments_content);
            floor = (TextView) itemView.findViewById(R.id.adapter_comments_floor);
            like_count = (TextView) itemView.findViewById(R.id.adapter_comments_like_count);
        }
    }
}
