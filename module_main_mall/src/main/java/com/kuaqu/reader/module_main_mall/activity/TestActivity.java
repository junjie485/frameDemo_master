package com.kuaqu.reader.module_main_mall.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kuaqu.reader.component_data.constant.RouterURLS;
import com.kuaqu.reader.module_main_mall.R;
@Route(path = RouterURLS.TEST_ACTIVITY)
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
