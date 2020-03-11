package com.kuaqu.reader.module_common_ui.utils;

import android.util.Log;

public class Mobile {
    /*
    * 充电
    */
    public void inputPower(V5Power v5Power){
        int power=v5Power.provideV5Power();
        System.out.println("手机（客户端）：我需要5V电压充电，现在是-->" + power + "V");
    }
}
