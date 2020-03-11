package com.kuaqu.reader.component_base.net;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kuaqu.reader.component_base.base.BaseBean;

import rx.Subscriber;


/**
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo
 * @date 2016/12/12  14:48
 */

public class ProgressSubscriber<T extends BaseBean> extends Subscriber<T> {

    private ResultListener<T> mListener;
    private Context mContext;


    public ProgressSubscriber(ResultListener<T> listener, Context context) {
        this.mListener = listener;
        this.mContext = context;

    }


//可以加入加载中效果

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(mContext, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {
        if (mListener != null) {
            if (t.getResult().equals("0")) {
                mListener.onNext(t);
            } else if (t.getResult().equals("-1")) {
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                mContext.startActivity(intent);
            }
        }
    }
}
