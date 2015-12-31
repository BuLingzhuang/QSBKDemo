package com.blz.demo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by 卜令壮
 * on 2015/12/29
 * E-mail q137617549@qq.com
 */
public class CircleTansformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        //抗锯齿设置
        paint.setAntiAlias(true);
        //CLAMP MIRROR镜像 REPEAT重复
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        new Canvas(result).drawCircle(source.getWidth() / 2, source.getHeight() / 2, source.getHeight() / 2, paint);
        source.recycle();
        return result;
    }

    @Override
    public String key() {
        return "circle";
    }
}
