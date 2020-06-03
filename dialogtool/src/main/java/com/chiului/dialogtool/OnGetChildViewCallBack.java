package com.chiului.dialogtool;

import android.view.View;

/**
 * 获取Dialog布局的控件回调$
 *
 * @author 神经大条蕾弟
 * @date 2020/06/03 17:48
 */
public interface OnGetChildViewCallBack<T> {
    /**
     * View的回调
     * @param view
     */
    void onChildView(T view);
}
