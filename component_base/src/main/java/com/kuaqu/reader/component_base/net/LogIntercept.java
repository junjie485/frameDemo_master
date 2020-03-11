package com.kuaqu.reader.component_base.net;

import android.text.TextUtils;



import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static android.text.TextUtils.isEmpty;


public class LogIntercept implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String body = null;
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            body = buffer.readString(charset);
        }

//        LogDataUtils.showLog("原始请求数据", "请求方式:"+request.method()+" 请求接口:"+ request.url()+" 请求头:"+ request.headers()+" 请求参数:"+body);

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        String rBody = null;

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        rBody = buffer.clone().readString(charset);

//        LogDataUtils.showLog("原始响应数据","响应码:"+response.code()+" 响应接口:"+response.request().url()+" 响应参数:"+body+"==="+response.headers());
        LogDataUtils.showLog("原始响应数据"," 响应数据:"+rBody);
        return response;
    }
    public static String getSessionCookie(String cookieString) {
        if (!TextUtils.isEmpty(cookieString)) {
            String[] splitCookie = cookieString.split(";");
            String[] splitSessionId = splitCookie[0].split("=");
            cookieString = splitSessionId[1];
            return cookieString;
        }
        return "";
    }
}
