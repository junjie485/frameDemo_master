package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.kuaqu.reader.module_my_view.R;

public class CustomImageView extends View {
    private final String TAG="自定义";
    private final int IMAGE_SCALE_FITXY = 1;
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private Bitmap mImage;
    private int mImageScale;
    private Rect mBound;
    private Paint mPaint;
    private Rect rect;
    private int mWidth;
    private int mHeight;


    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, 0, 0);
        mTitleText = array.getString(R.styleable.CustomImageView_titleText);
        mTitleTextColor = array.getColor(R.styleable.CustomImageView_titleTextColor, Color.BLACK);
        mTitleTextSize = array.getDimensionPixelSize(R.styleable.CustomImageView_titleTextSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mImage = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.CustomImageView_image, 0));
        mImageScale = array.getInt(R.styleable.CustomImageView_imageScaleType, 0);
        array.recycle();
        Log.e(TAG, "构造方法");
//        getInfo(context, attrs, 0);
        mBound = new Rect();
        mPaint = new Paint();
        rect = new Rect();
        mPaint.setTextSize(mTitleTextSize);
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);//获取字符串长度
//        mPaint.measureText(mTitleText);//获取字符串长度
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("自定义", "构造方法2");//这个方法不会调用的
    }

    private void getInfo(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            if (attr == R.styleable.CustomImageView_image) {
                mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));

            } else if (attr == R.styleable.CustomImageView_imageScaleType) {
                mImageScale = a.getInt(attr, 0);

            } else if (attr == R.styleable.CustomImageView_titleText) {
                mTitleText = a.getString(attr);

            } else if (attr == R.styleable.CustomImageView_titleTextColor) {
                mTitleTextColor = a.getColor(attr, Color.BLACK);

            } else if (attr == R.styleable.CustomImageView_titleTextSize) {
                mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                        16, getResources().getDisplayMetrics()));

            }
        }
        a.recycle();

    }
    //一般自定义view，测量时会用到getPadding，来测量宽高。自定义viewGroup，会用到getLeftMargin,来测量宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure");
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        Log.e(TAG,measureWidth+":::"+measureHeight);

        int width = 0, height = 0;
        int imgWidth = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
        int textWidth = getPaddingLeft() + getPaddingRight() + mBound.width();
        width = Math.max(imgWidth, textWidth);
        height = getPaddingBottom() + getPaddingTop() + mImage.getHeight() + mBound.height();
        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth : width, (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        Log.e(TAG,mWidth+"--->"+mHeight);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.top = getPaddingTop();
        rect.right = mWidth - getPaddingRight();
        rect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTitleTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        if (mBound.width() > mWidth) {//当前设置的宽度小于字体需要的宽度，将字体改为xxx..
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitleText, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

        } else {
            canvas.drawText(mTitleText, mWidth/2 - mBound.width()/2, mHeight - getPaddingBottom(), mPaint);
        }
        rect.bottom -= mBound.height();
        if (mImageScale == IMAGE_SCALE_FITXY) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else {
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mBound.height()) / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, rect, mPaint);

        }
    }
}
