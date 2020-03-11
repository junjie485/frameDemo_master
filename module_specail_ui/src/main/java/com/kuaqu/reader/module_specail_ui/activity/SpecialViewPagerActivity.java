package com.kuaqu.reader.module_specail_ui.activity;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.utils.RotateDownPageTransformer;
import com.kuaqu.reader.module_specail_ui.utils.ScaleInTransformer;

public class SpecialViewPagerActivity extends BaseActivity {
    private ViewPager viewPager;
    private int[] imgs = {R.mipmap.anim1, R.mipmap.anim2, R.mipmap.anim3, R.mipmap.anim4, R.mipmap.anim5, R.mipmap.anim6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_view_pager);
        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(true,new RotateDownPageTransformer());
//        viewPager.setPageTransformer(true,new ScaleInTransformer());
        viewPager.setAdapter(new PagerAdapter() {
                                 @Override
                                 public int getCount() {
                                     return imgs.length;
                                 }

                                 @Override
                                 public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                                     return view == object;
                                 }

                                 @NonNull
                                 @Override
                                 public Object instantiateItem(@NonNull ViewGroup container, int position) {
                                     ImageView imageView=new ImageView(SpecialViewPagerActivity.this);
                                     imageView.setImageResource(imgs[position]);
                                     imageView.setTag("view"+position);
                                     container.addView(imageView);
                                     return imageView;
                                 }
                                 @Override
                                 public void destroyItem(ViewGroup container, int position, Object object)
                                 {
                                     container.removeView((View) object);
                                 }

                             }

        );
    }
}
