package com.kuaqu.reader.module_common_ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.utils.StatusView;
import com.kuaqu.reader.module_common_ui.utils.StatusViewConvertListener;
import com.kuaqu.reader.module_common_ui.utils.ViewHolder;
/*
* 具体demo请参考https://github.com/SheHuan/StatusView
*https://www.jianshu.com/p/abbc70d1bce0
* 里面有详细用法。
* */
public class StatusActivity extends BaseActivity {
    private StatusView statusView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        statusView=StatusView.init(this,R.id.content_tv);
        statusView.showLoadingView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                statusView.showContentView();
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                statusView.showEmptyView();
            }
        }, 3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                statusView.showErrorView();
            }
        }, 5000);
        statusView.setOnErrorViewConvertListener(new StatusViewConvertListener() {
            @Override
            public void onConvert(ViewHolder viewHolder) {
                viewHolder.setOnClickListener(R.id.sv_error_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        statusView.showEmptyView();
                    }
                });
            }
        });
    }
}
