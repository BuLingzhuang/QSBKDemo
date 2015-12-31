package com.blz.demo.utils;

import com.blz.demo.items.TextItem;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by 卜令壮
 * on 2015/12/29
 * E-mail q137617549@qq.com
 */
public interface QSBKService {
    @GET("article/list/{type}")
    Call<TextItem> getList(@Path("type") String type, @Query("page") int page);
}
