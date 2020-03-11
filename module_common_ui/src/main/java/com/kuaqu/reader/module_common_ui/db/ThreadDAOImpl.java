package com.kuaqu.reader.module_common_ui.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kuaqu.reader.module_common_ui.response.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

public class ThreadDAOImpl {
    private DBHelper dbHelper;

    public ThreadDAOImpl(Context context) {
      dbHelper=new DBHelper(context);
    }
    public void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.execSQL("insert into thread_info (thread_id,url,thread_start,thread_end,finished) values(?,?,?,?,?)", new Object[]{threadInfo.id, threadInfo.url, threadInfo.thread_start, threadInfo.thread_end, threadInfo.finished});
        db.close();
    }
    public void deleteThread(String url, int threadId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ? and thread_id = ?",
                new Object[]{url, threadId});
        db.close();
    }

    public void updateThread(String url, int threadId, int finished) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished, url, threadId});
        db.close();
    }

    public List<ThreadInfo> queryThread(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
        List<ThreadInfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.id = cursor.getInt(cursor.getColumnIndex("thread_id"));
            threadInfo.url = cursor.getString(cursor.getColumnIndex("url"));
            threadInfo.thread_start = cursor.getInt(cursor.getColumnIndex("thread_start"));
            threadInfo.thread_end = cursor.getInt(cursor.getColumnIndex("thread_end"));
            threadInfo.finished = cursor.getInt(cursor.getColumnIndex("finished"));
            list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean isExists(String url, int threadId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, threadId + ""});
        boolean isExits = cursor.moveToNext();
        cursor.close();
        db.close();
        return isExits;
    }
}
