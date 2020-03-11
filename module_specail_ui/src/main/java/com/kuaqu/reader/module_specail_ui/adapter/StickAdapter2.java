package com.kuaqu.reader.module_specail_ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.activity.TabRecycler2Activity;
import com.kuaqu.reader.module_specail_ui.contract.StickBean;
import com.kuaqu.reader.module_specail_ui.contract.TabRecyBean;
import com.kuaqu.reader.module_specail_ui.utils.InnerRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StickAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements InnerRecyclerView.NeedIntercepectListener {
    private Context context;
    private List<StickBean> list;

    private int TOP_COUNT = 10;


    public StickAdapter2(Context context, List<StickBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ImgViewHolder(LayoutInflater.from(context).inflate(R.layout.text_item_rv, parent, false));
        } else {
            return new ListViewHolder(LayoutInflater.from(context).inflate(R.layout.stick_list_item2, parent, false));
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ImgViewHolder) {
            ImgViewHolder holder = (ImgViewHolder) viewHolder;
            holder.text.setText("test" + position);
        } else {
            ListViewHolder holder = (ListViewHolder) viewHolder;
            holder.mRv.setNeedIntercepectListener(this);

            String[] tabTxt = {"客厅", "卧室", "餐厅", "书房", "阳台", "儿童房"};
            List<TabRecyBean> anchorList = new ArrayList<>();
            holder.tablayout.removeAllTabs();
            for (int i = 0; i < tabTxt.length; i++) {
                holder.tablayout.addTab(holder.tablayout.newTab().setText(tabTxt[i]));
                TabRecyBean anchorView = new TabRecyBean(tabTxt[i], tabTxt[i]);
                anchorList.add(anchorView);
            }
            int screenH = getScreenHeight();
            int statusBarH = getStatusBarHeight(context);
            int tabH = 50*3;
            Log.e("uirtypr", "-===" + tabH);
            int lastH = screenH - statusBarH - tabH;
            LinearLayoutManager manager = new LinearLayoutManager(context);
            holder.mRv.setLayoutManager(manager);
            holder.mRv.setAdapter(new TabRecyAdapter(R.layout.tab_recy_item, anchorList, context, lastH));
            holder.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    //点击标签，使recyclerView滑动，isRecyclerScroll置false
                    int pos = tab.getPosition();
                    holder.isRecyclerScroll = false;
                    Log.e("tab选中", "--->" + pos);
//                        moveToPosition(manager, holder.mRv, pos,holder);
                    manager.scrollToPositionWithOffset(pos, 0);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {//复选，即连续点同一个tab
                    onTabSelected(tab);
                    holder.tablayout.setScrollPosition(tab.getPosition(), 0, true);
                }
            });
                holder.mRv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //当滑动由recyclerView触发时，isRecyclerScroll 置true
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            holder.isRecyclerScroll = true;
                        }
                        return false;
                    }
                });


                holder.mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (holder.canScroll) {
                            holder.canScroll = false;
                            moveToPosition(manager, recyclerView, holder.scrollToPosition,holder);
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);//dx，dy是相对偏移量，如果计算滑动距离可定义变量 tatal-=dy;来累加操作，获取滑动距离
                        if (holder.isRecyclerScroll) {
                            //第一个可见的view的位置，即tablayou需定位的位置
                            int position = manager.findFirstVisibleItemPosition();
                            if (holder.lastPos != position) {
                                holder.tablayout.setScrollPosition(position, 0, true);
                            }
                            holder.lastPos = position;
                        }

                    }
                });


        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < TOP_COUNT) {
            return 1;
        } else {
            return 2;
        }

    }

    @Override
    public int getItemCount() {
        return TOP_COUNT + 1;
    }

    @Override
    public void needIntercepect(boolean needIntercepect) {
        ((TabRecycler2Activity) context).adjustIntercept(!needIntercepect);
    }

    private class ImgViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ImgViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {
        private TabLayout tablayout;
        private InnerRecyclerView mRv;
        private int lastPos;
        private boolean canScroll;
        private int scrollToPosition;
        private boolean isRecyclerScroll;

        public ListViewHolder(View itemView) {
            super(itemView);
            tablayout = itemView.findViewById(R.id.tablayout);
            mRv = itemView.findViewById(R.id.rv);

            DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
            final float scale = dm.density;
            int lenth = (int) (50 * scale + 0.5f);

            ViewGroup.LayoutParams layoutParams =mRv.getLayoutParams();
            layoutParams.height = getScreenHeight() - getStatusBarHeight(context) - lenth;
            mRv.setLayoutParams(layoutParams);

            int height = getStatusBarHeight(context) + lenth;
            mRv.setMaxY(height);


        }
    }

    private int getScreenHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int position, ListViewHolder holder) {
        // 第一个可见的view的位置
        int firstItem = manager.findFirstVisibleItemPosition();
        // 最后一个可见的view的位置
        int lastItem = manager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            // 如果跳转位置firstItem 之前(滑出屏幕的情况)，就smoothScrollToPosition可以直接跳转，
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在firstItem 之后，lastItem 之间（显示在当前屏幕），smoothScrollBy来滑动到指定位置
            int top = mRecyclerView.getChildAt(position - firstItem).getTop();
            Log.e("dhsaud", "-->" + position + "--->" + firstItem + "-->" + top + "==" + mRecyclerView.getChildAt(1).getTop());
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            // 如果要跳转的位置在lastItem 之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用当前moveToPosition方法，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            holder.scrollToPosition = position;
            holder.canScroll = true;
        }
    }


}
