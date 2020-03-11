package com.kuaqu.reader.module_specail_ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.TestAdapter;
import com.kuaqu.reader.module_specail_ui.utils.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class QQActivity extends BaseActivity {
    private SlidingMenu mMenu;
    private RecyclerView recyclerView;
    private List<String> mList=new ArrayList<>();
    private TestAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
        recyclerView=findViewById(R.id.recyclerView);
        initView();
    }

    private void initView() {
        for(int i=0;i<40;i++){
            mList.add("第"+i+"项");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new TestAdapter(mList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void toggleMenu(View view)
    {
        mMenu.toggle();
    }

}
