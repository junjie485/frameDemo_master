package com.kuaqu.reader.component_base.net;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogDataUtils {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");//换行符
    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.e(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.e(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    //json规范化打印.而且不会有字符超过4K显示不全的问题。
    public static void printJson(String tag, String msg, String headString) {
        String message;
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }
        printLine(tag, true);//头
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.e(tag, "║ " + line);
        }
        printLine(tag, false);//尾
    }

    //控制字符超过4k显示不全的问题
    public static void showLog(String tag, String msg){
        int showCount=2000;
        msg=unicodeToString(msg);
        if(msg.length() >showCount){
            String show = msg.substring(0, showCount);
            Log.e(tag, show);
            if((msg.length() - showCount)>showCount){//剩下的文本还是大于规定长度
                String partLog = msg.substring(showCount,msg.length());
                showLog(tag,partLog);
            }else{
                String surplusLog = msg.substring(showCount, msg.length());
                Log.e(tag, surplusLog);
            }
        }else{
            Log.e(tag, msg);
        }
    }

//Unicode转 汉字字符串
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;

    }

}
