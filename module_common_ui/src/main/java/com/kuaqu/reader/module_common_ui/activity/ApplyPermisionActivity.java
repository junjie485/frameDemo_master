package com.kuaqu.reader.module_common_ui.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kuaqu.reader.component_base.base.BaseActivity;
import com.kuaqu.reader.component_base.utils.PermissionListener;
import com.kuaqu.reader.component_base.utils.PermissionsUtil;
import com.kuaqu.reader.module_common_ui.R;
import com.kuaqu.reader.module_common_ui.utils.ArmEquip;
import com.kuaqu.reader.module_common_ui.utils.BlueGemDecorator;
import com.kuaqu.reader.module_common_ui.utils.Book;
import com.kuaqu.reader.module_common_ui.utils.IEquip;
import com.kuaqu.reader.module_common_ui.utils.JSONFormatter;
import com.kuaqu.reader.module_common_ui.utils.Mobile;
import com.kuaqu.reader.module_common_ui.utils.ObjectFor3D;
import com.kuaqu.reader.module_common_ui.utils.Observer1;
import com.kuaqu.reader.module_common_ui.utils.Observer2;
import com.kuaqu.reader.module_common_ui.utils.RingEquip;
import com.kuaqu.reader.module_common_ui.utils.TestBuild2;
import com.kuaqu.reader.module_common_ui.utils.TestUtils;
import com.kuaqu.reader.module_common_ui.utils.Testbuild;
import com.kuaqu.reader.module_common_ui.utils.V220Power;
import com.kuaqu.reader.module_common_ui.utils.V5Power;
import com.kuaqu.reader.module_common_ui.utils.V5PowerAdapter;
import com.kuaqu.reader.module_common_ui.utils.XMLFormatter;
import com.kuaqu.reader.module_common_ui.utils.YellowGemDecorator;

public class ApplyPermisionActivity extends BaseActivity {
    private Button btn_camera, btn_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_permision);
        initView();
    }

    private void initView() {
        btn_camera = findViewById(R.id.btn_camera);
        btn_view = findViewById(R.id.btn_view);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsUtil.requestPermission(getApplicationContext(), new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {
                        Toast.makeText(ApplyPermisionActivity.this, "访问摄像头", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                        Toast.makeText(ApplyPermisionActivity.this, "用户拒绝了访问摄像头", Toast.LENGTH_LONG).show();
                    }
                }, Manifest.permission.CAMERA);
            }
        });
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsUtil.requestPermission(ApplyPermisionActivity.this, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {
                        Toast.makeText(ApplyPermisionActivity.this, "访问", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {
                        Toast.makeText(ApplyPermisionActivity.this, "用户拒绝了访问", Toast.LENGTH_LONG).show();
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        });
    }

    public void generricsMethod(View view) {
        Integer[] intArray = {1, 2, 3, 4, 5};
        Double[] doubleArray = {1.1, 2.2, 3.3, 4.4};
        Character[] charArray = {'H', 'E', 'L', 'L', 'O'};
        Log.e("数组", "\n整形数组为");
        printArray(intArray);
        Log.e("数组", "\n双精度型数组元素为");
        printArray(doubleArray);
        Log.e("数组", "\n字符型数组元素为");
        printArray(charArray);
    }

    public void generricsClass(View view) {
        Box<Integer> integerBox = new Box<Integer>();
        Box<String> stringBox = new Box<String>();
        integerBox.add(new Integer(10));
        stringBox.add(new String("啦啦菜鸟"));
        Log.e("数组", "" + integerBox.get());
        Log.e("数组", "" + stringBox.get());
    }

    public void generricsLimit(View view) {
        Log.e("数组", "\n整形数字中3,4,5的最大值为" + maxNum(3, 4, 5));
        Log.e("数组", "\n双精度数字中6.6, 8.8, 7.7的最大值为" + maxNum(6.6, 8.8, 7.7));
        Log.e("数组", "\n字符中pear,apple,orange的最大值为" + maxNum("pear", "apple", "orange"));
    }

    public void observerMode(View view) {
        ObjectFor3D objectFor3D=new ObjectFor3D();
        Observer1 observer1=new Observer1(objectFor3D);
        Observer2 observer2=new Observer2(objectFor3D);
        objectFor3D.setMsg("本期的号码是：123");
        objectFor3D.notifyObserver();

    }

    //通用方法，可以打印各类型数组元素
    //所有泛型方法声明都有一个类型参数声明部分（由尖括号分隔），该类型参数声明部分在方法返回类型之前（在下面例子中的<E>）。
    public <T> void printArray(T[] inputArray) {
        for (T eme : inputArray) {
            Log.e("数组", "" + eme);
        }
    }

    //有界的类型参数:
    // 可能有时候，你会想限制那些被允许传递到一个类型参数的类型种类范围。例如，一个操作数字的方法可能只希望接受Number或者Number子类的实例。这就是有界类型参数的目的。
    //要声明一个有界的类型参数，首先列出类型参数的名称，后跟extends关键字，最后紧跟它的上界。
    public <T extends Comparable<T>> T maxNum(T x, T y, T z) {
        T max = x;
        if (y.compareTo(max) > 0) {
            max = y;
        }
        if (z.compareTo(y) > 0) {
            max = z;
        }
        return max;
    }

     //泛型类的声明和非泛型类的声明类似，除了在类名后面添加了类型参数声明部分。
    //和泛型方法一样，泛型类的类型参数声明部分也包含一个或多个类型参数，参数间用逗号隔开。一个泛型参数，也被称为一个类型变量，
    // 是用于指定一个泛型类型名称的标识符。因为他们接受一个或多个参数，这些类被称为参数化的类或参数化的类型。
    private class Box<T> {
        private T t;

        public void add(T t) {
            this.t = t;
        }

        public T get() {
            return t;
        }

    }

    public void adapterMode(View view) {
        Mobile mobile = new Mobile();//手机端和220v电压无需改变
        V5Power v5Power = new V5PowerAdapter(new V220Power()) ;
        mobile.inputPower(v5Power);
        //将手机所需的5v的电压，抽象成接口，然后创建适配器，实现该接口，并引入220v的工作电压，转化。
        Testbuild testbuild=new Testbuild();
        testbuild.setName("小明").setPwd("123456");
        System.out.println(testbuild.getName()+"----"+testbuild.getPwd());
        TestBuild2.Builder builder=new TestBuild2.Builder();
        TestBuild2 testBuild2= builder.setName("小华")
                .setPwd("654321")
                .build();
         System.out.println(testBuild2.getName()+"===="+testBuild2.getPwd());
    }
//  模板方法： 定义一个操作中的算法的骨架，而将一些步骤延迟到子类中，模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤
  //模板方法着重抽象的是步骤
    public void templateMode(View view){
        Book book = new Book();
        book.setBookName("Thinking in Java");
        book.setPages(880);
        book.setPrice(68);
        book.setAuthor("Bruce Eckel");
        book.setIsbn("9787111213826");
        XMLFormatter xmlFormatter = new XMLFormatter();
        String result = xmlFormatter.formatBook(book);
        System.out.println(result);
        JSONFormatter jsonFormatter = new JSONFormatter();
        result = jsonFormatter.formatBook(book);
        System.out.println(result);

    }
    //装饰者模式：当我们设计好了一个类，我们需要给这个类添加一些辅助的功能，并且不希望改变这个类的代码。
    //简单的说：在设计好的类中，新增辅助功能（即装饰者）,同时不改变原有类。
    //简单实现：创建角色类接口，构建角色类。创建装饰者接口，并继承角色类接口（注：角色类与装饰者沟通的关键），构建装饰者类，并在构造函数中，传入角色类接口。
    public void decorateMode(View view){
        System.out.println("一个镶嵌1颗蓝宝石，1颗黄宝石的屠龙刀");
        IEquip equip=new BlueGemDecorator(new YellowGemDecorator(new ArmEquip()));
        System.out.println("攻击力："+equip.caculateAttack());
        System.out.println("描述："+equip.description());
        System.out.println("一个镶嵌1颗蓝宝石，1颗黄宝石的圣战戒指");
        IEquip equip2=new BlueGemDecorator(new YellowGemDecorator(new RingEquip()));
        System.out.println("攻击力："+equip2.caculateAttack());
        System.out.println("描述："+equip2.description());

//        TestUtils utils=new TestUtils(this);
//        utils.showNotify();
    }
}
