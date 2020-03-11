package com.kuaqu.reader.module_material_design;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.tools.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoder>{
    private List<Book> mList=new ArrayList<>();
    private Context context;
    private boolean animateItems = false;
    private int lastAnimatedPosition = -1;
    private final TypedValue mTypedValue = new TypedValue();
    public MyAdapter(Context context,List<Book> mList) {
        this.context=context;
        this.mList=mList;
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
    }

    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        MyViewHoder vh = new MyViewHoder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHoder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        holder.img.setImageResource(mList.get(position).getImg());
        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.author.setText("作者："+mList.get(position).getAuthor());
        holder.subTitle.setText("副标题："+mList.get(position).getSubtitle());
        holder.date.setText("出版年："+mList.get(position).getDate());
        holder.price.setText("定价："+mList.get(position).getPrice());

    }

    private void runEnterAnimation(View view, int position) {
        if (!animateItems || position >= 4 - 1) {
            return;
        }
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(ScreenUtils.getScreenHeight(context));
            view.animate()
                    .translationY(0)
                    .setStartDelay(100 * position)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    public class MyViewHoder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tvTitle,author,subTitle,date,price;
        public MyViewHoder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.ivBook);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            author=itemView.findViewById(R.id.author);
            subTitle=itemView.findViewById(R.id.subTitle);
            date=itemView.findViewById(R.id.date);
            price=itemView.findViewById(R.id.price);
        }
    }
}
