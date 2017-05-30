package com.sunchengjian.myplayer0530.pager;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunchengjian.myplayer0530.R;
import com.sunchengjian.myplayer0530.adapter.LocalVideoAdapter;
import com.sunchengjian.myplayer0530.domain.MediaItem;
import com.sunchengjian.myplayer0530.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by 0..0 on 2017/5/30.
 */

public class LocalVideo extends BaseFragment {
    private ListView lv;
    private TextView tv_nodata;
    private ArrayList<MediaItem> mediaItems;
    private LocalVideoAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_local_video, null);
        lv = (ListView) view.findViewById(R.id.lv);
        tv_nodata = (TextView) view.findViewById(R.id.tv_nodata);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  MediaItem mediaItem = mediaItems.get(position);
                MediaItem item = (MediaItem) adapter.getItem(position);

                //吊起系统自带的播放器
                Intent intent = new Intent();
                intent.setDataAndType(Uri.parse(item.getData()), "video/*");
                startActivity(intent);

            }
        });



        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //加载本地所有视频
        getData();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mediaItems != null && mediaItems.size() > 0) {
                tv_nodata.setVisibility(View.GONE);
                adapter = new LocalVideoAdapter(context, mediaItems);
                lv.setAdapter(adapter);

            } else {
                tv_nodata.setVisibility(View.VISIBLE);
            }
        }
    };

    public void getData() {
        new Thread() {
            public void run() {
                mediaItems = new ArrayList<>();
                ContentResolver resolver = context.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.DURATION,
                        MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DATA,
                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                        Log.e("TAG", "name==" + name + ",duration==" + duration + ",data===" + data);

                        mediaItems.add(new MediaItem(name, duration, size, data));

                        handler.sendEmptyMessage(0);

                    }
                    cursor.close();
                }
            }
        }.start();
    }
}

