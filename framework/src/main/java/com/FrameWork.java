package com;

import android.content.Context;

import com.utils.SpUtils;

/**
 * @author：张宝全
 * @date：2020-02-02
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class FrameWork {

    private volatile static FrameWork mFrameWork;

    private FrameWork(){

    }

    public static FrameWork getInstance(){
        if (mFrameWork == null){
            synchronized (FrameWork.class){
                if (mFrameWork == null){
                    mFrameWork = new FrameWork();
                }
            }
        }
        return mFrameWork;
    }

    /**
     * 初始化框架 Model
     *
     * @param mContext
     */
    public void initFramework(Context mContext) {
        SpUtils.getInstance().initSp(mContext);
//        BmobManager.getInstance().initBmob(mContext);
//        CloudManager.getInstance().initCloud(mContext);
//        LitePal.initialize(mContext);
//        MapManager.getInstance().initMap(mContext);
//        WindowHelper.getInstance().initWindow(mContext);
//        CrashReport.initCrashReport(mContext, BUGLY_KEY, BuildConfig.LOG_DEBUG);
//        ZXingLibrary.initDisplayOpinion(mContext);
//        NotificationHelper.getInstance().createChannel(mContext);
//        KeyWordManager.getInstance().initManager(mContext);
//
//        //全局捕获RxJava异常
//        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                LogUtils.e("RxJava：" + throwable.toString());
//            }
//        });
    }
}
