package com.kuaqu.reader.component_base.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.kuaqu.reader.component_base.R;
import com.kuaqu.reader.component_base.imgtransform.CircleCornerForm;
import com.kuaqu.reader.component_base.imgtransform.CircleTransform;
import com.squareup.picasso.Picasso;

/*
 * picasso：https://blog.csdn.net/mawei7510/article/details/80580097
 *
 *
 * */
public class ImgUtils {
    //正常显示图片
    public static void showImg(Context context, String url, ImageView view) {
/*        Picasso.with(context)
                .load(url)
                .placeholder(R.color.gray)
                .error(R.color.gray)
                .into(view);*/

        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.color.gray)
                        .error(R.color.gray)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .into(view);

    }

    //显示圆形图片
    public static void showCircleImg(Context context, String url, ImageView view) {
     /*   Picasso.with(context)
                .load(url)
                .transform(new CircleTransform())
                .placeholder(R.color.gray)
                .error(R.color.gray)
                .into(view);*/

        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.color.gray)
                        .error(R.color.gray)
                        .transforms(new CircleCrop())//圆形图片
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .into(view);

    }

    public static void showCornerImg(Context context, String url, ImageView view, int width, int height) {
      /*  Picasso.with(context)
                .load(url)
                .resize(dp2px(context, width), dp2px(context, height))
                .centerCrop()
                .transform(new CircleCornerForm())
                .placeholder(R.color.gray)
                .error(R.color.gray)
                .into(view);*/

        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.color.gray)
                        .error(R.color.gray)
                        .transforms(new CenterCrop(), new RoundedCorners(20))//圆角图片
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .into(view);

    }

    //加载本地资源图片
    public static void showResourceImg(Context context, int icon, ImageView view) {
/*        Picasso.with(context)
                .load(icon)
                .placeholder(R.color.gray)
                .error(R.color.gray)
//                .noFade()//取消图片渐入过渡效果
//                .resize(300,300)//设置图片尺寸
//                .onlyScaleDown()//只有当原始图片的尺寸大于我们指定的尺寸时，resize才起作用
//                .centerCrop()//图片重新设置尺寸后，出现拉伸等情况
//                .rotate(45)//设置图片旋转角度，以（0,0）点旋转，也可以指定旋转点（角度数,x,y）
//                .transform(new BlurTransformation(context))//高斯模糊
//                .transform(new GrayTransformation())//度灰处理
//                .transform(new CircleTransform())//圆形图片
//                .transform(new CircleCornerForm())//圆角图片
                .into(view);*/


        Glide.with(context)
                .load(icon)
                .apply(new RequestOptions()
                        .placeholder(R.color.gray)
                        .error(R.color.gray)
//                        .dontAnimate()
//                        .override(300, 300)
//                        .transforms(new CenterCrop(), new RoundedCorners(20))//圆角图片
//                        .transforms(new CircleCrop())//圆形图片
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .into(view);
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
