package com.kuaqu.reader.module_my_view.utils;

import android.animation.TypeEvaluator;

import java.lang.reflect.TypeVariable;

public class CharEvaluator implements TypeEvaluator<Character> {
//    其中fraction表示加速器的数字进度，startValue表示数字区间的头，endValue表示数字区间的尾。
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
        int startInt  = (int)startValue;
        int endInt = (int)endValue;
        int curInt = (int)(startInt + fraction *(endInt - startInt));
        char result = (char)curInt;
        return result;

    }
}
