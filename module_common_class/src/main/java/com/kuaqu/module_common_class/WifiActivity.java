package com.kuaqu.module_common_class;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kuaqu.module_common_class.utils.ViewInject;
import com.kuaqu.module_common_class.utils.ViewInjectUtils;

import java.util.Arrays;
import java.util.List;

public class WifiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiMgr.startScan();
        List<ScanResult> scanResults = wifiMgr.getScanResults();//扫描到的周围热点信息
        WifiInfo info = wifiMgr.getConnectionInfo();//已连接wifi信息
        for (int i = 0; i < scanResults.size(); i++) {
            Log.e("热点信息", scanResults.get(i).SSID);
        }

        Log.e("连接信息","-->"+info.getSSID()+"--->"+wifiMgr.getWifiState());
        ArrayDemo();
        SystemDemo();
    }

    //　　Object src : 原数组
    //   int srcPos : 从元数据的起始位置开始
    //　　Object dest : 目标数组
    //　　int destPos : 目标数组的开始起始位置
    //　　int length  : 要copy的数组的长度
    private void SystemDemo() {
        byte[]  srcBytes = new byte[]{2,4,0,0,0,0,0,10,15,50};
        byte[] destBytes = new byte[5];
        System.arraycopy(srcBytes,0,destBytes,0,5);
        for(int i=0;i<destBytes.length;i++){
            Log.e("system",""+destBytes[i]);
        }

    }

    //Arrays的copyOf()方法传回的数组是新的数组对象，改变传回数组中的元素值，不会影响原来的数组。
    //copyOf()的第二个自变量指定要建立的新数组长度，如果新数组的长度超过原数组的长度，则保留数组默认值
    private void ArrayDemo() {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = Arrays.copyOf(arr1, 3);//数组的copy
        int[] arr3 = Arrays.copyOf(arr1, 10);
        arr1 = Arrays.copyOf(arr1, 15);//数组的扩容
        for(int i = 0; i < arr2.length; i++)
            Log.e("array",""+arr2[i]);
        for(int i = 0; i < arr3.length; i++)
            Log.e("array",""+arr3[i]);
        for(int i = 0; i < arr1.length; i++)
            Log.e("array",""+arr1[i]);
    }

    public void downFile(View view){
     /*   //下载路径，如果路径无效了，可换成你的下载路径
        String url = "http://www.kuaqu.cn/upload/download/1.5.5.apk";
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir(Environment.getExternalStorageState(), "testFile.apk");
        //获取下载管理器
        DownloadManager downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);*/

        EditDialogFragment dialogFragment=new EditDialogFragment();
        dialogFragment.show(getSupportFragmentManager(),"dialogFragment");
    }


}
