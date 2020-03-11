package com.kuaqu.reader.module_common_ui.response;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

// 继承自Object类
public class AndroidtoJs extends Object {
    private Context context;
    public AndroidtoJs(Context context) {
        this.context=context;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {
        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
//        System.out.println("JS调用了Android的hello方法");
    }
}
