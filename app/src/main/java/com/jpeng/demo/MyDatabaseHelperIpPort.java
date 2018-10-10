package com.jpeng.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by ${LJ} on 2018/9/27.
 */

public class MyDatabaseHelperIpPort extends SQLiteOpenHelper {
    private Context mContext;
    private static final  String CREATE_IPPORT = "create table ip_port("+
            "IP text,"+
            "PORT text)";
    public MyDatabaseHelperIpPort(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //调用SQLiteDatabase语句来建立数据库
        db.execSQL(CREATE_IPPORT);
        //创建成功之后的回应
        Toast.makeText(mContext,"成功创建ip数据库",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
