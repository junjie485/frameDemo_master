package com.kuaqu.reader.module_my_view.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TimerTextView extends TextView implements Runnable{
    private long mday, mhour, mmin, msecond;//天，小时，分钟，秒
    private boolean run=false; //是否启动了

    public TimerTextView(Context context) {
        super(context);
    }

    public TimerTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTimes(long[] times) {
        mday = times[0];
        mhour = times[1];
        mmin = times[2];
        msecond = times[3];

    }

    private void ComputeTime() {
        msecond--;
        if (msecond < 0) {
            mmin--;
            msecond = 59;
            if (mmin < 0) {
                mmin = 59;
                mhour--;
                if (mhour < 0) {
                    // 倒计时结束，一天有24个小时
                    mhour = 23;
                    mday--;

                }
            }

        }

    }

    public boolean isRun() {
        return run;
    }

    public void beginRun() {
        this.run = true;
        run();
    }

    public void stopRun(){
        this.run = false;
    }


    @Override
    public void run() {
        if(run){
            ComputeTime();
            String strTime= mday +"天:"+ mhour+"小时:"+ mmin+"分钟:"+msecond+"秒";
            this.setText(strTime);
            postDelayed(this, 1000);
        }else {
            removeCallbacks(this);
        }

    }
}
