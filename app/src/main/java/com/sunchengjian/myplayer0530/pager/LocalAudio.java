package com.sunchengjian.myplayer0530.pager;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.sunchengjian.myplayer0530.fragment.BaseFragment;

/**
 * Created by 0..0 on 2017/5/30.
 */

public class LocalAudio extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("222");
    }
}

