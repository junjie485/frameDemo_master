package com.kuaqu.reader.module_common_ui.activity;

import android.Manifest;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.utils.PermissionListener;
import com.kuaqu.reader.component_base.utils.PermissionsUtil;
import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.utils.RectOnCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback {
    private SurfaceView sfv;
    private SurfaceHolder holder;
    private Camera camera;
    private ImageView img;
    private RectOnCamera rectOnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
               initView();
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {

            }
        }, Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);


    }

    private void initView() {
        sfv = findViewById(R.id.sfv);
        img = findViewById(R.id.img);
        holder = sfv.getHolder();
        holder.addCallback(CameraActivity.this);
    }

    public void startCamera(View view) {
        Camera.Parameters parameters = camera.getParameters();
        //图片的格式
        parameters.setPictureFormat(ImageFormat.JPEG);
        //预览的大小是多少
        parameters.setPreviewSize(800, 400);
        //设置对焦模式，自动对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                String path = "";
                if ((path = saveFile(bytes)) != null) {
                    //解决拍摄图片旋转90的问题
                    setPictureDegreeZero(path);
                    Log.e("图片", "---->" + path);
//                    img.setImageURI(Uri.fromFile(new File(path)));
                    Glide.with(CameraActivity.this).load(Uri.fromFile(new File(path)))
                            .apply(new RequestOptions().placeholder(R.color.gray)
                                    .error(R.color.gray)
                                    .transforms(new CircleCrop())//圆形图片
                                    .diskCacheStrategy(DiskCacheStrategy.NONE))
                            .into(img);
                }
                //实现连续拍多张的效果
                camera.startPreview();
            }
        });
    }

    public static void setPictureDegreeZero(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            // 修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
            // 例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_90));
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //保存临时文件的方法
    private String saveFile(byte[] bytes) {
        File file = null;
        try {
            File dir=new File(Environment.getExternalStorageDirectory().getPath()+File.separator+"loadPicture" );
            if(!dir.exists()){
                dir.mkdirs();
            }
            file = new File(Environment.getExternalStorageDirectory().getPath()+"/loadPicture",System.currentTimeMillis()+"tt001.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            camera = Camera.open();
            //开启前置相机CameraInfo.CAMERA_FACING_FRONT。默认后置CameraInfo.CAMERA_FACING_BACK
//            camera=  Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            camera.setDisplayOrientation(90);//相机预览方向默认是0，横向。90是竖屏
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //停止预览效果
        camera.stopPreview();
        //重新设置预览效果
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (camera != null) {
            camera.release();
        }
    }


}
