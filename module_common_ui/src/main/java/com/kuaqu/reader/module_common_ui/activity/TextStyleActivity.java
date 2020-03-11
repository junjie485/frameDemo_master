package com.kuaqu.reader.module_common_ui.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.widget.TextView;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_common_ui.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextStyleActivity extends BaseActivity {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_status);
        ButterKnife.bind(this);
        initView();
        //设置粗体
        textView1.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        textView1.getPaint().setAntiAlias(true);
        //文字加上中划线（又称删除线）
        textView2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        textView2.getPaint().setAntiAlias(true);
        //文字加上下划线
        textView3.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView3.getPaint().setAntiAlias(true);
        //文字设置不同的颜色和背景色
        SpannableStringBuilder stringBuilder=new SpannableStringBuilder("字体多种颜色一&背景色");
        stringBuilder.setSpan(new ForegroundColorSpan(Color.RED),0,2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(new ForegroundColorSpan(Color.YELLOW),2,5,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(new ForegroundColorSpan(Color.BLUE),5,7,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(new BackgroundColorSpan(Color.GREEN), 7, stringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView4.setText(stringBuilder);
        //文字设置不同的颜色（html格式）
        Spanned string= Html.fromHtml("<font color='red'>字体</font><font color='#00ff00'>多种颜色</font><font color='#0000ff'>二</font>");
        textView5.setText(string);
        //字体样式大小不一(有相对大小和绝对大小)
        SpannableStringBuilder bu=new SpannableStringBuilder("字体大小样式不一");
        bu.setSpan(new AbsoluteSizeSpan(80),0,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        bu.setSpan(new AbsoluteSizeSpan(40),2,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        bu.setSpan(new AbsoluteSizeSpan(60),6,bu.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView6.setText(bu);
        //设置文字上标和上标字符大小
        SpannableStringBuilder sp=new SpannableStringBuilder("设置字符上标");
        sp.setSpan(new SuperscriptSpan(),2,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new RelativeSizeSpan(0.5f),2,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView7.setText(sp);
        SpannableStringBuilder sb=new SpannableStringBuilder("设置字符下标");
        sb.setSpan(new SubscriptSpan(),2,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView8.setText(sb);
        //设置文字X方向缩放
        SpannableStringBuilder sk=new SpannableStringBuilder("请设置缩放");
        sk.setSpan(new ScaleXSpan(2f),2,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sk.setSpan(new ScaleXSpan(0.5f),4,5,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView9.setText(sk);
        //设置文字后面图片
        SpannableStringBuilder sd=new SpannableStringBuilder("文字跟图片了 ");
        Drawable drawable=getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0,0,50,50);
        ImageSpan imageSpan=new ImageSpan(drawable);
        sd.setSpan(imageSpan,sd.length()-1,sd.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView10.setText(sd);

    }

    private void initView() {
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        textView5=findViewById(R.id.textView5);
        textView6=findViewById(R.id.textView6);
        textView7=findViewById(R.id.textView7);
        textView8=findViewById(R.id.textView8);
        textView9=findViewById(R.id.textView9);
        textView10=findViewById(R.id.textView10);
    }
}
