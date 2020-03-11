package com.kuaqu.reader.module_common_ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.utils.SerialPortFinder;

import java.io.ByteArrayOutputStream;

public class PictureCompressActivity extends BaseActivity {
    private TextView textView;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_compress);
        textView=findViewById(R.id.textView);
        bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.anim2);
        textView.setText("压缩后图片的大小" + (bitmap.getByteCount() / 1024 )
                + "K宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
    }
    //质量压缩：图片大小、宽高、所占内存不变，变化的只是图片的位深和透明度。适合用于传递二进制图片数据。不适合显示图片，png是无损的不能压缩
    public void qualityCompress(View view){
        BitmapFactory.Options options=new BitmapFactory.Options();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[] aa=stream.toByteArray();
        Bitmap bm=BitmapFactory.decodeByteArray(aa,0,aa.length);
        textView.setText("压缩后图片的大小" + (bm.getByteCount() / 1024 )
                + "K宽度为" + bm.getWidth() + "高度为" + bm.getHeight()
                + "bytes.length=  " + (aa.length / 1024) + "KB"
                + "quality=" + 80);
    }

    //采样率压缩，大小变为原来的1/4。降低分辨率
    public void rateCompress(View view){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=2;
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.anim2,options);
        textView.setText("压缩后图片的大小" + (bitmap.getByteCount() / 1024)
                + "K宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
    }
    //缩放压缩：宽高各缩放1/2，大小缩放1/4
    public void scaleCompress(View view){
        Matrix matrix=new Matrix();
        matrix.setScale(0.5f,0.5f);
        Bitmap bit=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        textView.setText("压缩后图片的大小" + (bit.getByteCount() / 1024)
                + "K宽度为" + bit.getWidth() + "高度为" + bit.getHeight());
    }
    //rgb压缩：rgb-888（默认）一个像素占4个字节，rgb-565一个像素占2个字节
    public void rgbCompress(View view){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPreferredConfig= Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.anim2,options);
        textView.setText("压缩后图片的大小" + (bitmap.getByteCount() / 1024)
                + "K宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());

    }


}
