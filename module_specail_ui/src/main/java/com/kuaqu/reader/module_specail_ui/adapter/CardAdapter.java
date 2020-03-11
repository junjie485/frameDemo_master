package com.kuaqu.reader.module_specail_ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.utils.SwipeCardBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends BaseQuickAdapter<SwipeCardBean,BaseViewHolder> {

    private Context context;
    private List<SwipeCardBean> mDatas;
    public CardAdapter(int layoutResId, @Nullable List data, Context context) {
        super(layoutResId, data);
        this.context=context;
        this.mDatas=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, SwipeCardBean swipeCardBean) {
        helper.setText(R.id.tvName, swipeCardBean.getName());
        helper.setText(R.id.tvPrecent, swipeCardBean.getPostition() + " /" + mDatas.size());
        Picasso.with(context).load(swipeCardBean.getUrl()).into((ImageView) helper.getView(R.id.iv));
    }
}
