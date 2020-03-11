package com.kuaqu.reader.component_base.net;

import com.kuaqu.reader.component_base.base.BaseBean;
import rx.Observable;



public class ApiFactory extends RetrofitUtil{

    public static ApiFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }
    private static class SingletonHolder {
        private static final ApiFactory INSTANCE = new ApiFactory();
    }
    //实例
    public void login(ProgressSubscriber<BaseBean> subscriber, String username, String password){
        Observable observable = mApiService.login(username,password);
        toSubscribe(observable, subscriber);
    }
    //使用方式：
/*ApiFactory.getInstance().login(new ProgressSubscriber<LoginBean>(new ResultListener<LoginBean>() {
        @Override
        public void onNext(LoginBean login) {
            if(!login.getResult().equals("404")){
                goToMain();
            }else {
                showToastMessage(login.getMsg());
            }
        }
    },this),userName,passward);*/
}


