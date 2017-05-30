package com.sunchengjian.myplayer0530.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 0..0 on 2017/5/30.
 */

public abstract class BaseFragment extends Fragment {
    public Context context;

    //创建Fragment时调用
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    //创建视图时调用
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    public abstract View initView();

    //当依附的Activity被创建的时候回调
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();//在得到视图的基础上，设置数据
    }

    //当子类需要绑定数据的时候，重写该方法
    public void initData(){

    }
}
