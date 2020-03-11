package com.kuaqu.reader.module_main_mall.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.utils.ToastUtils;
import com.kuaqu.reader.module_main_mall.R;
import com.kuaqu.reader.module_main_mall.arouter.RouterCenter;

import javax.xml.transform.sax.TemplatesHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button common_btn,view_btn,designBtn,uiBtn,common_class_btn;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        common_btn=findViewById(R.id.common_btn);
        title=findViewById(R.id.title);
        view_btn=findViewById(R.id.view_btn);
        designBtn=findViewById(R.id.designBtn);
        uiBtn=findViewById(R.id.uiBtn);
        common_class_btn=findViewById(R.id.common_class_btn);
        uiBtn.setOnClickListener(this);
        designBtn.setOnClickListener(this);
        common_btn.setOnClickListener(this);
        view_btn.setOnClickListener(this);
        common_class_btn.setOnClickListener(this);
        title.setText("android个人空间");
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.common_btn) {
            RouterCenter.toCommonUI();
        }else if(i==R.id.view_btn){
            RouterCenter.toMyViewList();
        }else if(i==R.id.designBtn){
            RouterCenter.toMaterialDesign();
        }else if(i==R.id.uiBtn){
            RouterCenter.toSpecialUI();
        }else if(i==R.id.common_class_btn){
            RouterCenter.toCommonClass();
        }
    }
}
