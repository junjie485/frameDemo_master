package com.kuaqu.module_common_class.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaqu.module_common_class.R;

public class ScrollFoldAdapter extends RecyclerView.Adapter<ScrollFoldAdapter.ViewHolder> {
    private final int[] IMGS = {R.mipmap.item1, R.mipmap.item2, R.mipmap.item3, R.mipmap.item4,
            R.mipmap.item5,R.mipmap.item6, R.mipmap.anim5};
    private final String[] NAMES = {"万达百货——北京海淀店", "恒隆广场——大连中山店", "万达广场——大连高新园区", "翠微百货——北京店",
            "新世纪百货——北京店", "万达广场——上海宝山", "财神到商场——天津店"};
    //item的透明度，当未置顶显示时
    public static final float ITEM_SHADE_DARK_ALPHA = 0.65f;
    //item的透明度，当置顶显示时
    public static final float ITEM_SHADE_LIGHT_ALPHA = 0.15f;
    //item置顶时，内容扩展的倍数
    public static final float ITEM_CONTENT_TEXT_SCALE = 1.5f;

    private Context mContext;
    private RecyclerView recyclerView;

    //item置顶时的高度
    private int itemHeight;
    //item未置顶时的高度
    private int itemSmallHeight;

    public ScrollFoldAdapter(Context context, RecyclerView recyclerView){
        mContext = context;
        this.recyclerView = recyclerView;
        itemHeight = context.getResources().getDimensionPixelSize(
                R.dimen.scroll_fold_item_height);
        itemSmallHeight = (int)(itemHeight /ITEM_CONTENT_TEXT_SCALE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View item = LayoutInflater.from(mContext).inflate(R.layout.scroll_fold_list_item, null);
            return new ItemViewHolder(item);
        }
        else{
            View bottom = LayoutInflater.from(mContext).inflate(R.layout.scroll_fold_list_footer, null);
            return new BottomViewHolder(bottom);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.initData(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return IMGS.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position < IMGS.length){
            return 0;
        }
        return 1;
    }

    abstract class ViewHolder extends RecyclerView.ViewHolder{
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
        }

        abstract void initData(int position);
    }

    class BottomViewHolder extends ViewHolder{

        public BottomViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void initData(int position) {
            ViewGroup.LayoutParams bottomParams = itemView.getLayoutParams();
            if(bottomParams == null){
                bottomParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            }
            bottomParams.height = recyclerView.getHeight() - itemHeight + 10;
            itemView.setLayoutParams(bottomParams);
        }
    }

    class ItemViewHolder extends ViewHolder{
        View content;
        ImageView image;
        TextView name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            content = itemView.findViewById(R.id.item_content);
            image = (ImageView)itemView.findViewById(R.id.item_img);
            name = (TextView) itemView.findViewById(R.id.item_name);
        }

        void initData(int position){
            image.setImageResource(IMGS[position]);
            name.setText(NAMES[position]);
            ViewGroup.LayoutParams params = item.getLayoutParams();
            if(params == null){
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            }
            params.height = itemSmallHeight;
            content.findViewById(R.id.item_img_shade).setAlpha(ITEM_SHADE_DARK_ALPHA);
            item.setLayoutParams(params);
        }
    }
}
