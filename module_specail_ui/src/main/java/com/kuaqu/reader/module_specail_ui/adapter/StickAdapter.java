package com.kuaqu.reader.module_specail_ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.activity.StickRecyActivity;
import com.kuaqu.reader.module_specail_ui.contract.StickBean;
import com.kuaqu.reader.module_specail_ui.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

public class StickAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<StickBean> list;
    private int height;

    public StickAdapter(Context context, List<StickBean> list,int height) {
        this.context = context;
        this.list = list;
        this.height=height;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1){
            return new ImgViewHolder(LayoutInflater.from(context).inflate(R.layout.img_item,parent,false));
        }else {
            return new ListViewHolder(LayoutInflater.from(context).inflate(R.layout.stick_list_item,parent,false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            if(viewHolder instanceof ImgViewHolder){
                ImgViewHolder holder= (ImgViewHolder) viewHolder;
                holder.img.setImageResource(list.get(position).getImg());
            }else {
                ListViewHolder holder= (ListViewHolder) viewHolder;
                List<Fragment> fragments=new ArrayList<>();
                for(int i=0;i< list.get(position).getTitleList().size();i++){
                    String text=list.get(position).getTitleList().get(i);
                    TextView textView=new TextView(context);
                    textView.setText(text);
                    textView.setTag(i);
                    textView.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1f);
                    textView.setLayoutParams(params);
                    holder.tab_liner.addView(textView);
                    fragments.add(new TestFragment().newInstance(""+i,list.get(position).getList()));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, ""+text, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                FragmentPagerAdapter adapter=new FragmentPagerAdapter(((StickRecyActivity)context).getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return fragments.get(position);
                    }

                    @Override
                    public int getCount() {
                        return fragments.size();
                    }
                };
                holder.viewPager.setAdapter(adapter);
                ViewGroup.LayoutParams layoutParams = holder.viewPager.getLayoutParams();
                layoutParams.height=height;
                holder.viewPager.setLayoutParams(layoutParams);
            }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class ImgViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ImgViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
        }
    }
    private class ListViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout tab_liner;
        private ViewPager viewPager;
        public ListViewHolder(View itemView) {
            super(itemView);
            tab_liner=itemView.findViewById(R.id.tab_liner);
            viewPager=itemView.findViewById(R.id.viewpager);
        }
    }
}
