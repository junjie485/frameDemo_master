package com.kuaqu.reader.module_specail_ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudyFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private TestAdapter testAdapter;
    private List<String> mList=new ArrayList<>();
    private int type=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.study_fragment,container,false);
        initView();
        initData();
        return view;
    }
    public StudyFragment getInstance(int type){
        StudyFragment fragment=new StudyFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initData() {
        for(int i=0;i<50;i++){
            mList.add("tab"+type+"第"+i+"项");
        }
        testAdapter.notifyDataSetChanged();
    }

    private void initView() {
        type=getArguments().getInt("type");
        recyclerView=view.findViewById(R.id.recyclerView);
        testAdapter=new TestAdapter(mList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(testAdapter);
    }
}
