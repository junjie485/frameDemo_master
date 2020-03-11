package com.kuaqu.reader.module_specail_ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.StickAdapter;
import com.kuaqu.reader.module_specail_ui.contract.StickBean;
import com.kuaqu.reader.module_specail_ui.utils.OutRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StickRecyActivity extends BaseActivity {
    private OutRecyclerView recyclerView;
    private StickAdapter adapter;
    private List<StickBean> mList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick_recy);
        initView();
        initData();
    }

    private void initData() {
        StickBean stickBean=new StickBean(1,R.mipmap.anim5,null,null);
        List<String> tList=new ArrayList<>();
        tList.add("母婴"); tList.add("零食");tList.add("美妆");tList.add("百货");
        List<StickBean.ListBean> ttList=new ArrayList<>();
        for(int i=0;i<6;i++){
            StickBean.ListBean listBean=new StickBean.ListBean(R.mipmap.anim5,"商品名字","12.8","25.00");
            ttList.add(listBean);
        }
        StickBean stickBean2=new StickBean(2,R.mipmap.anim5,tList,ttList);
        mList.add(stickBean);
        mList.add(stickBean2);
    }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new StickAdapter(this,mList,getScreenHeight()-getStatusBarHeight(this)-data());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);

    }
    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }
    private int data(){
        return (int) (getResources().getDisplayMetrics().density*50+0.5f);
    }
    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public void adjustIntercept(boolean b){
        recyclerView.setNeedIntercept(b);
    }
}
