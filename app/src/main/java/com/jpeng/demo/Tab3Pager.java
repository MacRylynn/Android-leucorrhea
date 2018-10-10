package com.jpeng.demo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ${LJ} on 16-11-14.
 */
public class Tab3Pager extends Fragment {
    Socket socket = null;//定义socket

    private String orderString;//接收到的命令字符串
    private final String opennOrClose = "0xF628F628 0 0x20 0xA5A5A5A5";
    private final String countingCellsLoding = "0xF628F628 0 0x21 0xA5A5A5A5";
    private final String centrifugal = "0xF628F628 0 0x22 0xA5A5A5A5";

    private Button button;
    private Button button1;
    private Button button2;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab3, null);
        button = (Button) layout.findViewById(R.id.button4);
        button1 = (Button) layout.findViewById(R.id.button5);
        button2 = (Button) layout.findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectSocket();
            }
        });
        return layout;
    }

    /**
     * 初始化
     */
    private void connectSocket() {
        try {
            //启动连接线程
            Connect_Thread connect_Thread = new Connect_Thread();
            connect_Thread.start();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "请检查ip和端口号是否已开启连接", Toast.LENGTH_SHORT).show();
        }
    }
    //连接线程
    class Connect_Thread extends Thread//继承Thread
    {
        public void run()//重写run方法
        {
            try {
                if (socket == null) {
                    //用InetAddress方法获取ip地址
                    MyDatabaseHelperIpPort dbHelper = new MyDatabaseHelperIpPort(getContext(), "ip_port", null, 1);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.query("ip_port", null, null, null, null, null, null);
                    String IP = null;
                    String PORT = null;
                    if (cursor.moveToLast()) {
                        IP = cursor.getString(cursor.getColumnIndex("IP"));
                        PORT = cursor.getString(cursor.getColumnIndex("PORT"));
                    }
                    cursor.close();
                    if (IP != null && PORT != null) {
                        InetAddress ipAddress = InetAddress.getByName(IP);
                        int port = Integer.valueOf(PORT);//获取端口号
                        socket = new Socket(ipAddress, port);//创建连接地址和端口-------------------这样就好多了
                    }
                    //在创建完连接后启动接收线程
                    Tab3Pager.Receive_Thread receive_Thread = new Tab3Pager.Receive_Thread();
                    receive_Thread.start();
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //接收线程
    class Receive_Thread extends Thread {
        public void run()//重写run方法
        {
            try {
                while (true) {
                    final byte[] buffer = new byte[61440];//创建接收缓冲区
                    InputStream inputStream = socket.getInputStream();
                    final int len = inputStream.read(buffer);//数据读出来，并且返回数据的长度
                    if (getActivity() == null)
                        return;
                    getActivity().runOnUiThread(new Runnable()//不允许其他线程直接操作组件，用提供的此方法可以
                    {
                        public void run() {
                            // TODO Auto-generated method stub
                            if (len > 0) {
                                //  RrceiveEditText.setText(new String(buffer, 0, len));

                                orderString = new String(buffer, 0, len);
                                contrastOrderString(orderString);

                            }
                        }
                    });
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void contrastOrderString(String string) {
        switch (string) {
            case opennOrClose:
                button.setText("已连接");
                button.setTextColor(0xFF00FF00);
                break;
            case countingCellsLoding:
                button1.setTextColor(0xFF00FF00);
                break;
            case centrifugal:
                button2.setTextColor(0xFF00FF00);
                break;
            default:
                break;
        }
    }
}




