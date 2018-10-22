package com.jpeng.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jpeng.jptabbar.JPTabBar;

import java.util.List;

/**
 * Created by ${LJ} on 16-11-14.
 */
public class Tab4Pager extends Fragment implements View.OnClickListener {
    private String phone = "18782930471";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab4, null);
        Button clearBtn = (Button) layout.findViewById(R.id.button29);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"清理成功",Toast.LENGTH_SHORT).show();
            }
        });
        Button onlineBtn = (Button) layout.findViewById(R.id.button30);
        onlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isQQClientAvailable(getActivity())){
                    //此处传入的QQ号,需开通QQ推广功能,不然向此QQ号发送临时消息,会不成功
                    final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=530454062&version=1";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                }else{
                    Toast.makeText(getActivity(),"请安装QQ客户端",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button phoneBtn = (Button) layout.findViewById(R.id.button31);
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);//设置活动类型
                intent.setData(Uri.parse("tel:"+phone));//设置数据
                startActivity(intent);//开启意图
            }
        });
        return layout;
    }

            @Override
    public void onClick(View view) {
        JPTabBar tabBar = (JPTabBar) ((Activity) getContext()).findViewById(R.id.tabbar);
        tabBar.setTabTypeFace("fonts/Jaden.ttf");
    }
    //判断QQ客户端是否安装
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
}
