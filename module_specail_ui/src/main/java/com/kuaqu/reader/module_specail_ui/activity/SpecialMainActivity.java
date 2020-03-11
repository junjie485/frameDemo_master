package com.kuaqu.reader.module_specail_ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_data.constant.RouterURLS;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.utils.MyTestRecyclerView;

@Route(path = RouterURLS.SPECIAL_UI)
public class SpecialMainActivity extends BaseActivity implements View.OnClickListener {
    private Button recyBtn, eventBtn, foldBtn, vLayoutBtn, hoverBtn, cardBtn, tabBtn, vpBtn, viewPagerBtn, qqBtn, pageBtn;
    private Button nestBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_main);
        initView();
    }

    private void initView() {
        recyBtn = findViewById(R.id.recyBtn);
        eventBtn = findViewById(R.id.eventBtn);
        foldBtn = findViewById(R.id.foldBtn);
        vLayoutBtn = findViewById(R.id.vLayoutBtn);
        hoverBtn = findViewById(R.id.hoverBtn);
        cardBtn = findViewById(R.id.cardBtn);
        tabBtn = findViewById(R.id.tabBtn);
        vpBtn = findViewById(R.id.vpBtn);
        viewPagerBtn = findViewById(R.id.viewPagerBtn);
        qqBtn = findViewById(R.id.qqBtn);
        pageBtn = findViewById(R.id.pageBtn);
        nestBtn=findViewById(R.id.nestBtn);
        qqBtn.setOnClickListener(this);
        recyBtn.setOnClickListener(this);
        eventBtn.setOnClickListener(this);
        foldBtn.setOnClickListener(this);
        vLayoutBtn.setOnClickListener(this);
        cardBtn.setOnClickListener(this);
        hoverBtn.setOnClickListener(this);
        tabBtn.setOnClickListener(this);
        vpBtn.setOnClickListener(this);
        viewPagerBtn.setOnClickListener(this);
        pageBtn.setOnClickListener(this);
        nestBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.recyBtn) {
            startActivity(new Intent(this, MountActivity.class));
        } else if (view.getId() == R.id.eventBtn) {
            startActivity(new Intent(this, EventActivity.class));
        } else if (view.getId() == R.id.foldBtn) {
            startActivity(new Intent(this, FoldActivity.class));
        } else if (view.getId() == R.id.vLayoutBtn) {
            startActivity(new Intent(this, VLayoutActivity.class));
        } else if (view.getId() == R.id.hoverBtn) {
            startActivity(new Intent(this, HoverItemActivity.class));
        } else if (view.getId() == R.id.cardBtn) {
            startActivity(new Intent(this, SwipeCardActivity.class));
        } else if (view.getId() == R.id.tabBtn) {
//            startActivity(new Intent(this,TabRecycleActivity.class));
            startActivity(new Intent(this,StickRecyActivity.class));
        } else if (view.getId() == R.id.vpBtn) {
            startActivity(new Intent(this,StickRecyActivity.class));
//            startActivity(new Intent(this, StudyActivity.class));
        } else if (view.getId() == R.id.viewPagerBtn) {
            startActivity(new Intent(this, TestActivity.class));
        } else if (view.getId() == R.id.qqBtn) {
            startActivity(new Intent(this, QQActivity.class));
        } else if (view.getId() == R.id.pageBtn) {
            startActivity(new Intent(this, SpecialViewPagerActivity.class));
        } else if (view.getId() == R.id.nestBtn) {
            startActivity(new Intent(this, MyTestActivity.class));
        }
    }
}
