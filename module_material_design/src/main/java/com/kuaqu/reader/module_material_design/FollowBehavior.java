package com.kuaqu.reader.module_material_design;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/*
* layoutDependsOn()：确定使用Behavior的View要依赖的View的类型
 onDependentViewChanged()：当被依赖的View状态改变时回调
*onMeasureChild()：测量使用Behavior的View尺寸
onLayoutChild()：确定使用Behavior的View位置
onStartNestedScroll()：嵌套滑动开始（ACTION_DOWN），确定Behavior是否要监听此次事件
onStopNestedScroll()：嵌套滑动结束（ACTION_UP或ACTION_CANCEL）
onNestedScroll()：嵌套滑动进行中，要监听的子 View的滑动事件已经被消费
onNestedPreScroll()：嵌套滑动进行中，要监听的子 View将要滑动，滑动事件即将被消费（但最终被谁消费，可以通过代码控制）
*
* */
public class FollowBehavior extends CoordinatorLayout.Behavior<TextView> {

    public FollowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
/*
* 确定使用Behavior的View要依赖的View的类型
*/
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof Button;
    }
/*
* 当被依赖的View状态改变时回调
*/
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        child.setX(dependency.getX()+200);
        child.setY(dependency.getY()+200);
        return true;
    }
}


