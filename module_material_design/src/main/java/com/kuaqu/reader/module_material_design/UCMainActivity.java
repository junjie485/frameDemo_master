package com.kuaqu.reader.module_material_design;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kuaqu.reader.module_material_design.behavior.MainHeaderBehavior;

import java.util.ArrayList;

public class UCMainActivity extends AppCompatActivity implements MainHeaderBehavior.OnHeaderStateListener{
    private ViewPager mViewPager;

    private MainHeaderBehavior mHeaderBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucmain);

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        fragments.add(TypeFragment.newInstance());
        fragments.add(TypeFragment.newInstance());
        fragments.add(TypeFragment.newInstance());

        titles.add("tab1");
        titles.add("tab2");
        titles.add("tab3");

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tableLayout = (TabLayout) findViewById(R.id.title_tab);
        TypePageAdapter mTypeAdapter = new TypePageAdapter(getSupportFragmentManager());
        mTypeAdapter.setData(fragments, titles);
        mViewPager.setAdapter(mTypeAdapter);
        mViewPager.setOffscreenPageLimit(titles.size() - 1);

        tableLayout.setupWithViewPager(mViewPager);
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onHeaderClosed() {
        Log.e("status", "closed");
    }

    @Override
    public void onHeaderOpened() {
        Log.e("status", "opened");
    }

    @Override
    public void onBackPressed() {
        if (mHeaderBehavior != null && mHeaderBehavior.isClosed()) {
            mHeaderBehavior.openHeader();
        } else {
            super.onBackPressed();
        }
    }

}
