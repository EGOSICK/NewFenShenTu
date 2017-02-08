package com.xiandong.fst.tools;

import android.widget.ImageView;

import com.xiandong.fst.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by dell on 2017/1/12.
 */

public class XCircleImgTools {
    public static void setCircleImg(ImageView imageView, String img) {
        ImageOptions options = new ImageOptions.Builder()
                //设置加载过程中的图片
                .setLoadingDrawableId(R.color.appGray)
                //设置加载失败后的图片
                .setFailureDrawableId(R.color.appBlue)
                //设置使用缓存
                .setUseMemCache(true)
                //设置显示圆形图片
                .setCircular(true)
                //设置支持gif
                .setIgnoreGif(false)    //以及其他方法
                .build();
        x.image().bind(imageView, img, options);
    }
}
