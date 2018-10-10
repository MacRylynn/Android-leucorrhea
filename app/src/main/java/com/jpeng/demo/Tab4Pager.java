package com.jpeng.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jpeng.jptabbar.JPTabBar;

/**
 * Created by ${LJ} on 16-11-14.
 */
public class Tab4Pager extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab4,null);
        init(layout);
        return layout;
    }
    /**
     * 初始化
     */
    private void init(View layout) {

    }
    @Override
    public void onClick(View view) {
        JPTabBar tabBar = (JPTabBar) ((Activity)getContext()).findViewById(R.id.tabbar);
        tabBar.setTabTypeFace("fonts/Jaden.ttf");
    }

}
