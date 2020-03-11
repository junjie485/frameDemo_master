package com.kuaqu.module_common_class;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuaqu.module_common_class.adapter.RecyclerAdapter;
import com.kuaqu.module_common_class.utils.CustomLayoutManager;
import com.kuaqu.module_common_class.utils.StickLayoutManager;
import com.kuaqu.reader.component_base.base.BaseActivity;

import java.util.ArrayList;

public class RecyclerActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private RelativeLayout main_liner;
//    private CoverFlowAdapter mAdapter;
    private ArrayList<String> mDatas =new ArrayList<>();


    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 11:
                    Toast.makeText(RecyclerActivity.this, ""+msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initView();
        initData();
    }

    private void initData() {
        for (int i=0;i<200;i++){
            mDatas.add("第 " + i +" 个item");
        }
//        adapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        main_liner=findViewById(R.id.main_liner);
        recyclerView=findViewById(R.id.recyclerView);
        //线性布局
//        CoverFlowLayoutManager linearLayoutManager = new CoverFlowLayoutManager();
        CustomLayoutManager linearLayoutManager=new CustomLayoutManager();

        recyclerView.setLayoutManager(linearLayoutManager);

//        recyclerView.addItemDecoration(new LinearItemDecoration(this));
//        mAdapter=new CoverFlowAdapter(this, mDatas);
        mAdapter = new RecyclerAdapter(this, mDatas);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setHandler(handler);


    }
}
