package com.jpeng.demo;



import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * Created by ${LJ} on 16-11-14.
 */
public class Tab2Pager extends Fragment {
    private MyDatabaseHelper dbHelper;
    private ResultSet rs;
    private EditText editText;
    private String searchString;
    private  ArrayList<String> list=new ArrayList<String>();
    //连接的地址和数据库名称 10.0.2.2:3306代表手机本地地址 afs是数据库名称
    private  String url = "jdbc:mysql://10.0.2.2:3306/afs";
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.tab2, null);
        LinearLayout rootLayout = (LinearLayout) layout.findViewById(R.id.root_layout);
        //刷新手机数据库的数据按钮
        Button button = (Button) layout.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                synchronizeData();
            }
        });
        //显示所有数据
        Button button1 = (Button) layout.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DisplayAll.class);
                startActivity(intent);
            }
        });
        //按照数据搜索想要的数据
        editText = (EditText) layout.findViewById(R.id.editText2);
        Button button2 = (Button) layout.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchString = editText.getText().toString();
                searchData();
                //将满足搜索条件的ArrayList传递到SearchActivity里面去
                if(list.size()>0) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putStringArrayListExtra("list", list);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"没有此类型的数据，请重新输入搜索",Toast.LENGTH_LONG).show();
                }
                    list.clear();
            }
        });
        return layout;
    }


    /**
     * 用子线程访问电脑的数据库
     * 子线程的数据不能直接显示到主线程的界面
     * 所以先在手机建立SQLite数据库把电脑数据库的数据同步到手机的数据库
     * 方便显示在主线程的界面
     */
    private void synchronizeData() {
        dbHelper = new MyDatabaseHelper(getContext(), "afs", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Toast.makeText(getActivity(), "数据正在同步...", Toast.LENGTH_LONG).show();

        final ContentValues values = new ContentValues();
        //在刷新数据库之前先清除数据库里面的所有内容，防止ID重复时报错
        Cursor cursor = db.query("afs", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                db.delete("afs", null, null);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.v("message:", "删除数据成功");

        new Thread() {
            public void run() {
                try {
                    //注册驱动
                    Class.forName("com.mysql.jdbc.Driver");
                   //连接的地址和数据库名称 10.0.2.2:3306代表手机本地地址 afs是数据库名称
                  //  private  String url = "jdbc:mysql://10.0.2.2:3306/afs";
                    Connection conn = DriverManager.getConnection(url, "root", "530454062");
                    Statement stmt = conn.createStatement();
                    String sql = "select * from  sample_2018";//sample_2018是表名
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        values.put("ID", rs.getString(1));
                        values.put("SampleCode", rs.getString(2));
                        values.put("TestItem", rs.getString(3));
                        values.put("TestDateBegin", rs.getString(4));
                        values.put("TestDateEnd", rs.getString(5));
                        values.put("AuditDoctor", rs.getString(6));
                        values.put("AuditTime", rs.getString(7));
                        values.put("ClientID", rs.getString(8));
                        values.put("State", rs.getString(9));
                        values.put("TestStep", rs.getString(10));
                        values.put("FrameNo", rs.getString(11));
                        values.put("TubeNo", rs.getString(12));
                        values.put("ErrorInfo", rs.getString(13));
                        values.put("Remark", rs.getString(14));
                        values.put("H2O2", rs.getString(15));
                        values.put("LE", rs.getString(16));
                        values.put("SNA", rs.getString(17));
                        values.put("PIP", rs.getString(18));
                        values.put("NAG", rs.getString(19));
                        values.put("PH", rs.getString(20));
                        values.put("Name", rs.getString(21));
                        values.put("Age", rs.getString(22));
                        values.put("PatientsType", rs.getString(23));
                        values.put("ApplyDepartment", rs.getString(24));
                        values.put("BedNum", rs.getString(25));
                        values.put("AdmissionNum", rs.getString(26));
                        values.put("ApplyDoctor", rs.getString(27));
                        values.put("TV", rs.getString(28));
                        values.put("MOLDS", rs.getString(29));
                        values.put("COCCUS", rs.getString(30));
                        values.put("BACILLUS", rs.getString(31));
                        values.put("WBC", rs.getString(32));
                        values.put("RBC", rs.getString(33));
                        values.put("SQEP", rs.getString(34));
                        values.put("Cleanliness", rs.getString(35));
                        values.put("DetectDate", rs.getString(36));
                        values.put("DetectDoctor", rs.getString(37));
                        db.insert("afs", null, values);
                    }
                    //添加数据成功后作出的回应
                    Log.v("message:", "添加数据成功");
                    Log.v("message:", "数据库连接成功");
                } catch (ClassNotFoundException e) {
                    Log.v("message:", "数据库连接失败");
                } catch (SQLException e) {
                    Log.v("message:", "数据库连接失败");
                }
            }
        }.start();
        Toast.makeText(getContext(), "同步数据数据成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 搜索函数，根据传递进来的患者姓名或者ID
     */
    private void searchData() {
        dbHelper = new MyDatabaseHelper(getContext(), "afs", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.query("afs", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (searchString.
                        equals(cursor.getString(cursor.getColumnIndex("Name")))||searchString.
                        equals(cursor.getString(cursor.getColumnIndex("ID")))) {
                    list.add(cursor.getString(cursor.getColumnIndex("ID")));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
