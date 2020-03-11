package com.kuaqu.reader.module_specail_ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.CardAdapter;
import com.kuaqu.reader.module_specail_ui.utils.OverLayCardLayoutManager;
import com.kuaqu.reader.module_specail_ui.utils.RenRenCallback;
import com.kuaqu.reader.module_specail_ui.utils.SwipeCardBean;

import java.util.List;

public class SwipeCardActivity extends BaseActivity {
    RecyclerView mRv;
    CardAdapter mAdapter;
    List<SwipeCardBean> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);

        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new OverLayCardLayoutManager());

        mAdapter=new CardAdapter(R.layout.item_swipe_card,mDatas = SwipeCardBean.initDatas(),this);
        mRv.setAdapter(mAdapter);

        //初始化配置
        ItemTouchHelper.Callback callback = new RenRenCallback(mRv, mAdapter, mDatas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);


        findViewById(R.id.btnRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatas.add(new SwipeCardBean(100, "http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "增加的"));
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
