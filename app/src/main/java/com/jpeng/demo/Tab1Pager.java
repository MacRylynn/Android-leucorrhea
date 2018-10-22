package com.jpeng.demo;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ${LJ} on 18-9-14.
 */
public class Tab1Pager extends Fragment {
    boolean isConnect = true;//连接还是断开
    Button ConnectButton;//定义连接按钮
    Button SendButton1;//定义开始按钮
    Button SendButton2;//定义复位按钮
    Button SendButton3;//定义停止按钮
    Button SendButton4;//定义继续按钮
    EditText IPEditText;//定义ip输入框
    EditText PortText;//定义端口输入框
    EditText RrceiveEditText;//定义信息输入框
    Socket socket = null;//定义socket
    private OutputStream outputStream = null;//定义输出流
    private InputStream inputStream = null;//定义输入流
    private MyDatabaseHelperIpPort dbHelper;//存输入的ip和port


    private String StartMachine    = "0xF628F6280x010x010xA5A5A5A5";
    private String ResetMachine    = "0xF628F6280x010x020xA5A5A5A5";
    private String StopMachine     = "0xF628F6280x010x030xA5A5A5A5";
    private String ContinueMachine = "0xF628F6280x010x040xA5A5A5A5";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab1, null);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ConnectButton = (Button) layout.findViewById(R.id.Connect_Bt);//获得连接按钮对象
        SendButton1 = (Button) layout.findViewById(R.id.Send_Bt1);//获得发送按钮对象
        SendButton2 = (Button) layout.findViewById(R.id.Send_Bt2);//获得发送按钮对象
        SendButton3 = (Button) layout.findViewById(R.id.Send_Bt3);//获得发送按钮对象
        SendButton4 = (Button) layout.findViewById(R.id.Send_Bt4);//获得发送按钮对象
        IPEditText = (EditText) layout.findViewById(R.id.ip_ET);//获得ip文本框对象
        PortText = (EditText) layout.findViewById(R.id.Port_ET);//获得端口文本框按钮对象
        RrceiveEditText = (EditText) layout.findViewById(R.id.Receive_ET);//获得接收消息文本框对象

        //连接
        ConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isConnect == true) //标志位 = true表示连接
                    {
                        //将获取的IP和PORT存入数据库
                        saveIpPort();
                        isConnect = false;//置为false
                        ConnectButton.setText("断开");//按钮上显示--断开
                        //启动连接线程
                        Connect_Thread connect_Thread = new Connect_Thread();
                        connect_Thread.start();

                    } else //标志位 = false表示退出连接
                    {
                        isConnect = true;//置为true
                        ConnectButton.setText("连接");//按钮上显示连接
                        try {
                            socket.close();//关闭连接
                            socket = null;
                        } catch (IOException e) {
// TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        //发送指令的四个按钮
        SendButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnect) {
                    try {
                        //获取输出流
                        outputStream = socket.getOutputStream();
                        //发送数据(指令的值)
                        // private String StartMachine = "0xF628F628 0 0x20 0xA5A5A5A5";
                        outputStream.write(StartMachine.getBytes());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "请先连接地址和端口号", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SendButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnect) {
                    try {
                        //获取输出流
                        outputStream = socket.getOutputStream();
                        //发送数据
                        outputStream.write(ResetMachine.getBytes());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "请先连接地址和端口号", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SendButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnect) {
                    try {
                        //获取输出流
                        outputStream = socket.getOutputStream();
                        //发送数据
                        outputStream.write(StopMachine.getBytes());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "请先连接地址和端口号", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SendButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnect) {
                    try {
                        //获取输出流
                        outputStream = socket.getOutputStream();
                        //发送数据
                        outputStream.write(ContinueMachine.getBytes());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "请先连接地址和端口号", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return layout;
    }

    //连接线程
    class Connect_Thread extends Thread//继承Thread
    {
        public void run()//重写run方法
        {
            try {
                if (socket == null) {
                    //用InetAddress方法获取ip地址
                    InetAddress ipAddress = InetAddress.getByName(IPEditText.getText().toString());
                    int port = Integer.valueOf(PortText.getText().toString());//获取端口号
                    //创建连接地址和端口-------------------这样就好多了
                    socket = new Socket(ipAddress, port);
                    //在创建完连接后启动接收线程
                    Receive_Thread receive_Thread = new Receive_Thread();
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
                    inputStream = socket.getInputStream();
                    final int len = inputStream.read(buffer);//数据读出来，并且返回数据的长度
                    if (getActivity() == null)
                        return;
                    getActivity().runOnUiThread(new Runnable()//不允许其他线程直接操作组件，用提供的此方法可以
                    {
                        public void run() {
// TODO Auto-generated method stub
                            if (len > 0) {
                                RrceiveEditText.setText(new String(buffer, 0, len));
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

    //保存ip和端口号
    private void saveIpPort() {
        dbHelper = new MyDatabaseHelperIpPort(getContext(), "ip_port", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //先清空表
        db.execSQL("delete from ip_port");
        ContentValues values = new ContentValues();
        values.put("IP", IPEditText.getText().toString());
        values.put("PORT", PortText.getText().toString());
        db.insert("ip_port", null, values);
        Cursor cursor = db.query("ip_port", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String IP = cursor.getString(cursor.getColumnIndex("IP"));
                String PORT = cursor.getString(cursor.getColumnIndex("PORT"));
                Toast.makeText(getContext(), "IP：" + IP + "\n" + "PORT：" + PORT, Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }
        cursor.close();

    }
}





