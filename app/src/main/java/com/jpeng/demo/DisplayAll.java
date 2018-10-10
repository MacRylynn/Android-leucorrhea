package com.jpeng.demo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/9/25.
 */

public class DisplayAll extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private LinearLayout rootLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_all);
        rootLayout = (LinearLayout)findViewById(R.id.root_layout);
        displayData();
    }


    /**从本地数据库读取所有的数据
     * 逐条显示在界面上*/
    private void displayData() {
        dbHelper = new MyDatabaseHelper(DisplayAll.this, "afs", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.query("afs", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                RelativeLayout relativerLayout = new RelativeLayout(DisplayAll.this);
                TextView textView = new TextView(DisplayAll.this);
                String displayText = "样品ID：" + cursor.getString(cursor.getColumnIndex("ID")) + "    " + "客户编号：" + cursor.getString(cursor.getColumnIndex("ClientID"));
                textView.setText(displayText);
                textView.setTextSize(30);
                textView.setBackgroundColor(Color.rgb(255, 255, 0));
                LinearLayout.LayoutParams relativeLayout_parent_params
                        = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                RelativeLayout.LayoutParams text_parent_params
                        = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                text_parent_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                WindowManager screenWindow = this.getWindowManager();//获得手机屏幕的对象
                text_parent_params.height = 120;
                text_parent_params.width = screenWindow.getDefaultDisplay().getWidth();
                text_parent_params.setMargins(10, 20, 10, 10);
                relativerLayout.addView(textView, text_parent_params);
                rootLayout.addView(relativerLayout, relativeLayout_parent_params);
                final String itemId = cursor.getString(cursor.getColumnIndex("ID"));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //把点击这条数据的ID传到详情显示界面
                        Intent skipIntent = new Intent(DisplayAll.this, ResultDetail.class);
                        skipIntent.putExtra("itemId", itemId);
                        startActivity(skipIntent);
                    }
                });
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
