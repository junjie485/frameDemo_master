package com.kuaqu.reader.module_specail_ui.presenter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuaqu.reader.component_base.utils.ScreenUtils;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.activity.VLayoutActivity;
import com.kuaqu.reader.module_specail_ui.adapter.BaseDelegateAdapter;
import com.kuaqu.reader.module_specail_ui.contract.HomeContract;
import com.yc.cn.ycbannerlib.banner.BannerView;
import com.yc.cn.ycbannerlib.marquee.MarqueeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private VLayoutActivity activity;

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
    }

    public interface viewType {
        int typeBanner = 1;         //轮播图
        int typeGv = 2;             //九宫格
        int typeTitle = 3;          //标题
        int typeList = 4;           //list
        int typeNews = 5;           //新闻
        int typeMarquee = 6;        //跑马灯
        int typePlus = 7;          //不规则视图
        int typeSticky = 8;         //指示器
        int typeFooter = 9;         //底部
        int typeGvSecond = 10;      //九宫格
    }

    @Override
    public DelegateAdapter initRecyclerView(RecyclerView recyclerView) {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置适配器
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);
        return delegateAdapter;
    }

    @Override
    public BaseDelegateAdapter initBannerAdapter() {
        final List<Object> arrayList = new ArrayList<>();
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        //banner
        return new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.base_wrap_banner, 1, viewType.typeBanner) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 绑定数据
                BannerView mBanner = holder.getView(R.id.banner);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = ScreenUtils.dp2px(activity, 120);
                mBanner.setLayoutParams(layoutParams);
                mView.setBanner(mBanner, arrayList);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initGvMenu() {
        int[] img_url = {R.mipmap.ic_category_16, R.mipmap.ic_category_18, R.mipmap.ic_category_2, R.mipmap.ic_category_6, R.mipmap.ic_category_27, R.mipmap.ic_category_24, R.mipmap.ic_category_22, R.mipmap.ic_category_14};
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        gridLayoutHelper.setVGap(16);
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.item_vp_grid_iv, 8, viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_new_seed_title, "item" + position);
                holder.setImageResource(R.id.iv_new_seed_ic, img_url[position]);
                holder.getView(R.id.ll_new_seed_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mView.setOnclick(position);
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initMarqueeView() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.view_vlayout_marquee, 1, viewType.typeMarquee) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                MarqueeView marqueeView = holder.getView(R.id.marqueeView);

                ArrayList<String> info1 = new ArrayList<>();
                info1.add("1.坚持读书，写作，源于内心的动力！");
                info1.add("2.欢迎订阅喜马拉雅听书！");
                info1.add("3.欢迎关注我的GitHub！");
                marqueeView.startWithList(info1);
                // 在代码里设置自己的动画
                marqueeView.setOnItemClickListener((position1, textView) ->
                        mView.setMarqueeClick(position1)
                );
            }
        };
    }

    @Override
    public BaseDelegateAdapter initTitle(String title) {
        return new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.base_view_title, 1, viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initList1() {
        int[] img_url = {R.mipmap.ic_data_music, R.mipmap.ic_data_picture, R.mipmap.ic_data_music, R.mipmap.ic_data_picture, R.mipmap.ic_data_music, R.mipmap.ic_data_picture, R.mipmap.ic_data_music, R.mipmap.ic_data_picture};
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setMargin(0, 0, 0, 0);
        gridLayoutHelper.setPadding(0, 20, 0, 10);
        gridLayoutHelper.setVGap(10);
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setWeights(new float[]{25, 25, 25, 25});
        gridLayoutHelper.setBgColor(Color.WHITE);
        //gridLayoutHelper.setAutoExpand(true);//是否自动填充空白区域
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.view_vlayout_grid, 8, viewType.typeGvSecond) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, "item" + position);
                ImageView iv = holder.getView(R.id.iv_image);
                iv.setImageResource(img_url[position]);
                Objects.requireNonNull(holder.getConvertView()).setOnClickListener(v ->
                        mView.setGridClick(position)
                );
            }
        };
    }

    @Override
    public BaseDelegateAdapter initList2() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        return new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.item_gold_news_list, 3, viewType.typeNews) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                ImageView imageView = holder.getView(R.id.iv_logo);
                holder.setText(R.id.tv_title, "标题 ");
                imageView.setImageResource(R.mipmap.image_default);
                holder.setText(R.id.tv_time, "时间");

            }
        };
    }

    @Override
    public BaseDelegateAdapter initList3() {
        int[] img_url ={R.mipmap.bg_small_autumn_tree_min,R.mipmap.bg_small_leaves_min,R.mipmap.bg_small_magnolia_trees_min};
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setMargin(20, 0, 20, 0);
        gridLayoutHelper.setPadding(0, 20, 0, 10);
        gridLayoutHelper.setBgColor(Color.WHITE);
        //gridLayoutHelper.setAspectRatio(1.5f);  // 设置设置布局内每行布局的宽与高的比
        // gridLayoutHelper特有属性（下面会详细说明）
        //设置每行中 每个网格宽度 占 每行总宽度 的比例
        gridLayoutHelper.setWeights(new float[]{30, 40, 30});
        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(0);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(5);
        //是否自动填充空白区域
        //gridLayoutHelper.setAutoExpand(false);
        // 设置每行多少个网格
        //gridLayoutHelper.setSpanCount(6);
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.base_btn_title_view, 3,viewType.typeList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, "item"+position);
                ImageView iv = holder.getView(R.id.iv_image);
                iv.setImageResource(img_url[position]);
                Objects.requireNonNull(holder.getConvertView()).setOnClickListener(v -> {
                    //点击事件
                    mView.setGridClickThird(position);
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initList4() {
        int[] img_url ={R.mipmap.bg_small_autumn_tree_min,R.mipmap.bg_small_leaves_min,R.mipmap.bg_small_magnolia_trees_min};
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        //noinspection deprecation
        onePlusNLayoutHelper.setBgColor(activity.getResources().getColor(R.color.white));
        onePlusNLayoutHelper.setMargin(0, 0, 0, 0);
        onePlusNLayoutHelper.setPadding(10, 20, 10, 10);
        return new BaseDelegateAdapter(activity, onePlusNLayoutHelper, R.layout.view_vlayout_plus, 3, viewType.typePlus) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.getView(R.id.ll_first).setVisibility(View.VISIBLE);
                    holder.getView(R.id.ll_second).setVisibility(View.GONE);
                    holder.setText(R.id.tv_title, "lable"+position);
                    holder.setImageResource(R.id.iv_image, img_url[position]);
                } else {
                    holder.getView(R.id.ll_first).setVisibility(View.GONE);
                    holder.getView(R.id.ll_second).setVisibility(View.VISIBLE);
                    holder.setText(R.id.tv_title2, "lable"+position);
                    holder.setImageResource(R.id.iv_image2,  img_url[position]);
                }
                Objects.requireNonNull(holder.getConvertView()).setOnClickListener(v -> {
                    //点击事件
                    mView.setGridClickFour(position);
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initList5() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        return new BaseDelegateAdapter(activity,
                linearLayoutHelper, R.layout.view_vlayout_news, 10, viewType.typeFooter) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                    ImageView imageView = holder.getView(R.id.iv_logo);
                    holder.setText(R.id.tv_title, "新闻标题 ");
                    imageView.setImageResource(R.mipmap.image_default);
                    holder.setText(R.id.tv_time, "新闻时间");

            }
        };
    }

    @Override
    public void bindActivity(VLayoutActivity activity) {
        this.activity = activity;
    }
}
