package com.kuaqu.reader.module_specail_ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.TestAdapter;
import com.kuaqu.reader.module_specail_ui.utils.TestRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity {
    private TestRecyclerView recyclerView;
    private TestAdapter adapter;
    private View topView;
    private List<String> mList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause_test);
        initView();
        initData();


    }

    private void initData() {
        for(int i=0;i<50;i++){
            mList.add("第"+i+"项");
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerView);
        topView=findViewById(R.id.topView);
        topView.requestFocus();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new TestAdapter(mList,this);
        recyclerView.setAdapter(adapter);
    }
}
