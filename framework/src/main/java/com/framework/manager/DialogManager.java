package com.framework.manager;

import android.content.Context;
import android.view.Gravity;

import com.liuguilin.framework.R;
import com.view.DialogView;

/**
 * @author：张宝全
 * @date：2020/6/21
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class DialogManager {

    private static volatile DialogManager mInstance;

    private DialogManager(){

    }

    public static DialogManager getInstance(){
        if (mInstance == null){
            synchronized (DialogManager.class){
                if (mInstance == null){
                    mInstance = new DialogManager();
                }
            }
        }
        return mInstance;
    }

    public DialogView initView(Context mContext, int layout) {
        return new DialogView(mContext, layout, R.style.Theme_Dialog, Gravity.CENTER);
    }

    public DialogView initView(Context mContext, int layout, int gravity) {
        return new DialogView(mContext, layout, R.style.Theme_Dialog, gravity);
    }

    public void show(DialogView view) {
        if (view != null) {
            if (!view.isShowing()) {
                view.show();
            }
        }
    }

    public void hide(DialogView view) {
        if (view != null) {
            if (view.isShowing()) {
                view.dismiss();
            }

        }
    }
}
