package com.kuaqu.reader.module_my_view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kuaqu.reader.module_my_view.R;
import com.kuaqu.reader.module_my_view.utils.StereoView;

public class StereActivity extends AppCompatActivity {
    private StereoView stereoView;
    private int translateY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stere);
        stereoView = (StereoView) findViewById(R.id.stereoView);

        stereoView.setStartScreen(1);
        stereoView.post(new Runnable() {
            @Override
            public void run() {
                int[] location = new int[2];
                stereoView.getLocationOnScreen(location);
                translateY = location[1];
            }
        });
        stereoView.setiStereoListener(new StereoView.IStereoListener() {
            @Override
            public void toPre(int curScreen) {
                System.out.println("跳转到前一页 " + curScreen);
            }

            @Override
            public void toNext(int curScreen) {
                System.out.println("跳转到下一页 " + curScreen);
            }
        });
    }
}
