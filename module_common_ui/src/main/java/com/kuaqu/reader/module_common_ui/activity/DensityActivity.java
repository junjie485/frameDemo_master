package com.kuaqu.reader.module_common_ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kuaqu.reader.component_base.utils.Density;
import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.app.MyAppCommon;

public class DensityActivity extends AppCompatActivity {
    //今日头条适配方案：工具类只能写在Application类中，即只能全部应用，不能单独应用于某个界面
    //以上是根据360dp为基准进行写的，在不同分辨率手机测试，看是否两个控件无缝衔接
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density);
        //详细解释，请看备注
    }
}
