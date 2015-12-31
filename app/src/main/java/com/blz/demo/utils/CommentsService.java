package com.blz.demo.utils;

import com.blz.demo.items.Comments;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by 卜令壮
 * on 2015/12/30
 * E-mail q137617549@qq.com
 */
public interface CommentsService {
    @GET("article/{id}/comments")
    Call<Comments> getList(@Path("id") String id,@Query("page") int page);
}
