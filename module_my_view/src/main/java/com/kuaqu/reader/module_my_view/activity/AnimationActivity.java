package com.kuaqu.reader.module_my_view.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.kuaqu.reader.module_my_view.R;
import com.kuaqu.reader.module_my_view.utils.ArgbEvaluator;
import com.kuaqu.reader.module_my_view.utils.CharEvaluator;
import com.kuaqu.reader.module_my_view.utils.ExpandRelateView;

/*
* 使用动画，需要在res文件夹下创建anim文件夹，用来存放动画的xml文件
* 属性动画与补间动画的区别：属性动画不仅可以针对控件创建动画，还可以针对控件属性来创建动画。补间动画进行平移操作后，
* 控件的点击位置还在原来的初始位置，而属性动画直接改变控件属性值，使控件实际位置发生改变，点击位置，就在平移后位置。
* ValueAnimator：public static ValueAnimator ofInt(int... values)
*值动画：只针对值进行动画，不涉及对象（ObjectAnimator）。通过值的改变，监听addUpdateListener()来改变控件的位置。
* 动画流程：ofInt(0,400)(定义数字区间)--》加速器（返回当前数字进度）--》Evaluator(根据进度，计算当前值)--》监听器返回（在addUpdateListener返回）
* ObjectAnimator：ObjectAnimator ofFloat(Object target, String propertyName, float... values) 注意：propertyName是指该控件只要有set属性名的方法，就可以调用。
 *动画流程：ofFloat(tv,"ScaleY",0,3,1)--》加速器（返回当前数字进度）--》Evaluator(根据进度，计算当前值)--》调用set函数（反射调用，并将当前值作为参数传入）
* */
public class AnimationActivity extends AppCompatActivity {
    private TextView target,tv_char,tv_color;
    private ExpandRelateView contentRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        target=findViewById(R.id.target);
        tv_char=findViewById(R.id.tv_char);
        tv_color=findViewById(R.id.tv_color);
        contentRv=findViewById(R.id.contentRv);
    }
    public void onTrans(View view){
//        Animation animation=AnimationUtils.loadAnimation(this,R.anim.trans);
//        target.startAnimation(animation);

       Animation translateAnim = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -80,
                Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -80);
        translateAnim.setDuration(2000);
        translateAnim.setFillBefore(true);
        target.startAnimation(translateAnim);
    }
    public void onScale(View view){
//        Animation animation=AnimationUtils.loadAnimation(this,R.anim.scale);
//        target.startAnimation(animation);

        Animation scaleAnim = new ScaleAnimation(0.0f,1.4f,0.0f,1.4f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnim.setDuration(700);
        target.startAnimation(scaleAnim);
    }
    public void onAlpha(View view){
//        Animation animation=AnimationUtils.loadAnimation(this,R.anim.alpha);
//        target.startAnimation(animation);

        Animation alphaAnim = new AlphaAnimation(1.0f,0.1f);
        alphaAnim.setDuration(3000);
        alphaAnim.setFillBefore(true);
        target.startAnimation(alphaAnim);
    }
    public void onRotate(View view){
//        Animation animation=AnimationUtils.loadAnimation(this,R.anim.rotate);
//        target.startAnimation(animation);

        Animation  rotateAnim = new RotateAnimation(0, -650, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(3000);
        rotateAnim.setFillAfter(true);
        target.startAnimation(rotateAnim);

    }
    public void onSet(View view){
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.set);
        target.startAnimation(animation);

    }
    public void onStartAnim(View view){
       /* ValueAnimator valueAnimator=ValueAnimator.ofObject(new CharEvaluator(),new Character('A'),new Character('Z'));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                char tv= (char) animation.getAnimatedValue();
                tv_char.setText(String.valueOf(tv));
            }
        });
        valueAnimator.setDuration(10000);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();


        ObjectAnimator animator = ObjectAnimator.ofInt(tv_color, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        animator.setDuration(8000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();*/

        contentRv.startAnim();

    }
}
