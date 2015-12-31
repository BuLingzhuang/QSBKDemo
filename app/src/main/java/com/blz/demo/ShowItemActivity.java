package com.blz.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blz.demo.adapters.ShowItemAdapter;
import com.blz.demo.items.Comments;
import com.blz.demo.items.TextItem;
import com.blz.demo.utils.CircleTansformation;
import com.blz.demo.utils.CommentsService;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ShowItemActivity extends AppCompatActivity implements Callback<Comments> {
    private static final String TAG = ShowItemActivity.class.getSimpleName();
    private Call<Comments> call;
    private ShowItemAdapter adapter;
    private List<Comments.ItemsEntity> itemsEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);
        ListView listView = (ListView) findViewById(R.id.showItem_listView);
        View headerView = getLayoutInflater().inflate(R.layout.adapter_show_item_header, null);

        //设置内容显示部分
        ImageView icon = (ImageView) headerView.findViewById(R.id.showItem_text_icon);
        TextView name = (TextView) headerView.findViewById(R.id.showItem_text_name);
        TextView type = (TextView) headerView.findViewById(R.id.showItem_text_type);
        TextView content = (TextView) headerView.findViewById(R.id.showItem_text_content);
        TextView downUp = (TextView) headerView.findViewById(R.id.showItem_text_downUp);
        TextView comments = (TextView) headerView.findViewById(R.id.showItem_text_comments);
        TextView share = (TextView) headerView.findViewById(R.id.showItem_text_share);
        ImageView image = (ImageView) headerView.findViewById(R.id.showItem_text_image);
        ImageView videoImage = (ImageView) headerView.findViewById(R.id.showItem_text_videoImage);
        ImageView videoPlay = (ImageView) headerView.findViewById(R.id.showItem_text_videoPlay);
        ImageView typeImage = (ImageView) headerView.findViewById(R.id.showItem_text_typeImage);
        Intent intent = getIntent();
        TextItem.ItemsEntity itemsEntity = (TextItem.ItemsEntity) intent.getSerializableExtra("TextItem");
        if (itemsEntity.getUser() != null) {

            name.setText(itemsEntity.getUser().getLogin());
            String iconURL = getIconURL(itemsEntity.getUser().getId(), itemsEntity.getUser().getIcon());
            if (iconURL == null) {
                icon.setImageResource(R.mipmap.ic_launcher);
            } else {
                Picasso.with(this)
                        .load(iconURL)
                        .transform(new CircleTansformation())
                        .into(icon);
            }
        } else {
            name.setText("匿名用户");
            icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (itemsEntity.getType() != null) {
            switch (itemsEntity.getType()) {
                case "hot":
                    type.setText("热门");
                    typeImage.setImageResource(R.mipmap.ic_rss_hot);
                    break;
                case "promote":
                    type.setText("精选");
                    typeImage.setImageResource(R.mipmap.ic_rss_promote);
                    break;
                case "fresh":
                    type.setText("新鲜");
                    typeImage.setImageResource(R.mipmap.ic_rss_fresh);
                    break;
                default:
                    type.setText(itemsEntity.getType());
                    break;
            }
        }
        content.setText(itemsEntity.getContent());
        downUp.setText("点赞：" + (itemsEntity.getVotes().getUp() - itemsEntity.getVotes().getDown()));
        comments.setText("评论：" + itemsEntity.getComments_count());
        share.setText("转发：" + itemsEntity.getShare_count());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int windowWidth = displayMetrics.widthPixels;
        if (itemsEntity.getImage() == null) {
            image.setVisibility(View.GONE);
        } else {
            image.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(getImageURL(itemsEntity.getImage()))
                        .resize(windowWidth, 0)
                    .placeholder(R.mipmap.image_placeholder)
                    .error(R.mipmap.image_error)
                    .into(image);
        }
        if (itemsEntity.getPic_url() == null) {
            videoImage.setVisibility(View.GONE);
            videoPlay.setVisibility(View.GONE);
        } else {
            Log.e(TAG, "宽度：" + windowWidth);
            videoImage.setVisibility(View.VISIBLE);
            videoPlay.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(itemsEntity.getPic_url())
                        .resize(windowWidth, 0)
                    .placeholder(R.mipmap.image_placeholder)
                    .error(R.mipmap.image_error)
                    .into(videoImage);
        }
        listView.addHeaderView(headerView);
        //设置评论部分
        adapter = new ShowItemAdapter(this);
        listView.setAdapter(adapter);
        Retrofit build = new Retrofit.Builder().baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommentsService service = build.create(CommentsService.class);
        Log.e(TAG, itemsEntity.getId());
        call = service.getList(itemsEntity.getId(), 1);
        call.enqueue(this);
    }

    public static String getIconURL(long id, String icon) {
        String url = "http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";
        return String.format(url, id / 10000, id, icon);
    }

    public static String getImageURL(String image) {
        String url = "http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s";
        Pattern pattern = Pattern.compile("(\\d+)\\d{4}");
        Matcher matcher = pattern.matcher(image);
        matcher.find();
        return String.format(url, matcher.group(1), matcher.group(), "medium", image);
    }

    //call的回调方法
    @Override
    public void onResponse(Response<Comments> response, Retrofit retrofit) {
        Log.e(TAG, "网络正常");
        itemsEntityList = response.body().getItems();
        Log.e(TAG, "size = " + itemsEntityList.size());
        adapter.addAll(itemsEntityList);
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }
}
