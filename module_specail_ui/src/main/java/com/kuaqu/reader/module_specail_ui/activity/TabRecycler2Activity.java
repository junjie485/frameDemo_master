package com.kuaqu.reader.module_specail_ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.widget.HeaderAndFooterWrapper;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.StickAdapter;
import com.kuaqu.reader.module_specail_ui.adapter.StickAdapter2;
import com.kuaqu.reader.module_specail_ui.utils.OutRecyclerView;

public class TabRecycler2Activity extends BaseActivity {
    private OutRecyclerView recyclerView;
    private StickAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_recy2);
        initView();
    }

    private void initView() {

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new StickAdapter2(this,null);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setNestedScrollingEnabled(true);

        HeaderAndFooterWrapper wrapper=new HeaderAndFooterWrapper(adapter);
        TextView textView=new TextView(this);
        ViewGroup.LayoutParams params= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setText("这是头部");
        wrapper.addHeaderView(textView);
        recyclerView.setAdapter(wrapper);
        recyclerView.setNestedScrollingEnabled(true);

    }


    public void adjustIntercept(boolean b){
        recyclerView.setNeedIntercept(b);
    }
}
