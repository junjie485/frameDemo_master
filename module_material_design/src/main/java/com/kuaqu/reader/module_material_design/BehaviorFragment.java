package com.kuaqu.reader.module_material_design;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BehaviorFragment extends Fragment {
    private View view;
    private RecyclerView my_list;
    private BehaviorAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.behavior_fragment,container,false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        List<String> list=new ArrayList<>();
        for(int i=0;i<30;i++){
           list.add("item"+i);
        }
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        my_list=view.findViewById(R.id.my_list);
        adapter=new BehaviorAdapter(R.layout.behavior_list_item);
        my_list.setLayoutManager(new LinearLayoutManager(getContext()));
        my_list.setAdapter(adapter);
    }


}
