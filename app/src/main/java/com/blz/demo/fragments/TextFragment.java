package com.blz.demo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.blz.demo.R;
import com.blz.demo.ShowItemActivity;
import com.blz.demo.adapters.TextAdapter;
import com.blz.demo.items.TextItem;
import com.blz.demo.utils.QSBKService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment implements Callback<TextItem>, AdapterView.OnItemClickListener {
    private static final String TAG = TextFragment.class.getSimpleName();
    public static final String TEXT = "text";
    private TextAdapter adapter;
    private Call<TextItem> call;
    private List<TextItem.ItemsEntity> itemsEntityList;

    public TextFragment() {
        // Required empty public constructor
    }

    public static TextFragment newInstance(String text){
        Bundle args = new Bundle();
        TextFragment fragment = new TextFragment();
        args.putString(TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) view.findViewById(R.id.fragment_text_listView);
        adapter = new TextAdapter(getContext());
        listView.setAdapter(adapter);
        Retrofit build = new Retrofit.Builder().baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        QSBKService service = build.create(QSBKService.class);
        String fragmentName = getArguments().getString(TEXT);
        String fragmentType = "error";
        if (fragmentName != null) {
            switch (fragmentName) {
                case "纯文":
                    fragmentType = "text";
                    break;
                case "纯图":
                    fragmentType = "image";
                    break;
                case "专享":
                    fragmentType = "suggest";
                    break;
                case "视频":
                    fragmentType = "video";
                    break;
                case "最新":
                    fragmentType = "latest";
                    break;
            }
        }
        call = service.getList(fragmentType,1);
        call.enqueue(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onResponse(Response<TextItem> response, Retrofit retrofit) {
        itemsEntityList = response.body().getItems();
        adapter.addAll(itemsEntityList);
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(getContext(),"网络错误",Toast.LENGTH_SHORT).show();
    }
    //listView的选中事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (itemsEntityList != null) {
            TextItem.ItemsEntity itemsEntity = itemsEntityList.get(position);
//            Bundle args = new Bundle();
//            args.putSerializable("TextItem.ItemsEntity", itemsEntity);
            Intent intent = new Intent(getContext(), ShowItemActivity.class);
            intent.putExtra("TextItem",itemsEntity);
//            intent.putExtras(args);
            startActivity(intent);
        }
    }
}
