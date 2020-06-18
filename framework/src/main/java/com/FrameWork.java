package com;

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
}
