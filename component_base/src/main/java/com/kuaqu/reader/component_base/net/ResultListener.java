package com.kuaqu.reader.component_base.net;

/**
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo
 * @date 2016/12/12  14:48
 */

public interface ResultListener<T> {
    void onNext(T t);
}
