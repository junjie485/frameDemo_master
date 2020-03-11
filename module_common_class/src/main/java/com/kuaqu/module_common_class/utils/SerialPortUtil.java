package com.kuaqu.module_common_class.utils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPortUtil {
    private SerialPort serialPort = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private ReceiveThread mReceiveThread = null;
    private boolean isStart = false;

    public void openSearialPort() {
        try {
            serialPort = new SerialPort(new File("/dev/ttyS0"), 9600, 0);
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
            isStart = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        getSerialPort();
    }

    public void sendSerialPort(String data) {
        try {
            byte[] sendData = DataUtils.hexToByteArr(data);
            outputStream.write(sendData);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //开启接收数据的线程
    private void getSerialPort() {
        if (mReceiveThread == null) {
            mReceiveThread = new ReceiveThread();
        }
        mReceiveThread.start();
    }

    private class ReceiveThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (isStart){
                if(inputStream==null){
                    return;
                }
                byte[] readData=new byte[1024];
                try {
                   int size= inputStream.read(readData);
                    if (size > 0) {
                        String readString = DataUtils.bytesToHexString(readData);
                        EventBus.getDefault().post(readString);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
