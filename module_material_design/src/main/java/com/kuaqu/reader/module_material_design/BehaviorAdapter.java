package com.kuaqu.reader.module_material_design;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class BehaviorAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BehaviorAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.text,s);
    }
}
