package com.kuaqu.reader.module_my_view.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_my_view.R;
import com.kuaqu.reader.module_my_view.utils.VerticalLinearLayout;

public class TestActivity extends BaseActivity {
    private VerticalLinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myview_test);
        initView();
    }

    private void initView() {
        layout=findViewById(R.id.id_main_ly);
        layout.setOnPageChangeListener(new VerticalLinearLayout.OnPageChangeListener() {
            @Override
            public void onPageChange(int currentPage) {
                Toast.makeText(TestActivity.this, "第"+currentPage+"页", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
