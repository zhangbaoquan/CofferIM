package com.coffer.qq.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.coffer.qq.R;

/**
 * @author：张宝全
 * @date：2020/6/19
 * @Description： 启动页
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class IndexActivity extends AppCompatActivity {

    /**
     * 1.把启动页全屏
     * 2.延迟进入主页
     * 3.根据具体逻辑是进入主页还是引导页还是登录页
     * 4.适配刘海屏
     */

    private static final int SKIP_MAIN = 1000;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initHandler();


    }

    private void initHandler(){
        mHandler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == SKIP_MAIN){

                }
            }
        };
        mHandler.sendEmptyMessageDelayed(SKIP_MAIN, 2 * 1000);
    }
}
