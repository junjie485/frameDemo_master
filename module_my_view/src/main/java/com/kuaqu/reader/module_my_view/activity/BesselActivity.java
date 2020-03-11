package com.kuaqu.reader.module_my_view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_my_view.R;
import com.kuaqu.reader.module_my_view.utils.BezierTypeEvaluator;
import com.kuaqu.reader.module_my_view.utils.WaterRipperView;

public class BesselActivity extends BaseActivity {
    private WaterRipperView waterView;
    private ImageView cart_iv;
    private Button plus;
    private LinearLayout main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bessel);
        waterView=findViewById(R.id.waterView);
        cart_iv=findViewById(R.id.cart_iv);
        plus=findViewById(R.id.plus);
        main_layout=findViewById(R.id.main_layout);
        waterView.startAnim();
    }
    public void enterCart(View view){
        //贝塞尔起始数据点
        int[] startPosition = new int[2];
        //贝塞尔结束数据点
        int[] endPosition = new int[2];
        plus.getLocationInWindow(startPosition);
        cart_iv.getLocationInWindow(endPosition);

        PointF startF = new PointF();
        PointF endF = new PointF();
        PointF controllF = new PointF();
        startF.x=startPosition[0];
        startF.y=startPosition[1];
        endF.x=endPosition[0];
        endF.y=endPosition[1];
        controllF.x=endF.x;
        controllF.y=startF.y;

        final ImageView imageView = new ImageView(this);
        main_layout.addView(imageView);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.getLayoutParams().width = 50;
        imageView.getLayoutParams().height = 50;
        imageView.setX(startF.x);
        imageView.setY(startF.y);

        ValueAnimator animator=ValueAnimator.ofObject(new BezierTypeEvaluator(controllF),startF,endF);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF= (PointF) valueAnimator.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                main_layout.removeView(imageView);
            }
        });
        animator.setDuration(200);
        animator.start();

    }
}
