package com.kuaqu.reader.module_specail_ui.activity;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.utils.ScreenUtils;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.HoveringAdapter;
import com.kuaqu.reader.module_specail_ui.utils.HeaderScrollHelper;
import com.kuaqu.reader.module_specail_ui.utils.HeaderScrollView;

public class MountActivity extends BaseActivity implements HeaderScrollHelper.ScrollableContainer{
    private RecyclerView recyclerView;
    private HeaderScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mount);
        scrollView = (HeaderScrollView) findViewById(R.id.view_hover);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        scrollView.setCurrentScrollableContainer(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        //设置recyclerview的高度为屏幕高度-状态栏高度-header高度
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight(this) - ScreenUtils.getStatusHeight(this) - ScreenUtils.dp2px(this, 55)));
        final String[] data = {"header", "content", "content", "content", "content", "content", "content", "content", "content", "content", "content", "footer",
                "header", "content", "content", "content", "content", "content", "content", "content", "content", "content", "content", "footer",
                "header", "content", "content", "content", "content", "content", "content", "content", "content", "content", "content", "footer"};
        HoveringAdapter adapter = new HoveringAdapter(this, data);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public View getScrollableView() {
        return recyclerView;
    }
}
