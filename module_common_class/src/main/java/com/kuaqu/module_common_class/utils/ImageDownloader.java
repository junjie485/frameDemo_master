package com.kuaqu.module_common_class.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

public class ImageDownloader {
    public static final String TAG="TextDownload";
    private LruCache<String,Bitmap> lruCache;

    public ImageDownloader() {
        long maxMemory=Runtime.getRuntime().maxMemory();
        int cacheSize = (int) (maxMemory / 8);
        lruCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    // 把Bitmap对象加入到缓存中
    public void addBitmapToMemory(String key,Bitmap bitmap){
        if (getBitmapFromMemCache(key) == null) {
            lruCache.put(key, bitmap);
        }
    }
    // 从缓存中得到Bitmap对象
    public Bitmap getBitmapFromMemCache(String key) {
        Log.i(TAG, "lrucache size: " + lruCache.size());
        return lruCache.get(key);
    }

    // 从缓存中删除指定的Bitmap
    public void removeBitmapFromMemory(String key) {
        lruCache.remove(key);
    }
}
