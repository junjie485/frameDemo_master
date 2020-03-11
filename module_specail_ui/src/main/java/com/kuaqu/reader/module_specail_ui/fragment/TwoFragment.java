package com.kuaqu.reader.module_specail_ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;


import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.RecyclerAdapter;
import com.kuaqu.reader.module_specail_ui.utils.PullUpToLoadMore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {
    View mView;
    RecyclerView mRecyclerView;
    RecyclerAdapter mRecyclerAdapter;
    ArrayList<String> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        for (int i = 0; i < 40; i++) {
            mList.add("我是李小米吖：" + i);
        }
        mView = inflater.inflate(R.layout.fragment_two, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerAdapter = new RecyclerAdapter(getActivity());
        mRecyclerAdapter.setList(mList);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        //兼容api23以以下版本
        mRecyclerView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                //得到当前界面，第一个子视图的position
                int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findFirstVisibleItemPosition();
                if (firstVisibleItemPosition == 0) {
                    PullUpToLoadMore.isTop = true;
                } else {
                    PullUpToLoadMore.isTop = false;
                }
            }
        });



//        MyScrollView twoScrollView = (MyScrollView) mView.findViewById(R.id.twoScrollview);
//        twoScrollView.setScrollListener(new MyScrollView.ScrollListener() {
//            @Override
//            public void onScrollToBottom() {
//
//            }
//
//            @Override
//            public void onScrollToTop() {
//
//            }
//
//            @Override
//            public void onScroll(int scrollY) {
//                if (scrollY == 0) {
//                    PullUpToLoadMore.isTop  = true;
//                } else {
//                    PullUpToLoadMore.isTop  = false;
//                }
//            }
//
//            @Override
//            public void notBottom() {
//
//            }
//
//        });
    }

}
