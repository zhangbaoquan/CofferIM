package com.coffer.qq.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Constants;
import com.coffer.qq.MainActivity;
import com.coffer.qq.R;
import com.utils.SpUtils;

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
                    startMain();
                }
            }
        };
        mHandler.sendEmptyMessageDelayed(SKIP_MAIN, 2 * 1000);
    }

    /**
     * 进入主页
     */
    private void startMain() {
        //1.判断App是否第一次启动 install - first run
        boolean isFirstApp = SpUtils.getInstance().getBoolean(Constants.SP_IS_FIRST_APP, true);
        Intent intent = new Intent();
        if (isFirstApp) {
            //跳转到引导页
            intent.setClass(this, GuideActivity.class);
            //非第一次启动
            SpUtils.getInstance().putBoolean(Constants.SP_IS_FIRST_APP, false);
        } else {
            //2.如果非第一次启动，判断是否曾经登录过
            String token = SpUtils.getInstance().getString(Constants.SP_TOKEN, "");
            if (TextUtils.isEmpty(token)) {
                //3.判断Bmob是否登录
//                if (BmobManager.getInstance().isLogin()) {
//                    //跳转到主页
//                    intent.setClass(this, MainActivity.class);
//                } else {
//                    //跳转到登录页
//                    intent.setClass(this, LoginActivity.class);
//                }
            } else {
                //跳转到主页
                intent.setClass(this, MainActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }
}
