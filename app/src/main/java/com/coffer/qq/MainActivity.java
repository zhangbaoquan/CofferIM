package com.coffer.qq;


import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.coffer.qq.activity.BaseActivity;
import com.coffer.qq.manager.MediaPlayerManager;

public class MainActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testPlay();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void testPlay(){
        // 测试视频播放
        MediaPlayerManager mediaPlayerManager = new MediaPlayerManager();
        AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.guide);
        mediaPlayerManager.startPlay(assetFileDescriptor);
    }
}
