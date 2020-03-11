package com.kuaqu.module_common_class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kuaqu.module_common_class.utils.Book;
import com.kuaqu.module_common_class.utils.DeadLock;
import com.kuaqu.module_common_class.utils.ReflectClass;
import com.kuaqu.module_common_class.utils.Task;
import com.kuaqu.reader.component_base.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JavaBaseActivity extends BaseActivity {
    private static final String TAG = "JavaBaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_base);
        TextView normalText = findViewById(R.id.normalText);
        TextView robotoText = findViewById(R.id.robotoText);
        TextView droidText = findViewById(R.id.droidText);
        normalText.setText("岁月静好abcdefg");
        //原先android默认字体
        robotoText.setTypeface(Typeface.createFromAsset(getAssets(), "DroidSans-Bold.ttf"));
        robotoText.setText("岁月静好abcdefg");
        //现在android默认字体
        droidText.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf"));
        droidText.setText("岁月静好abcdefg");
    }

    public void excuteTest(View view) {
//        hashMapTest();
//        TheadTest();
//        ThreadPoolTest();
//        ReflectTest();
//        DeadLockTest();
//        sortListTest();
//        ThreadLocalTest();
//            StringTest();
          FutureTaskTest();

    }


    //Callable 与 Runnable:前者有结果的返回值，或者没有返回值。
    // Future<V>接口是用来获取异步计算结果的,通过调用get方法得到结果，这个方法会产生阻塞，直到有结果返回
    //FutureTask除了实现了Future接口外还实现了Runnable接口，因此FutureTask也可以直接提交给Executor执行。
    private void FutureTaskTest() {
        Task task=new Task();
        FutureTask<Integer> futureTask=new FutureTask<Integer>(task);
        ExecutorService service= Executors.newCachedThreadPool();
        service.execute(futureTask);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程在执行任务");
        try {
            if(futureTask.get()!=null){
                System.out.println("task运行结果"+futureTask.get());
            }else {
                System.out.println("future.get()未获取到结果");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("所有任务执行完毕");
    }

    //String a=""直接赋值的方式得到一个常量字符串，存在于常量池中，相同内容的字符在常量池中只有一个。
    //String b=new String("")创建的字符串不是常量是实例对象。会在堆内存中创建存储空间。
    private void StringTest() {
        String a="";
        String b=new String("");
        Log.e("字符串相等",""+(a==b));
    }


    private void ThreadLocalTest() {
        //局部变量被共享，是因为虽然是两个线程，但是用的是同一个对象。如何独立呢，threadLocal
        //ThreadLocal为每个线程的中并发访问的数据提供一个副本
//        TestRunable runable=new TestRunable();
        TestRunable2 runable=new TestRunable2();
        Thread thread1=new Thread(runable,"Thread1");
        Thread thread2=new Thread(runable,"Thread2");
        thread1.start();
        thread2.start();
    }
    class TestRunable implements Runnable{
        int local=0;
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Thread.currentThread().getName().equals("Thread1")){
                local++;
            }else if(Thread.currentThread().getName().equals("Thread2")){
                Log.e("local是否被共享",""+local);
            }
        }
    }
    class TestRunable2 implements Runnable{
        ThreadLocal<Integer> threadLocal=new ThreadLocal<>();
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadLocal.set(new Integer(0));
            if(Thread.currentThread().getName().equals("Thread1")){
                threadLocal.set(new Integer(threadLocal.get()+1));
                Log.e("local11",""+threadLocal.get());
            }else if(Thread.currentThread().getName().equals("Thread2")){
                Log.e("local22",""+threadLocal.get());
            }
        }
    }


    //先按书名名字长短正序排序，如果相同，则判断作者名长短，正序排序
    private void sortListTest() {
        Book book = new Book();
        book.setName("人类发展史");
        book.setAuthor("james");
        Book book1 = new Book();
        book1.setName("人类进化繁衍史");
        book1.setAuthor("jack");
        Book book2 = new Book();
        book2.setName("人类退化史");
        book2.setAuthor("jode");
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        bookList.add(book1);
        bookList.add(book2);
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                int ret = o1.getName().length() - o2.getName().length();
                if (ret > 0) {//o1大于o2,返回1,排在后面
                    return 1;
                } else if (ret < 0) {//o1小于o2，返回-1，排在前面
                    return -1;
                } else {
                    int rr = o1.getAuthor().length() - o2.getAuthor().length();
                    if (rr > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

            }
        });
        System.out.println(bookList);
    }

    //    类锁和对象锁不同，synchronized修饰不加static的方法，锁是加在单个对象上，
// 不同的对象没有竞争关系；修饰加了static的方法，锁是加载类上，这个类所有的对象竞争一把锁。
    private void DeadLockTest() {
        //死锁的产生
        Thread t1 = new Thread(new DeadLock(true), "线程1");
        Thread t2 = new Thread(new DeadLock(false), "线程2");
        t1.start();
        t2.start();
        //如何避免死锁：1.顺序加锁 2.加锁时限 3死锁检测（产生死锁大部分原因是嵌套封锁）
        //1.顺序加锁(在线程1未释放锁前，线程2等待)
 /*       Thread t1=new Thread(new DeadLock(true),"线程1");
        Thread t2=new Thread(new DeadLock(true),"线程2");
        t1.start();
        t2.start();*/


    }

    private void ReflectTest() {
        ReflectClass.reflectNewInstance();
        ReflectClass.reflectPrivateConstructor();
        ReflectClass.reflectPrivateField();
        ReflectClass.reflectPrivateMethod();
        ReflectClass.getZenMode();
        ReflectClass.shutDown();//需要权限
        ReflectClass.shutdownOrReboot(true, true);//需要权限
    }

    //先创建5个核心线程，然后其余线程先添加到任务队列（5）中。队列满了后，由于最大线程数为10，开始再创建5个线程，达到最大线程数
    //当核心线程执行完毕，开始执行任务队列里的线程。
    private void ThreadPoolTest() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
        }
        executor.shutdown();
    }

    /*
     * wait与notify、sleep：wait使当前线程阻塞，前提是必须先获得锁，所以只能在synchronized锁范围内里使用wait、notify/notifyAll方法，而sleep可以在任何地方使用。
     * wait进入等待状态会释放资源锁，而sleep会持续占据锁。
     * 实例：多个线程同时操作一个对象，轮流执行
     * */
    private void TheadTest() {
        Print print = new Print();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 26; i++) {
                    print.printNum();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 26; i++) {
                    print.printChar();
                }
            }
        }).start();

    }

    private void hashMapTest() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "史记");
        hashMap.put(2, "资治通鉴");
        hashMap.put(3, "明史");
        hashMap.put(4, "二十四史");
        //遍历(键值对遍历)
        Set<Map.Entry<Integer, String>> entrySet = hashMap.entrySet();
        for (Map.Entry<Integer, String> entry : entrySet) {
            Log.e(TAG, entry.getKey() + "===" + entry.getValue());
        }
        //遍历(键遍历)
        Set<Integer> set = hashMap.keySet();
        for (Integer integer : set) {
            Log.e(TAG, integer + "===" + hashMap.get(integer));
        }
        //遍历(值遍历)
        Collection values = hashMap.values();
        for (Object value : values) {
            Log.e(TAG, "===" + value);
        }
    }


    public class Print {
        private int flag = 1;//信号量。当值为1时打印数字，当值为2时打印字母
        private int count = 1;

        public synchronized void printNum() {
            if (flag != 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.e(TAG, "" + (2 * count - 1));
            Log.e(TAG, "" + (2 * count));
            flag = 2;
            notify();//随机唤醒一个线程
        }

        private synchronized void printChar() {
            if (flag != 2) {
                //打印字母
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.e(TAG, "" + ((char) (count - 1 + 'A')));
            count++;//当一轮循环打印完之后，计数器加1
            flag = 1;
            notify();
        }
    }

    private int allMoney = 10000;

    class MyTask implements Runnable {
        private int taskNum;

        public MyTask(int taskNum) {
            this.taskNum = taskNum;
        }

        @Override
        public void run() {
            Log.e(TAG, "正在执行task " + taskNum);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "task " + taskNum + "执行完毕");
        }
    }
}
