package com.kuaqu.reader.module_specail_ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.contract.StickBean;
import com.kuaqu.reader.module_specail_ui.utils.SwipeCardBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FragmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<StickBean.ListBean> mDatas;

    public FragmentListAdapter(Context context, List<StickBean.ListBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_recy_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.img.setImageResource(mDatas.get(position).getImg());
        viewHolder.name.setText(mDatas.get(position).getName());
        viewHolder.price.setText("￥"+mDatas.get(position).getPrice());
        viewHolder.market_price.setText("￥"+mDatas.get(position).getMarket_price());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,price,market_price;
        private ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            market_price=itemView.findViewById(R.id.market_price);
            img=itemView.findViewById(R.id.img);
        }
    }
}
