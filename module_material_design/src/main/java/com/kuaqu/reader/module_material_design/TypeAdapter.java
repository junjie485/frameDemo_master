package com.kuaqu.reader.module_material_design;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHoder> {
    private int[] c;
    private Context context;
    private List<String> datas;

    public TypeAdapter(Context context, List<String> datas) {
        c = new int[]{Color.parseColor("#33FF0000"),
                Color.parseColor("#3300FF00"),
                Color.parseColor("#330000FF")};
        this.context = context;
        this.datas = datas;
    }

    @Override
    public TypeAdapter.MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent,false);
        MyViewHoder vh = new MyViewHoder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(TypeAdapter.MyViewHoder holder, int position) {
//        holder.item_tv.setBackgroundResource(c[position % 3]);
        holder.item_tv.setText("item" + (position + 1));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHoder extends RecyclerView.ViewHolder {
        TextView item_tv;
        public MyViewHoder(View itemView) {
            super(itemView);
            item_tv = itemView.findViewById(R.id.item_tv);
        }
    }
}
