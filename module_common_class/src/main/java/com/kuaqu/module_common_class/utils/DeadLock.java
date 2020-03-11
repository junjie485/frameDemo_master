package com.kuaqu.module_common_class.utils;

public class DeadLock  implements Runnable{
    private static  Object object1=new Object();
    private static  Object object2=new Object();
    private boolean flag=true;

    public DeadLock(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if(flag){
            synchronized (object1){
                System.out.println(Thread.currentThread().getName() + "已经锁住obj1");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object2){
                    // 执行不到这里
                    System.out.println("1秒钟后，"+Thread.currentThread().getName() + "锁住obj2");
                }
            }
        }else {
            synchronized (object2){
                System.out.println(Thread.currentThread().getName() + "已经锁住obj2");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(object1){
                    // 执行不到这里
                    System.out.println("1秒钟后，"+Thread.currentThread().getName() + "锁住obj1");
                }
            }
        }


    }
}
