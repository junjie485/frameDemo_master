package com.kuaqu.reader.module_common_ui.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.kuaqu.reader.module_common_ui.R;

public class TestUtils {
    Context context;
    public TestUtils(Context context) {
        this.context=context;
    }
    public void showPop(View view2){
        View view= LayoutInflater.from(context).inflate(R.layout.pop_layout,null,false);
        PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        popupWindow.showAsDropDown(view2);
        LinearLayout main=view.findViewById(R.id.main);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getY()<main.getTop()){
                    popupWindow.dismiss();
                }
                return true;
            }
        });

    }
    public void showDialog(){
        View view= LayoutInflater.from(context).inflate(R.layout.pop_layout,null,false);
        Dialog dialog=new Dialog(context);
        dialog.setContentView(view);
        WindowManager.LayoutParams lp= dialog.getWindow().getAttributes();
        lp.gravity=Gravity.CENTER;
        lp.width=159;
        lp.height=130;
        dialog.getWindow().setAttributes(lp);
    }
    public void showNotify(){
        NotificationManager manager= (NotificationManager) ((Activity)context).getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder compat=new NotificationCompat.Builder(context);
        compat.setContentText("你好世界")
                .setContentTitle("林俊杰")
                .setSmallIcon(R.mipmap.ic_launcher);
              manager.notify(0,compat.build());

    }
}
