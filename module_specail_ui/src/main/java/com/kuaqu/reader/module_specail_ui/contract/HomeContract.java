package com.kuaqu.reader.module_specail_ui.contract;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.kuaqu.reader.module_specail_ui.activity.VLayoutActivity;
import com.kuaqu.reader.module_specail_ui.adapter.BaseDelegateAdapter;
import com.yc.cn.ycbannerlib.banner.BannerView;

import java.util.List;

public interface HomeContract {
    interface View  {
        void setBanner(BannerView mBanner, List<Object> arrayList);
        void setOnclick(int position);
        void setMarqueeClick(int position);
        void setGridClick(int position);
        void setGridClickThird(int position);
        void setGridClickFour(int position);
        void setNewsList2Click(int position, String url);
        void setNewsList5Click(int position , String url);
    }
    interface Presenter  {
        DelegateAdapter initRecyclerView(RecyclerView recyclerView);
        BaseDelegateAdapter initBannerAdapter();
        BaseDelegateAdapter initGvMenu();
        BaseDelegateAdapter initMarqueeView();
        BaseDelegateAdapter initTitle(String title);
        BaseDelegateAdapter initList1();
        BaseDelegateAdapter initList2();
        BaseDelegateAdapter initList3();
        BaseDelegateAdapter initList4();
        BaseDelegateAdapter initList5();
        void bindActivity(VLayoutActivity activity);
    }
}
