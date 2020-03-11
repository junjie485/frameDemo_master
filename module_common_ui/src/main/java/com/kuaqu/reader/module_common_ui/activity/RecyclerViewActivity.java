package com.kuaqu.reader.module_common_ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.widget.HeaderAndFooterWrapper;
import com.kuaqu.reader.module_common_ui.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RecyclerViewActivity extends BaseActivity {
    private RecyclerView recyclerView;
    HomeAdapter adapter;
    List<Model> modelList=new ArrayList<>();
    View notDataView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
        initData();
        initListner();
    }

    private void initListner() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "点击了"+position, Toast.LENGTH_SHORT).show();
                //注意：插入数据后，position位置会发生改变
                switch (position){
                    default:
                        adapter.notifyItemChanged(position);//刷新指定一个。一定会闪
                        break;
                    case 1:
                        adapter.notifyDataSetChanged();//全部刷新。基本不会闪，刚开始个别可能会闪
                        break;
                    case 2:
                        adapter.notifyItemRangeChanged(position, 2);//从指定位置开始刷新指定个数。一定会闪
                        break;
                    case 3://只更改数据源，这样当然不会刷新UI
                        modelList.add(position, new Model("插入一条数据"));
                        break;
                    case 4:
                        modelList.add(position, new Model("插入一条数据"));
                        adapter.notifyItemInserted(position);//在当前位置插入一个并刷新，正常
                        break;
                    case 5://
                        modelList.add(position, new Model("插入一条数据。之刷新当前item"));
                        adapter.notifyItemChanged(position);//这样只会导致当前item刷新，而不会刷新其他item，当然是不行的
                        break;
                    case 6:
                        modelList.set(position, modelList.get(new Random().nextInt(modelList.size())));
                        adapter.notifyItemChanged(position, HomeAdapter.NOTIFY_TV);//局部刷新
                        break;
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int i = view.getId();
                if (i == R.id.item_btn) {
                    Toast.makeText(RecyclerViewActivity.this, "点击了按钮", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new HomeAdapter(R.layout.list_item,modelList);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        adapter.setNotDoAnimationCount(3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
//        adapter.isFirstOnly(false);//表示不止进来的一次动画
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        //由于空布局是以recyclerview为父布局，必须把recyclerview设为满屏，空布局才能显示正常



    }

    private void initData() {
        for(int i=1;i<10;i++){
            Model model=new Model();
            model.setText("第"+i+"项");
            modelList.add(model);
        }
        modelList.get(0).setText("刷新当前，notifyItemChanged(int)");
        modelList.get(1).setText("全部刷新，notifyDataSetChanged()");
        modelList.get(2).setText("从此位置开始刷新2个，notifyItemRangeChanged");
        modelList.get(3).setText("只更改数据源，这样当然不会刷新UI");
        modelList.get(4).setText("插入一个并自动刷新，notifyItemInserted");
        modelList.get(5).setText("插入一个并刷新当前，notifyItemChanged");
        modelList.get(6).setText("局部刷新，tv");
        adapter.setNewData(modelList);//内部有刷新,清空原有数据
//        adapter.addData(modelList);//不清空，用作加载
        if(modelList.size()==0){
            adapter.setEmptyView(notDataView);
        }
        // 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法,预加载
        adapter.setPreLoadNumber(1);
    }

    public class Model{
        public Model() {
        }

        public Model(String text) {
            this.text = text;
        }

        String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
    public class HomeAdapter extends BaseQuickAdapter<Model,BaseViewHolder>{
        public static final int NOTIFY_TV = 10086;
        public HomeAdapter(int layoutResId, @Nullable List<Model> data) {
            super(layoutResId, data);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
            //payloads是从notifyItemChanged(int, Object)中，或从notifyItemRangeChanged(int, int, Object)中传进来的Object集合
            //如果payloads不为空并且viewHolder已经绑定了旧数据了，那么adapter会使用payloads参数进行布局刷新
            //如果payloads为空，adapter就会重新绑定数据，也就是刷新整个item
            String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            if (payloads.isEmpty()) {//为空，即不是调用notifyItemChanged(position,payloads)后执行的，也即在初始化时执行的
                ((Button) holder.getView(R.id.item_btn)).setText(time);
                ((TextView) holder.getView(R.id.item_text)).setText(modelList.get(position).getText());
            } else if (payloads.size() > 0 && payloads.get(0) instanceof Integer) {
                //不为空，即调用notifyItemChanged(position,payloads)后执行的，可以在这里获取payloads中的数据进行局部刷新
                int type = (int) payloads.get(0);// 刷新哪个部分 标志位
                switch (type) {
                    case NOTIFY_TV:
                        ((Button) holder.getView(R.id.item_btn)).setText(time);//只刷新tv
                        break;
                }
            }
        }

        @Override
        protected void convert(BaseViewHolder helper, Model item) {
            helper.addOnClickListener(R.id.item_btn);//可以进行链式调用
            helper.setText(R.id.item_text,item.getText());
            String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            Log.e("position",helper.getLayoutPosition()+"-->"+helper.getAdapterPosition());
             if(helper.getLayoutPosition()%2==0){
                 ((Button) helper.getView(R.id.item_btn)).setText(time);
             }
        }
    }

}
