package com.kuaqu.reader.module_specail_ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaqu.reader.component_base.widget.FullyGridLayoutManager;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.activity.StickRecyActivity;
import com.kuaqu.reader.module_specail_ui.adapter.FragmentListAdapter;
import com.kuaqu.reader.module_specail_ui.contract.StickBean;
import com.kuaqu.reader.module_specail_ui.utils.InnerRecyclerView;

import java.io.Serializable;
import java.util.List;

public class TestFragment extends Fragment implements InnerRecyclerView.NeedIntercepectListener{
    private String type;
    private View view;
    private List<StickBean.ListBean> mList;
    private InnerRecyclerView recyclerView;
    private FragmentListAdapter adapter;
    private int height;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.stick_fragment_item, container, false);
        }
        initView();
        return view;
    }

    private void initView() {
        type = getArguments().getString("type");
        mList= (List<StickBean.ListBean>) getArguments().getSerializable("list");
        Log.e("shuju","--->"+mList.get(0).getName());
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(true);
        DisplayMetrics dm = getActivity().getApplicationContext().getResources().getDisplayMetrics();
        final float scale = dm.density;
        int i = (int) (50 * scale + 0.5f);
        height = getStatusBarHeight(getActivity())+i;
        recyclerView.setMaxY(height);
        recyclerView.setNeedIntercepectListener(this);

        GridLayoutManager manager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        adapter=new FragmentListAdapter(getContext(),mList);
        recyclerView.setAdapter(adapter);


    }

    public static TestFragment newInstance(String type, List<StickBean.ListBean> list) {
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putSerializable("list", (Serializable) list);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void needIntercepect(boolean needIntercepect) {
        ((StickRecyActivity)getActivity()).adjustIntercept(!needIntercepect);
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
}
