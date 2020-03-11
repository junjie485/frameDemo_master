package com.kuaqu.reader.component_base.imgtransform;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.squareup.picasso.Transformation;
/*
* picasso
* 圆角图片
* 注意：设置圆角时，要使用resize(x,y)重新设置宽高，否则设置圆角可能无效
*
*/
public class CircleCornerForm  implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        //此处为图片原本宽高，而非控件设置的宽高，可以设置resize(x,y)来重新设置宽高，就有效了。
        int widthLight = source.getWidth();
        int heightLight = source.getHeight();
        Log.e("图片宽高",widthLight+"--->"+heightLight);
        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Paint paintColor = new Paint();
        paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);

        RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

        canvas.drawRoundRect(rectF, widthLight / 5, heightLight / 5, paintColor);

        Paint paintImage = new Paint();
        paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(source, 0, 0, paintImage);
        source.recycle();
        return output;
    }

    @Override
    public String key() {
        return "roundcorner";
    }

}

