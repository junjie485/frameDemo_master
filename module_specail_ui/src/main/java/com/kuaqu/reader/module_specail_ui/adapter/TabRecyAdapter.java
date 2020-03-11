package com.kuaqu.reader.module_specail_ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.contract.TabRecyBean;
import com.kuaqu.reader.module_specail_ui.utils.SwipeCardBean;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class TabRecyAdapter extends BaseQuickAdapter<TabRecyBean,BaseViewHolder> {

    private Context context;
    private List<TabRecyBean> mDatas;
    private int lastH;
    public TabRecyAdapter(int layoutResId, @Nullable List data, Context context,int length) {
        super(layoutResId, data);
        this.context=context;
        this.mDatas=data;
        this.lastH=length;
    }

    @Override
    protected void convert(BaseViewHolder helper, TabRecyBean swipeCardBean) {
        helper.setText(R.id.tv_anchor, swipeCardBean.getTitle());
        helper.setText(R.id.tv_content, swipeCardBean.getContent());
        TextView textView=helper.getView(R.id.tv_content);
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        textView.setBackgroundColor(Color.rgb(r, g, b));

        //判断最后一个view
        if (helper.getAdapterPosition() == mDatas.size() - 1) {
            if (helper.itemView.getHeight() < lastH) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.height = lastH;
                helper.itemView.setLayoutParams(params);
            }
        }
    }
}
