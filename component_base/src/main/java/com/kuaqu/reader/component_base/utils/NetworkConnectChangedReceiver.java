package com.kuaqu.reader.component_base.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kuaqu.reader.component_base.base.BaseApplication;
import com.kuaqu.reader.component_base.base.EventBusBean;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetworkConnectChangedReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        int netStatus= NetUtil.getNetworkStatus(BaseApplication.getAppContext());
        Log.e("网络","--->"+netStatus);
        int net=ping(netStatus);
        EventBus.getDefault().post(new EventBusBean("network",net));
    }

    private int ping(int netStatus) {
        InputStream input = null;
        BufferedReader in;
        StringBuffer stringBuffer;
        if (netStatus==0||netStatus==1) {
            try {
                String ip = "www.baidu.com";//之所以写百度是因为百度比较稳定，一般不会出现问题，也可以ping自己的服务器
                Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping3次
                // 读取ping的内容
                input = p.getInputStream();
                in = new BufferedReader(new InputStreamReader(input));
                stringBuffer = new StringBuffer();
                String content = "";
                while ((content = in.readLine()) != null) {
                    stringBuffer.append(content);
                }
                // PING的状态
                int status = p.waitFor();  //status 为 0 ，ping成功，即为可以对外访问；为2则失败，即联网但不可以上网
                if (status == 0) {
                    Log.e("net","net is  available");
                    return 1;
                } else {

                    Log.e("net","net is not available");
                    return -1;
                }
            } catch (IOException e) {
                Log.e("net", "IOException");
            } catch (InterruptedException e) {
                Log.e("net", "InterruptedException");
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        }
        return -1;
    }
}
