package com.kuaqu.reader.module_specail_ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.BaseDelegateAdapter;
import com.kuaqu.reader.module_specail_ui.contract.HomeContract;
import com.kuaqu.reader.module_specail_ui.presenter.HomePresenter;
import com.yc.cn.ycbannerlib.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

public class VLayoutActivity extends BaseActivity implements HomeContract.View{
    private RecyclerView mRecyclerView;
    private List<DelegateAdapter.Adapter> mAdapters=new ArrayList<>();
    private HomePresenter presenter=new HomePresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlayout);
        presenter.bindActivity(this);
        initView();
    }

    private void initView() {
        mRecyclerView=findViewById(R.id.recyclerView);

        DelegateAdapter delegateAdapter = presenter.initRecyclerView(mRecyclerView);
        //把轮播器添加到集合
        BaseDelegateAdapter bannerAdapter = presenter.initBannerAdapter();
        mAdapters.add(bannerAdapter);

        //初始化九宫格
        BaseDelegateAdapter menuAdapter = presenter.initGvMenu();
        mAdapters.add(menuAdapter);

        //初始化
        BaseDelegateAdapter marqueeAdapter = presenter.initMarqueeView();
        mAdapters.add(marqueeAdapter);

        //初始化标题
        BaseDelegateAdapter titleAdapter = presenter.initTitle("豆瓣分享");
        mAdapters.add(titleAdapter);
        //初始化list3
        BaseDelegateAdapter girdAdapter3 = presenter.initList3();
        mAdapters.add(girdAdapter3);

        //初始化标题
        titleAdapter = presenter.initTitle("猜你喜欢");
        mAdapters.add(titleAdapter);
        //初始化list1
        BaseDelegateAdapter girdAdapter = presenter.initList1();
        mAdapters.add(girdAdapter);


        //初始化标题
        titleAdapter = presenter.initTitle("热门新闻");
        mAdapters.add(titleAdapter);
        //初始化list2
        BaseDelegateAdapter linearAdapter = presenter.initList2();
        mAdapters.add(linearAdapter);


        //初始化标题
        titleAdapter = presenter.initTitle("为您精选");
        mAdapters.add(titleAdapter);
        //初始化list3
        BaseDelegateAdapter plusAdapter = presenter.initList4();
        mAdapters.add(plusAdapter);


        //初始化list控件
        titleAdapter = presenter.initTitle("优质新闻");
        mAdapters.add(titleAdapter);
        linearAdapter = presenter.initList5();
        mAdapters.add(linearAdapter);

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
        mRecyclerView.requestLayout();

    }

    @Override
    public void setBanner(BannerView mBanner, List<Object> arrayList) {

    }

    @Override
    public void setOnclick(int position) {

    }

    @Override
    public void setMarqueeClick(int position) {

    }

    @Override
    public void setGridClick(int position) {

    }

    @Override
    public void setGridClickThird(int position) {

    }

    @Override
    public void setGridClickFour(int position) {

    }

    @Override
    public void setNewsList2Click(int position, String url) {

    }

    @Override
    public void setNewsList5Click(int position, String url) {

    }
}
