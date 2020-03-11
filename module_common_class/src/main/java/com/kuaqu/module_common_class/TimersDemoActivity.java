package com.kuaqu.module_common_class;

import android.os.SystemClock;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.Toast;

import com.kuaqu.reader.component_base.base.BaseActivity;

public class TimersDemoActivity extends BaseActivity implements View.OnClickListener,Chronometer.OnChronometerTickListener {
    private Chronometer chronometer;
    private Button btn_start,btn_stop,btn_base,btn_format;
    private CalendarView calendarTv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_demo);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        btn_start =findViewById(R.id.btnStarts);
        btn_stop = findViewById(R.id.btnStop);
        btn_base = findViewById(R.id.btnReset);
        btn_format = findViewById(R.id.btn_format);

        chronometer.setOnChronometerTickListener(this);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_base.setOnClickListener(this);
        btn_format.setOnClickListener(this);
        calendarTv=findViewById(R.id.calendarTv);

        calendarTv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                Toast.makeText(TimersDemoActivity.this,"您选择的时间是："+ year + "年" + month + "月" + dayOfMonth + "日",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btnStarts) {
            chronometer.start();
        }else if (i == R.id.btnStop) {
            chronometer.stop();
        }else if (i == R.id.btnReset) {
            chronometer.setBase(SystemClock.elapsedRealtime());// 复位
        }else if (i == R.id.btn_format) {
            chronometer.setFormat("Time：%s");// 更改时间显示格式
        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        String time = chronometer.getText().toString();
        if(time.equals("00:00")){
            Toast.makeText(TimersDemoActivity.this,"时间到了~",Toast.LENGTH_SHORT).show();
        }
    }
}
