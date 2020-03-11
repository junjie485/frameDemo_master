package com.kuaqu.reader.module_my_view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_my_view.R;
import com.kuaqu.reader.module_my_view.utils.TimerTextView;

public class TimerActivity extends BaseActivity {
    private TimerTextView timerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timerTextView=findViewById(R.id.timer_text_view);
        long[] times = {0,10,5,30};
        timerTextView.setTimes(times);
    }
    public void onStartTimer(View view){
        if(!timerTextView.isRun()){
            timerTextView.beginRun();
        }

    }
    public void onStopTimer(View view){
        if(timerTextView.isRun()){
            timerTextView.stopRun();
        }

    }
}
