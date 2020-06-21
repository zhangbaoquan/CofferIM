package com.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * @author：张宝全
 * @date：2020/6/21
 * @Description： 自定义提示框
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class DialogView extends Dialog {

    public DialogView(@NonNull Context context, int layout,int themeResId,int gravity) {
        super(context, themeResId);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
    }
}
