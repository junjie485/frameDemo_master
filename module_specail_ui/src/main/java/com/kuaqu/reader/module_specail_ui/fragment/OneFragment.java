package com.kuaqu.reader.module_specail_ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.utils.MyScrollView;
import com.kuaqu.reader.module_specail_ui.utils.PullUpToLoadMore;


public class OneFragment extends Fragment {
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_one, container, false);
        initView();
        return mView;
    }

    private void initView() {
        MyScrollView oneScrollView= (MyScrollView) mView.findViewById(R.id.oneScrollview);
        oneScrollView.setScrollListener(new MyScrollView.ScrollListener() {
            @Override
            public void onScrollToBottom() {

            }

            @Override
            public void onScrollToTop() {

            }

            @Override
            public void onScroll(int scrollY) {
                if (scrollY == 0) {
                    PullUpToLoadMore.isTop= true;
                } else {
                    PullUpToLoadMore.isTop= false;
                }
            }

            @Override
            public void notBottom() {

            }

        });
    }
}
