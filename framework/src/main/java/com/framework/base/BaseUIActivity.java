package com.framework.base;

import android.os.Bundle;
import com.utils.SystemUI;


/**
 * FileName: BaseUIActivity
 * Founder: LiuGuiLin
 * Profile: UI 基类
 */
public abstract class BaseUIActivity extends BaseActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemUI.fixSystemUI(this);
    }
}
