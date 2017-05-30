package com.sunchengjian.myplayer0530;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.sunchengjian.myplayer0530.fragment.BaseFragment;
import com.sunchengjian.myplayer0530.pager.LocalAudio;
import com.sunchengjian.myplayer0530.pager.LocalVideo;
import com.sunchengjian.myplayer0530.pager.NetAudio;
import com.sunchengjian.myplayer0530.pager.NetVideo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_main;
    private ArrayList<BaseFragment> fragments;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        initFrament();
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_main.check(R.id.rb_local_video);
    }

    private void initFrament() {
        fragments = new ArrayList<>();
        fragments.add(new LocalVideo());
        fragments.add(new LocalAudio());
        fragments.add(new NetAudio());
        fragments.add(new NetVideo());

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.rb_local_video:
                    position = 0;
                    break;
                case R.id.rb_local_audio:
                    position = 1;
                    break;
                case R.id.rb_net_audio:
                    position = 2;
                    break;
                case R.id.rb_net_video:
                    position = 3;
                    break;
            }
            BaseFragment fragment = fragments.get(position);
            addFragment(fragment);
        }
    }

    private Fragment tempFragment;

    private void addFragment(Fragment fragment) {
        if (tempFragment != fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //判断是否添加过-没有添加
            if (!fragment.isAdded()) {
                //把之前的隐藏
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                //添加当前的
                ft.add(R.id.fl_content, fragment);
            } else {
                // 当前Fragment已经添加过
                //把之前的隐藏
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                //显示当前的
                ft.show(fragment);
            }
            ft.commit();//提交事务
            //把当前的缓存起来
            tempFragment = fragment;

        }
    }

    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     *
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }
}
