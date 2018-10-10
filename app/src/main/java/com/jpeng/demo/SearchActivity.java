package com.jpeng.demo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by ${LJ} on 2018/9/25.
 */

public class SearchActivity extends AppCompatActivity {
    private LinearLayout rootLayout;
    private MyDatabaseHelper dbHelper;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        Intent intent = getIntent();
        ArrayList<String> list = intent.getStringArrayListExtra("list");
        displaySearchData(list);
        System.out.println(list);
        list.clear();
    }

    /**
     * 根据Tab2Pager搜索得到的所有感兴趣的项的ID本身是一个list
     * 取得list中的每一项作为ID号
     * 遍历数据库
     * 得到感兴趣ID号的数据详细情况
     * 点击每一项会跳转到这一项的详情页
     */
    private void displaySearchData(ArrayList<String> list) {

            dbHelper = new MyDatabaseHelper(SearchActivity.this, "afs", null, 1);
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            final Cursor cursor = db.query("afs", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).equals(cursor.getString(cursor.getColumnIndex("ID")))) {
                            RelativeLayout relativerLayout = new RelativeLayout(SearchActivity.this);
                            TextView textView = new TextView(SearchActivity.this);
                            String displayText = "样品ID：" + cursor.getString(cursor.getColumnIndex("ID")) + "    " + "客户编号：" + cursor.getString(cursor.getColumnIndex("ClientID"));
                            textView.setText(displayText);
                            textView.setTextSize(25);
                            textView.setBackgroundColor(Color.rgb(255, 255, 0));
                            LinearLayout.LayoutParams relativeLayout_parent_params
                                    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            RelativeLayout.LayoutParams text_parent_params
                                    = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            text_parent_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                            text_parent_params.height = 100;
                            text_parent_params.width = 800;
                            text_parent_params.setMargins(150, 10, 10, 10);
                            relativerLayout.addView(textView, text_parent_params);
                            rootLayout.addView(relativerLayout, relativeLayout_parent_params);
                            //将点击项的ID传到详情展示页
                            final String itemId = cursor.getString(cursor.getColumnIndex("ID"));
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //把点击这条数据的ID传到详情显示界面
                                    Intent skipIntent = new Intent(SearchActivity.this, ResultDetail.class);
                                    skipIntent.putExtra("itemId", itemId);
                                    startActivity(skipIntent);
                                }
                            });
                        }
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();

    }


}
