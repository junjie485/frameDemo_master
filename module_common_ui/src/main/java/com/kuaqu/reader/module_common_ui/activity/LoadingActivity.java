package com.kuaqu.reader.module_common_ui.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.utils.CustomDialog;
import com.kuaqu.reader.component_base.utils.CustomPopupWindow;
import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.service.TestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends BaseActivity implements View.OnClickListener {
    private KProgressHUD hud;
    private Button label_indeterminate, grace_indeterminate, bar_determinate, custom_view;
    private Button pop_view,dialog_view;
    private CustomDialog  alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initView();
    }

    private void initView() {
        label_indeterminate = findViewById(R.id.label_indeterminate);
        grace_indeterminate = findViewById(R.id.grace_indeterminate);
        bar_determinate = findViewById(R.id.bar_determinate);
        custom_view = findViewById(R.id.custom_view);
        dialog_view=findViewById(R.id.dialog_view);
        pop_view=findViewById(R.id.pop_view);
        label_indeterminate.setOnClickListener(this);
        grace_indeterminate.setOnClickListener(this);
        bar_determinate.setOnClickListener(this);
        custom_view.setOnClickListener(this);
        pop_view.setOnClickListener(this);
        dialog_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.label_indeterminate) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("正在加载中....")
//                    .setWindowColor(getResources().getColor(R.color.colorPrimary))
                    ;
            scheduleDismiss();
            hud.show();
        } else if (view.getId() == R.id.grace_indeterminate) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f);
            scheduleDismiss();
            hud.show();
        } else if (view.getId() == R.id.bar_determinate) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
//                    .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
//                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                    .setLabel("Please wait");
            simulateProgressUpdate();
            hud.show();
        } else if (view.getId() == R.id.custom_view) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.progress_drawable_white);
            AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
            drawable.start();
            hud = KProgressHUD.create(this)
                    .setCustomView(imageView)
                    .setLabel("This is a custom view");
            scheduleDismiss();
            hud.show();
        }else if(view.getId()==R.id.pop_view){
            CustomPopupWindow customPopupWindow=new CustomPopupWindow.Builder(this)
                    .setContentView(R.layout.pop_layout_item)
                    .setWidth(LinearLayout.LayoutParams.MATCH_PARENT)
                    .setHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
                    .setOutSideCancel(true)
//                    .setAnimationStyle()
                    .setBackGroundAlpha(0.5f)
                    .builder()
                    .showAtLocation(findViewById(R.id.main_liner),Gravity.BOTTOM,0,0);//相对于父控件，不过好像当前控件效果也同样
//                   .showAsDropDown(custom_view);
            Button common_media_photo= (Button) customPopupWindow.getItemView(R.id.common_media_photo);
        }else if(view.getId()==R.id.dialog_view){
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
             alertDialog = builder.cancelTouchout(false)
                    .view(R.layout.dialog_logout)
                    .style(R.style.dialog)
                    .widthdp(230)
                    .heightpx(WindowManager.LayoutParams.WRAP_CONTENT)
                    .addViewOnclick(R.id.logout_cancle, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    })
                    .addViewOnclick(R.id.logout_sure, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .build();
            alertDialog.show();
            TextView logout_info = (TextView) alertDialog.findViewById(R.id.logout_info);
            logout_info.setText("确定要删除吗？");
        }

    }

    private void simulateProgressUpdate() {
        hud.setMaxProgress(100);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int currentProgress;

            @Override
            public void run() {
                currentProgress += 1;
                hud.setProgress(currentProgress);
                if (currentProgress == 80) {
                    hud.setLabel("Almost finish...");
                }
                if (currentProgress < 100) {
                    handler.postDelayed(this, 50);
                }
            }
        }, 100);
    }

    private void scheduleDismiss() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hud.dismiss();
            }
        }, 2000);
    }
    //冒泡排序
    public void BubbleSort(){
        int arr[]={3,48,25,5,37,67,8,12,15};
        int temp;
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
        for(int i=0;i<arr.length;i++){
            System.out.println("yc1-----" +arr[i]);
        }
    }
    public void selectSort(){
        int arr[]={3,48,25,5,37,67,8,12,15};
        int pos;
        for(int i=0;i<arr.length;i++){
            pos=i;
            for(int j=i+1;j<arr.length;j++){
                if(arr[j]<arr[pos]){
                    pos=j;
                }
            }
            if(pos!=i){

            }
        }
    }

}
