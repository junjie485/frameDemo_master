package com.kuaqu.reader.component_base.net;



import com.kuaqu.reader.component_base.base.BaseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo
 * @date 2016/12/09  17:04
 */

public interface ApiService {

    //登录
    @FormUrlEncoded
    @POST("/login/index")
    Observable<BaseBean> login(@Field("username") String username, @Field("password") String password);




}
