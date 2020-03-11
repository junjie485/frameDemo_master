package com.kuaqu.reader.module_common_ui.utils;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.TextView;

import com.kuaqu.reader.module_common_ui.R;

public class TextStyleUtils {
    //设置不同字体颜色
    public static SpannableStringBuilder setDiffTextColor(String data,int color,int start,int end){
        SpannableStringBuilder stringBuilder=new SpannableStringBuilder(data);
        stringBuilder.setSpan(new ForegroundColorSpan(color),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }
    //设置不同字体大小
    public static SpannableStringBuilder setDiffTextSize(String data,int size,int start,int end){
        SpannableStringBuilder stringBuilder=new SpannableStringBuilder(data);
        stringBuilder.setSpan(new AbsoluteSizeSpan(size),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }
    //设置中划线
    public static void setCancelLine(TextView view){
        view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        view.getPaint().setAntiAlias(true);
    }
    //设置下划线
    public static void setUnderLine(TextView view){
        view.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        view.getPaint().setAntiAlias(true);
    }
    //文字中插入图片
    public static SpannableStringBuilder setTextInsertPicture(String data,Drawable drawable,int width,int height,int start,int end){
        SpannableStringBuilder sd=new SpannableStringBuilder(data);
//        Drawable drawable=getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0,0,width,height);
        ImageSpan imageSpan=new ImageSpan(drawable);
        sd.setSpan(imageSpan,start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sd;
    }
    //设置下标
    public static SpannableStringBuilder setSubscript(String data,int start,int end){
        SpannableStringBuilder sb=new SpannableStringBuilder(data);
        sb.setSpan(new SubscriptSpan(),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
    //设置上标
    public static SpannableStringBuilder setSuperscript(String data,int start,int end){
        SpannableStringBuilder sb=new SpannableStringBuilder(data);
        sb.setSpan(new SuperscriptSpan(),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
