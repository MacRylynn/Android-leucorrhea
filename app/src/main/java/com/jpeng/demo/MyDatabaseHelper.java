package com.jpeng.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/9/20.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final  String CREATE_AFS = "create table afs("+
            //primary key 将id设为主键 autoincrement表示id列是自增长的
            "ID integer primary key autoincrement,"+
            "SampleCode text,"+
            "TestItem text,"+
            "TestDateBegin text,"+
            "TestDateEnd text,"+
            "AuditDoctor text,"+
            "AuditTime text,"+
            "ClientID text,"+
            "State text,"+
            "TestStep text,"+
            "FrameNo text,"+
            "TubeNo text,"+
            "ErrorInfo text,"+
            "Remark text,"+
            "H2O2 text,"+
            "LE text,"+
            "SNA text,"+
            "PIP text,"+
            "NAG text,"+
            "PH text,"+
            "Name text,"+
            "Age text,"+
            "PatientsType text,"+
            "ApplyDepartment text,"+
            "BedNum text,"+
            "AdmissionNum text,"+
            "ApplyDoctor text,"+
            "TV text,"+
            "MOLDS text,"+
            "COCCUS text,"+
            "BACILLUS text,"+
            "WBC text,"+
            "RBC text,"+
            "SQEP text,"+
            "Cleanliness text,"+
            "DetectDate text,"+
            "DetectDoctor text)";
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //调用SQLiteDatabase语句来建立数据库
        db.execSQL(CREATE_AFS);
        //创建成功之后的回应
        Toast.makeText(mContext,"成功创建数据库",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

