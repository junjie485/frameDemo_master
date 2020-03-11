package com.kuaqu.module_common_class.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kuaqu.module_common_class.R;

import java.util.ArrayList;

/*
*这里主要重写三个方法：onCreateViewHolder、onBindViewHolder、getItemCount。
* 另外集合需要从外界传进来，所以需要构造函数。
*创建viewHolder，避免每次都需要重新find item项的控件。
* */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private ArrayList<String> mDatas;
    private static final int TYPE_SECTION=0;
    private static final int TYPE_ITEM=1;
    private int M_SECTION_ITEM_NUM = 10;
    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public RecyclerAdapter(Context mContext, ArrayList<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%M_SECTION_ITEM_NUM==0){
            return TYPE_SECTION;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if(viewType==TYPE_SECTION){
            return new SectionHolder(inflater.inflate(R.layout.item_section_layout,parent,false));
        }
        return new NormalHolder(inflater.inflate(R.layout.item_rv_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NormalHolder){
            NormalHolder normalHolder = (NormalHolder)holder;
            normalHolder.mTV.setText(mDatas.get(position));
        }else if(holder instanceof SectionHolder){
            SectionHolder sectionHolder = (SectionHolder)holder;
            sectionHolder.mSectionTv.setText("第 "+position/M_SECTION_ITEM_NUM +" 组");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(11,"这是第"+position+"项"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public class NormalHolder extends RecyclerView.ViewHolder{
        public TextView mTV;

        public NormalHolder(View itemView) {
            super(itemView);
            mTV = (TextView) itemView.findViewById(R.id.item_tv);
            mTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,mTV.getText(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public class SectionHolder extends RecyclerView.ViewHolder {

        public TextView mSectionTv;

        public SectionHolder(View itemView) {
            super(itemView);
            mSectionTv = (TextView) itemView.findViewById(R.id.item_section_tv);
        }
    }

}
