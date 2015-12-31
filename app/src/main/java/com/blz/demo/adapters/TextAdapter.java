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
import com.blz.demo.items.TextItem;
import com.blz.demo.utils.CircleTansformation;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 卜令壮
 * on 2015/12/29
 * E-mail q137617549@qq.com
 */
public class TextAdapter extends BaseAdapter {
    private Context context;
    private List<TextItem.ItemsEntity> list;

    public TextAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_text,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        TextItem.ItemsEntity itemsEntity = list.get(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (itemsEntity.getUser() != null) {
            holder.name.setText(itemsEntity.getUser().getLogin());
            String iconURL = getIconURL(itemsEntity.getUser().getId(), itemsEntity.getUser().getIcon());
            if (iconURL == null){
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
        if (itemsEntity.getType() != null) {
            switch (itemsEntity.getType()) {
                case "hot":
                    holder.type.setText("热门");
                    holder.typeImage.setImageResource(R.mipmap.ic_rss_hot);
                    break;
                case "promote":
                    holder.type.setText("精选");
                    holder.typeImage.setImageResource(R.mipmap.ic_rss_promote);
                    break;
                case "fresh":
                    holder.type.setText("新鲜");
                    holder.typeImage.setImageResource(R.mipmap.ic_rss_fresh);
                    break;
                default:
                    holder.type.setText(itemsEntity.getType());
                    break;
            }
        }
        holder.content.setText(itemsEntity.getContent());
        holder.downUp.setText("点赞："+(itemsEntity.getVotes().getUp()-itemsEntity.getVotes().getDown()));
        holder.comments.setText("评论："+itemsEntity.getComments_count());
        holder.share.setText("转发："+itemsEntity.getShare_count());
        if (itemsEntity.getImage() == null) {
            holder.image.setVisibility(View.GONE);
        }else{
            holder.image.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(getImageURL(itemsEntity.getImage()))
                    .resize(parent.getWidth(),0)
                    .placeholder(R.mipmap.image_placeholder)
                    .error(R.mipmap.image_error)
                    .into(holder.image);
        }
        if (itemsEntity.getPic_url() == null) {
            holder.videoImage.setVisibility(View.GONE);
            holder.videoPlay.setVisibility(View.GONE);
        }else{
            holder.videoImage.setVisibility(View.VISIBLE);
            holder.videoPlay.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(itemsEntity.getPic_url())
                    .resize(parent.getWidth(),0)
                    .placeholder(R.mipmap.image_placeholder)
                    .error(R.mipmap.image_error)
                    .into(holder.videoImage);
        }
        return convertView;
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
    public void addAll(Collection<? extends TextItem.ItemsEntity> collection){
        list.addAll(collection);
        notifyDataSetChanged();
    }
    private static class ViewHolder{
        private ImageView icon;
        private TextView name;
        private TextView type;
        private TextView content;
        private TextView downUp;
        private TextView comments;
        private TextView share;
        private ImageView image;
        private ImageView videoImage;
        private ImageView videoPlay;
        private ImageView typeImage;

        public ViewHolder(View itemView){
            icon = (ImageView) itemView.findViewById(R.id.adapter_text_icon);
            name = (TextView) itemView.findViewById(R.id.adapter_text_name);
            type = (TextView) itemView.findViewById(R.id.adapter_text_type);
            content = (TextView) itemView.findViewById(R.id.adapter_text_content);
            downUp = (TextView) itemView.findViewById(R.id.adapter_text_downUp);
            comments = (TextView) itemView.findViewById(R.id.adapter_text_comments);
            share = (TextView) itemView.findViewById(R.id.adapter_text_share);
            image = (ImageView) itemView.findViewById(R.id.adapter_text_image);
            videoImage = (ImageView) itemView.findViewById(R.id.adapter_text_videoImage);
            videoPlay = (ImageView) itemView.findViewById(R.id.adapter_text_videoPlay);
            typeImage = (ImageView) itemView.findViewById(R.id.adapter_text_typeImage);
        }
    }
}
