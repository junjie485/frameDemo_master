package com.kuaqu.reader.module_specail_ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebChromeClient;

import com.kuaqu.reader.module_specail_ui.R;
import com.kuaqu.reader.module_specail_ui.adapter.MyTestAdapter;
import com.kuaqu.reader.module_specail_ui.utils.MyTestRecyclerView;
import com.kuaqu.reader.module_specail_ui.utils.NestedScrollingWebView;

import java.util.ArrayList;
import java.util.List;

public class MyTestActivity extends AppCompatActivity {
    private MyTestRecyclerView recyclerView;
    private MyTestAdapter adapter;
    private NestedScrollingWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_test);
        initView();
        initData();
    }

    private void initData() {
        List<String> list=new ArrayList<>();
        for(int i=0;i<50;i++){
            list.add(""+i);
        }
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        webView=findViewById(R.id.web_container);
        recyclerView=findViewById(R.id.recyclerView);
        adapter=new MyTestAdapter(R.layout.my_test_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView .loadUrl("https://github.com/wangzhengyi/Android-NestedDetail");
    }
}
