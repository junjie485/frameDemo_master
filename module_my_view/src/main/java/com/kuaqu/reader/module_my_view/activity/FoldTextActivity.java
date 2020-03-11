package com.kuaqu.reader.module_my_view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_my_view.R;
import com.kuaqu.reader.module_my_view.utils.FoldTextView;

public class FoldTextActivity extends BaseActivity {
    private FoldTextView spanTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_text);
        spanTextView = findViewById(R.id.text);
        spanTextView.setText("111111123阿斯顿发阿斯顿发送到大。厦法定阿萨【德法师打发斯蒂芬撒地】方阿萨德法师打发斯问问蒂芬撒地方阿萨德法师打发斯蒂。芬撒地方发送到发送到发送到发送到发送到发送，到发送到发送到发送到，发送111111123阿斯顿发阿斯顿发送到大。厦法定阿萨【德法师打发斯蒂芬撒地】方阿萨德法师打发斯问问蒂芬撒地方阿萨德法师打发斯蒂。芬撒地方发送到发送到发送到发送到发送到发送，到发送到发送到发送到，发送");
        spanTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FoldTextActivity.this, "textView点击事件", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
