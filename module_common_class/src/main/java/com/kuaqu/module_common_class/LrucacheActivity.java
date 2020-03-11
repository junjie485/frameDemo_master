package com.kuaqu.module_common_class;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.kuaqu.module_common_class.utils.ImageDownloader;
import com.kuaqu.reader.component_base.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.http.Url;

/*
 * 通过点击查看日志比较网络加载，和缓存加载
 * */
public class LrucacheActivity extends BaseActivity {
    public static final String TAG = "TextDownload";
    private ImageView imgIv;
    private ImageDownloader imageDownloader = new ImageDownloader();
    private String bitmapUrl = "http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg";
    private Thread thread;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "hanlder handleMessage: " + Thread.currentThread().getName());
            switch (msg.what) {
                case 1:
                    imgIv.setImageBitmap((Bitmap) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrucache);
        imgIv = findViewById(R.id.imgIv);


    }

    public void onShowImage(View view) {
        Bitmap bitmap = imageDownloader.getBitmapFromMemCache("bitmap");
        if (bitmap == null) {
            new Thread(mRunnable).start();
        } else {
            imgIv.setImageBitmap(bitmap);
        }
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "run: " + Thread.currentThread().getName());
            Bitmap bitmap = null;
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            try {
                URL url = new URL(bitmapUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    imageDownloader.addBitmapToMemory("bitmap", bitmap);
                    handler.obtainMessage(1, bitmap).sendToTarget();
                }
            } catch (Exception e) {
            }
        }
    };

    public void onCompress(View view) {
        Bitmap bit = BitmapFactory.decodeResource(getResources(), R.mipmap.anim5);
        Log.e("原图尺寸", "压缩前图片的大小" + (bit.getByteCount() / 1024 ) + "kb宽度为" + bit.getWidth() + "高度为" + bit.getHeight());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 2;//采样率，宽高个缩小1/2，大小缩小1/4
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.anim5,options);
        Log.e("原图尺寸", "压缩后图片的大小" + (bitmap.getByteCount() / 1024 ) + "kb宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
    }
}
