package com.kuaqu.reader.module_common_ui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context) {
        super(context, "download.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table thread_info(_id integer primary key autoincrement,thread_id integer,url text,thread_start integer,thread_end integer,finished integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //先删除
        db.execSQL("drop table if exists thread_info");
        //再创建
        db.execSQL("create table thread_info(_id integer primary key autoincrement,thread_id integer,url text,thread_start integer,thread_end integer,finished integer)");
    }
}
