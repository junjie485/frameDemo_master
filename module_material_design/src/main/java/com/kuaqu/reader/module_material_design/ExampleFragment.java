package com.kuaqu.reader.module_material_design;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaqu.reader.module_material_design.behavior.MainHeaderBehavior;

import java.util.ArrayList;

public class ExampleFragment extends Fragment {
    private ViewPager mViewPager;
    View view;
    private MainHeaderBehavior mHeaderBehavior;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.example_fragment, null);
        initView();
        return view;
    }

    private void initView() {


    }

}
