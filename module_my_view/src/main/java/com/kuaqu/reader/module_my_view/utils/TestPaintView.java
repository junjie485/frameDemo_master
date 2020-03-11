package com.kuaqu.reader.module_my_view.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kuaqu.reader.module_my_view.R;

//调用顺序：构造函数---》onFinishInflate----》onMeasure----》onSizeChanged----》onLayout----》onDraw
public class TestPaintView extends View{
    private Paint mPaint;

    //当不需要使用xml声明或者不需要使用inflate动态加载时候，实现此构造函数即可
    public TestPaintView(Context context) {
        super(context);
    }
    //注意：不要在构造函数中进行获取宽高的操作。可以在onSizeChange()函数中获取
    public TestPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    //获取控件自定义属性：首先在values文件夹下创建attrs.xml。以<declare-styleable>为根元素，值类型包括：
    //1.reference:引用资源ID。2.String:字符串。3.Color:颜色。4.boolean:布尔值。5.dimension:尺寸值。6.integer：整型  7.enum：枚举
    //接着开始获取。TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, 0, 0);
    //array.getString(R.styleable.CustomImageView_titleText):控件名_属性名
    //最后要释放：  array.recycle();
    private void init() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置线帽样式
        mPaint.setStrokeJoin(Paint.Join.ROUND);//设置连接处样式

//      mPaint.setAlpha(100);//取值0-255之间
//      mPaint.setStrokeMiter(90);//设置画笔倾斜度
//      mPaint.setPathEffect();//设置路径样式:ComposePathEffect(合并路径效果), CornerPathEffect(圆形拐角效果), DashPathEffect(虚线效果), DiscretePathEffect(离散路径效果), PathDashPathEffect(印章路径效果)

                 /*文字属性*/
//      mPaint.setFakeBoldText(true);//设置是否为粗体文字
//      mPaint.setStrikeThruText(true);//设置删除线效果
//      mPaint.setUnderlineText(true);//设置下划线效果
//      mPaint.setTextSkewX(0.25f);//设置错切(水平倾斜度)
//      mPaint.getTextBounds("你好",0,2,new Rect());//获取文字宽高，返回到rect矩形中
//      float width= mPaint.measureText("你好");//获取文字宽度


                /*图像属性*/
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0,
//                0, 0, 1, 0, 0,
//                0, 0, 0, 1, 0,
//        });
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));//设置图像滤镜
//        mPaint.setShadowLayer(2,5,5,Color.GRAY);//设置图像阴影，对图片无效
//        mPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.OUTER));//设置边缘发光效果.Blur.INNER——内发光、Blur.SOLID——外发光、Blur.NORMAL——内外发光、Blur.OUTER——仅显示发光效果。EmbossMaskFilter实现浮雕效果
//        mPaint.setShader(new BitmapShader());//BitmapShader(Bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)(图章工具)、LinearGradient()（渐变）、
//        mPaint.setXfermode();//设置图像混合模式



    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //在这里可以做一些初始化操作，如定义画笔
    }

    //主要是分发给子组件进行绘制,用于viewGroup。在ondraw方法之后调用
    //当viewGroup没有背景时，直接调用dispatchDraw方法跳过ondraw方法
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }



    //:void drawBitmap(Bitmap bitmap, float left, float top, Paint paint)
    //:void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint) Rect src：对原图片的裁剪区域.RectF dst：将（裁剪完的）原图片绘制到View控件上的区域
    //:void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) 涉及图像的平移，旋转、缩放、倾斜。
    //Matrix:缩放postScale(float sx, float sy)，postScale(float sx, float sy, float px(缩放中心), float py(缩放中心))。平移：postTranslate(float dx, float dy)
    //旋转： postRotate(float degrees)、postRotate(float degrees, float px(旋转中心), float py(旋转中心))。错切：postSkew(float kx(X轴倾斜值,大于0向左倾斜，小于0向右倾斜 ), float ky(Y轴倾斜值,大于0向下倾斜，小于0向上倾斜))、postSkew(float kx, float ky, float px(倾斜依据点X), float py(倾斜依据点Y))
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(10,10,mPaint);
        float pts[]={30,30,60,60};
//        int offset:集合中跳过的数值个数，注意不是点的个数！一个点是两个数值；
//        count:参与绘制的数值的个数，指pts[]里人数值个数，而不是点的个数，因为一个点是两个数值
        canvas.drawPoints(pts,0,4,mPaint);
        canvas.drawLine(50,5,100,5,mPaint);
        Rect rect=new Rect(100,100,400,400);
        canvas.drawRect(rect,mPaint);
        RectF rectf=new RectF(500,100,800,400);
        canvas.drawRoundRect(rectf,20,20,mPaint);//rect：RectF对象。ry：y方向上的圆角半径。ry：y方向上的圆角半径。
        canvas.drawCircle(200,600,200,mPaint);
        RectF f=new RectF(500,500,800,800);
        canvas.drawArc(f,0,90,true,mPaint);//rect：RectF对象。startAngle:起始角度（水平右侧是0度）。sweepAngle:扫过的角度(注：不是结束角度)。userCenter:是否有圆心
        ///////////////////////利用路径可以实现上述所有操作，区别点：path会设置一个顺逆时针方向

        Path path=new Path();
        path.moveTo(100,950);
        path.lineTo(100,1120);
        path.lineTo(400,1120);
        path.close();
        canvas.drawPath(path,mPaint);
        Path path1=new Path();
        RectF f1=new RectF(500,950,900,1120);
        float radii[] ={10,10,20,20,30,30,40,40};//利用路径可以绘制四个圆角不同的圆角矩形
        path1.addRoundRect(f1,radii, Path.Direction.CCW);
        canvas.drawPath(path1,mPaint);

        Path circlePath=new Path();
        circlePath.addCircle(200,1420,200, Path.Direction.CW);
        mPaint.setTextSize(35);
        canvas.drawTextOnPath("风萧萧兮易水寒，壮士一去兮不复返",circlePath,360,20,mPaint);//hOffset : 与路径起始点的水平偏移距离。vOffset : 与路径中心的垂直偏移量

//        mPaint.setShadowLayer(10,15,15,Color.GREEN);//设置字体阴影
        mPaint.setTextSize(35);
        mPaint.setUnderlineText(true);//下划线
        mPaint.setStrikeThruText(true);//删除线
        mPaint.setTextSkewX((float)-0.25);
        canvas.drawText("欢迎欢迎",50,900,mPaint);

        canvas.translate(400,1420);
        Path path2=getPath();
        CornerPathEffect cornerPathEffect=new CornerPathEffect(100);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setPathEffect(cornerPathEffect);
        canvas.drawPath(path2,paint);

        canvas.translate(0,200);
        Path path3=getPath();
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{20,10},0);
        paint.setPathEffect(dashPathEffect);
        canvas.drawPath(path3,paint);


    }
    private Path getPath() {
        Path path = new Path();
        // 定义路径的起点
        path.moveTo(0, 0);

        // 定义路径的各个点
        for (int i = 0; i <= 40; i++) {
            path.lineTo(i * 35, (float) (Math.random() * 150));
        }
        return path;
    }
//计算本控件的宽高,如果继承自原有控件，则一般不需要重写此方法
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
           int  widthMode=MeasureSpec.getMode(widthMeasureSpec);
           if(widthMode==MeasureSpec.EXACTLY){
               //如果控件在xml中宽高已经赋值或者match_parent，则调用该条件，直接获取宽高
           }else if(widthMode==MeasureSpec.AT_MOST||widthMode==MeasureSpec.UNSPECIFIED){
                //如果宽高是wrap_parent,需要获取控件内容的宽高+getpading，来获取控件宽高
           }
           //最后设置宽高
//           setMeasuredDimension(width,height);
    }
//    用于布局控件，对于不是继承ViewGroup的控件，一般不需要重写此方法
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //left：控件左边框距离父布局左部的距离
        //top：控件顶部距离父布局顶部的距离
        //right：控件有边框距离父布局左部的距离  ：：：注意
        //bottom:控件底边框距离父布局顶部的距离  ：：：注意
    }
}
