package com.sunchengjian.myplayer0530.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by 0..0 on 2017/5/30.
 */

public class Title extends LinearLayout implements View.OnClickListener {
    private Context context;

    public Title(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public void onClick(View v) {

    }
}