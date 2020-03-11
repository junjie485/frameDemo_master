package com.kuaqu.module_common_class

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        getNum()
        getStringMathod()
        getForMathod()
        getClassMathod()

    }

    private fun getClassMathod() {
        var site = Person("Person",50)
        site.name = "Jerry"
        site.age = 20
        Log.e("类", "-->" + site.name + ":" + site.age + ":" + Person("").Data())
        var s = Student()
        Log.e("重写", "" + s.study())
    }

    private fun getForMathod() {
        for (i in 1..4) {
            Log.e("输出", "" + i)
        }
        for (i in 1..4 step 2) {
            Log.e("输出2", "" + i)
        }
        for (i in 4 downTo 1 step 2) {
            Log.e("输出3", "" + i)
        }
        for (i in 1 until 4) {
            Log.e("输出4", "" + i)
        }
    }

    private fun getStringMathod() {
        var a = 1
        val s1 = "a is $a"
        a = 2
        val s2 = "${s1.replace("is", "was")},but now is $a"
        Log.e("字符串打印", "-->" + s2)
    }

    private fun getNum() {
        var a = 5
        var b: Float = 10.0f
        Log.e("求和", "-->" + (a + b))
    }
  //open关键字表示该类可继承，主构造器跟在类的后面
    open class Person constructor(name: String) {
        init {
            Log.e("类", "初始化类名：$name")
        }
      //次构造函数通过this关键字来代理主构造函数
      constructor(name: String,age:Int):this(name){
        Log.e("构造","构造传入的年龄$age")
      }
        //字符串的set()和get()方法
        var name = "tom"
            get() = field.toUpperCase()// 将变量赋值后转换为大写
        var age = 16
            set(value) {
                if(value<10){
                    field = value
                }else{
                    field=-1
                }
            }
        open fun study() {
            Log.e("重写", "我毕业了")
        }
        //内部类：内部类可以访问外部类成员属性和成员函数。
        inner class Data {
            fun foo() = 2
        }

    }

    class Student : Person("Person") {
        override fun study() {
            Log.e("重写", "我在读大学")
        }
    }
}
