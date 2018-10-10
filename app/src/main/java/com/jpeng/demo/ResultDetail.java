package com.jpeng.demo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.util.Objects;

/**
 * Created by Administrator on 2018/9/21.
 */

public class ResultDetail extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_detail);
        Intent intent = getIntent();
        //是数据库中某一条数据的id
        String data = intent.getStringExtra("itemId");
        //打开数据库得到读写权限
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "afs", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("afs", null, null, null, null, null, null);
        //使用Cursor这个类必须要判断if(cursor.moveToFirst())否则cursor会从-1开始
        if (cursor.moveToFirst()) {
            do {
                if (Objects.equals(cursor.getString(cursor.getColumnIndex("ID")), data)) {
                    //选择ID=data的这一条数据
                    String ID = cursor.getString(cursor.getColumnIndex("ID"));
                    String SampleCode = cursor.getString(cursor.getColumnIndex("SampleCode"));
                    String TestItem = cursor.getString(cursor.getColumnIndex("TestItem"));
                    String TestDateBegin = cursor.getString(cursor.getColumnIndex("TestDateBegin"));
                    String TestDateEnd = cursor.getString(cursor.getColumnIndex("TestDateEnd"));
                    String AuditDoctor = cursor.getString(cursor.getColumnIndex("AuditDoctor"));
                    String AuditTime = cursor.getString(cursor.getColumnIndex("AuditTime"));
                    String ClientID = cursor.getString(cursor.getColumnIndex("ClientID"));
                    String State = cursor.getString(cursor.getColumnIndex("State"));
                    String TestStep = cursor.getString(cursor.getColumnIndex("TestStep"));
                    String FrameNo = cursor.getString(cursor.getColumnIndex("FrameNo"));
                    String TubeNo = cursor.getString(cursor.getColumnIndex("TubeNo"));
                    String ErrorInfo = cursor.getString(cursor.getColumnIndex("ErrorInfo"));
                    String Remark = cursor.getString(cursor.getColumnIndex("Remark"));
                    String H2O2 = cursor.getString(cursor.getColumnIndex("H2O2"));
                    String LE = cursor.getString(cursor.getColumnIndex("LE"));
                    String SNA = cursor.getString(cursor.getColumnIndex("SNA"));
                    String PIP = cursor.getString(cursor.getColumnIndex("PIP"));
                    String NAG = cursor.getString(cursor.getColumnIndex("NAG"));
                    String PH = cursor.getString(cursor.getColumnIndex("PH"));
                    String Name = cursor.getString(cursor.getColumnIndex("Name"));
                    String Age = cursor.getString(cursor.getColumnIndex("Age"));
                    String PatientsType = cursor.getString(cursor.getColumnIndex("PatientsType"));
                    String ApplyDepartment = cursor.getString(cursor.getColumnIndex("ApplyDepartment"));
                    String BedNum = cursor.getString(cursor.getColumnIndex("BedNum"));
                    String AdmissionNum = cursor.getString(cursor.getColumnIndex("AdmissionNum"));
                    String ApplyDoctor = cursor.getString(cursor.getColumnIndex("ApplyDoctor"));
                    String TV = cursor.getString(cursor.getColumnIndex("TV"));
                    String MOLDS = cursor.getString(cursor.getColumnIndex("MOLDS"));
                    String COCCUS = cursor.getString(cursor.getColumnIndex("COCCUS"));
                    String BACILLUS = cursor.getString(cursor.getColumnIndex("BACILLUS"));
                    String WBC = cursor.getString(cursor.getColumnIndex("WBC"));
                    String RBC = cursor.getString(cursor.getColumnIndex("RBC"));
                    String SQEP = cursor.getString(cursor.getColumnIndex("SQEP"));
                    String Cleanliness = cursor.getString(cursor.getColumnIndex("Cleanliness"));
                    String DetectDate = cursor.getString(cursor.getColumnIndex("DetectDate"));
                    String DetectDoctor = cursor.getString(cursor.getColumnIndex("DetectDoctor"));



                    TextView textView1 = (TextView) findViewById(R.id.textView1);
                    textView1.setText(ID);
                    TextView textView2 = (TextView) findViewById(R.id.textView2);
                    textView2.setText(SampleCode);
                    TextView textView3 = (TextView) findViewById(R.id.textView3);
                    textView3.setText(TestItem);
                    TextView textView4 = (TextView) findViewById(R.id.textView4);
                    textView4.setText(TestDateBegin.substring(0, TestDateBegin.length()-2));
                    TextView textView5 = (TextView) findViewById(R.id.textView5);
                    textView5.setText(TestDateEnd.substring(0, TestDateEnd.length()-2));
                    TextView textView6 = (TextView) findViewById(R.id.textView6);
                    textView6.setText(AuditDoctor);
                    TextView textView7 = (TextView) findViewById(R.id.textView7);
                    textView7.setText(AuditTime.substring(0, AuditTime.length()-2));
                    TextView textView8 = (TextView) findViewById(R.id.textView8);
                    textView8.setText(ClientID);
                    TextView textView9 = (TextView) findViewById(R.id.textView9);
                    textView9.setText(State);
                    TextView textView10 = (TextView) findViewById(R.id.textView10);
                    textView10.setText(TestStep);
                    TextView textView11 = (TextView) findViewById(R.id.textView11);
                    textView11.setText(FrameNo);
                    TextView textView12 = (TextView) findViewById(R.id.textView12);
                    textView12.setText(TubeNo);
                    TextView textView13 = (TextView) findViewById(R.id.textView13);
                    textView13.setText(ErrorInfo);
                    TextView textView14 = (TextView) findViewById(R.id.textView14);
                    textView14.setText(Remark);
                    TextView textView15 = (TextView) findViewById(R.id.textView15);
                    textView15.setText(H2O2);
                    TextView textView16 = (TextView) findViewById(R.id.textView16);
                    textView16.setText(LE);
                    TextView textView17 = (TextView) findViewById(R.id.textView17);
                    textView17.setText(SNA);
                    TextView textView18 = (TextView) findViewById(R.id.textView18);
                    textView18.setText(PIP);
                    TextView textView19 = (TextView) findViewById(R.id.textView19);
                    textView19.setText(NAG);
                    TextView textView20 = (TextView) findViewById(R.id.textView20);
                    textView20.setText(PH);
                    TextView textView21 = (TextView) findViewById(R.id.textView21);
                    textView21.setText(Name);
                    TextView textView22 = (TextView) findViewById(R.id.textView22);
                    textView22.setText(Age);
                    TextView textView23 = (TextView) findViewById(R.id.textView23);
                    textView23.setText(PatientsType);
                    TextView textView24 = (TextView) findViewById(R.id.textView24);
                    textView24.setText(ApplyDepartment);
                    TextView textView25 = (TextView) findViewById(R.id.textView25);
                    textView25.setText(BedNum);
                    TextView textView26 = (TextView) findViewById(R.id.textView26);
                    textView26.setText(AdmissionNum);
                    TextView textView27 = (TextView) findViewById(R.id.textView27);
                    textView27.setText(ApplyDoctor);
                    TextView textView28 = (TextView) findViewById(R.id.textView28);
                    textView28.setText(TV);
                    TextView textView29 = (TextView) findViewById(R.id.textView29);
                    textView29.setText(MOLDS);
                    TextView textView30 = (TextView) findViewById(R.id.textView30);
                    textView30.setText(COCCUS);
                    TextView textView31 = (TextView) findViewById(R.id.textView31);
                    textView31.setText(BACILLUS);
                    TextView textView32 = (TextView) findViewById(R.id.textView32);
                    textView32.setText(WBC);
                    TextView textView33 = (TextView) findViewById(R.id.textView33);
                    textView33.setText(RBC);
                    TextView textView34 = (TextView) findViewById(R.id.textView34);
                    textView34.setText(SQEP);
                    TextView textView35 = (TextView) findViewById(R.id.textView35);
                    textView35.setText(Cleanliness);
                    TextView textView36 = (TextView) findViewById(R.id.textView36);
                    textView36.setText(DetectDate);
                    TextView textView37 = (TextView) findViewById(R.id.textView37);
                    textView37.setText(DetectDoctor);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();


    }

    private void displayResultDetail(String dataIndex) {

    }
}
