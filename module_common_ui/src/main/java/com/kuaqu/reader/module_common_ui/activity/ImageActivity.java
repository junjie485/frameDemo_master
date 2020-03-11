package com.kuaqu.reader.module_common_ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.utils.ImgUtils;
import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.utils.Book;

public class ImageActivity extends BaseActivity {
    private ImageView image1,image2,image3,image4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgae);
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        image3=findViewById(R.id.image3);
        image4=findViewById(R.id.image4);

        ImgUtils.showResourceImg(this,R.mipmap.testimg,image1);//本地资源
        ImgUtils.showCircleImg(this,"http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg",image2);//圆形图片
        ImgUtils.showImg(this,"http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg",image3);//正常显示
        ImgUtils.showCornerImg(this,"http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg",image4,100,100);//圆角图片
    }
}
