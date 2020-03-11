package com.kuaqu.reader.module_common_ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


import com.kuaqu.reader.component_base.utils.PermissionListener;
import com.kuaqu.reader.component_base.utils.PermissionsUtil;
import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.response.AndroidtoJs;
import com.kuaqu.reader.module_common_ui.response.Contact;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/*
* android通过webView调用js代码：
1.通过WebView的loadUrl（）
2.通过WebView的evaluateJavascript（）
* JS通过WebView调用 Android 代码:
1.通过WebView的addJavascriptInterface（）进行对象映射
2.通过 WebViewClient 的shouldOverrideUrlLoading ()方法回调拦截 url
*
* 下载文件，服务器地址--读取字节流--创建文件写入流FileOutputStream（文件下载路径）--output.write()
* 如果不想读取字节流，可以转换为字符流，读取一行
* */
public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private Button button;
    private String msg="android传参调用js方法";
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView=findViewById(R.id.webView);
        button=findViewById(R.id.button);


        WebSettings webSettings=webView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);//设置缩放
//        webSettings.setBuiltInZoomControls(true);// 设置出现缩放工具


        //evaluateJavascript()只支持android4.4以上使用
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                // 格式规定为:file:///android_asset/文件名.html
                 webView.reload();
                 webView.loadUrl("file:///android_asset/javascript.html");
                 webView.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         if(Build.VERSION.SDK_INT<18){
                             webView.loadUrl("javascript:callJS(' "+msg+" ')");
                         }else {
                             webView.evaluateJavascript("javascript:callJS(' "+msg+" ')", new ValueCallback<String>() {
                                 @Override
                                 public void onReceiveValue(String s) {//s代表返回值
                                     Toast.makeText(WebViewActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                                 }
                             });
                         }
                     }
                 },1000);

            }
        });

        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }


        });
        //这种方式android4.2以下存在漏洞
        webView.addJavascriptInterface(new AndroidtoJs(this),"test");//AndroidtoJS类对象映射到js的test对象

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {
                        //  步骤3：
                        // 执行JS所需要调用的逻辑
                        // 可以在协议上带有参数并传递到Android上
                        Set<String> collection = uri.getQueryParameterNames();

                        for(String ss:collection){
                            Toast.makeText(WebViewActivity.this, ""+uri.getQueryParameters(ss), Toast.LENGTH_SHORT).show();
                        }

                    }

                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String url) {//webview页面加载完成监听
                super.onPageFinished(view, url);

            }
        });
    }
    @SuppressLint("JavascriptInterface")
    public void getFriends(View view){
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                webView.reload();
                webView.addJavascriptInterface(new SharpJS(), "sharp");
                webView.loadUrl("file:///android_asset/demo3.html");
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                Toast.makeText(WebViewActivity.this, "请打开权限", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.READ_CONTACTS,Manifest.permission.CALL_PHONE);


    }
    //自定义一个Js的业务类,传递给JS的对象就是这个,调用时直接javascript:sharp.contactlist()
    public class SharpJS {
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void contactlist() {
            webView.post(new Runnable() {//使该方法在ui线程执行
                @Override
                public void run() {
                    try {
                        System.out.println("contactlist()方法执行了！");
                        String json = buildJson(getContacts());
                        Log.e("数据",""+ json);
                        webView.loadUrl("javascript:show('" + json + "')");
                    } catch (Exception e) {
                        System.out.println("设置数据失败" + e);
                    }
                }
            });

        }
        @JavascriptInterface
        @SuppressLint("MissingPermission")
        public void call(String phone) {
            System.out.println("call()方法执行了！");
            Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(it);
        }
    }

    //将获取到的联系人集合写入到JsonObject对象中,再添加到JsonArray数组中
    public String buildJson(List<Contact> contacts)throws Exception
    {
        JSONArray array = new JSONArray();
        for(Contact contact:contacts)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", contact.getId());
            jsonObject.put("name", contact.getName());
            jsonObject.put("phone", contact.getPhone());
            array.put(jsonObject);
        }
        return array.toString();
    }

    //定义一个获取联系人的方法,返回的是List<Contact>的数据
    public List<Contact> getContacts()
    {
        List<Contact> Contacts = new ArrayList<Contact>();
        //①查询raw_contacts表获得联系人的id
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //查询联系人数据
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while(cursor.moveToNext())
        {
            Contact contact = new Contact();
            //获取联系人姓名,手机号码
            contact.setId(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
            contact.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contact.setPhone(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            Contacts.add(contact);
        }
        cursor.close();
        return Contacts;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }
}
