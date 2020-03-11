package com.kuaqu.reader.module_specail_ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuaqu.reader.module_specail_ui.R;

import java.util.List;

public class MyTestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MyTestAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.name,s);
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "店家事件", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
