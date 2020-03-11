package com.kuaqu.reader.module_common_ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.utils.StatusBarUtil;
import com.kuaqu.reader.module_common_ui.R;

public class StatusBarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        //状态栏颜色浸入
//        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FF4081"));

        //顶部图片浸入
        StatusBarUtil.setTranslucentStatus(this);//设置状态栏透明
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        //android:fitsSystemWindows=”true” （触发View的padding属性来给系统窗口留出空间）
//        StatusBarUtil.setStatusBarDarkTheme(this,true);//设置深浅色状态栏主题
    }
}
