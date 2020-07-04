package com.chiului.dialogtool;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 万能自定义Dialog
 * 采用Builder链式调用, 传入layoutID与位置即可实现自定义Dialog
 * @author 神经大条蕾弟
 * @date   2020/06/03 11:22
 */
public class DialogTool extends Dialog {

    private static LinearLayout mInflateLayout;
    private Context mContext;
    private View mView;
    private boolean mIsClickOutSide;
    private int mWidth;
    private int mHeight;
    private int mLayoutX;
    private int mLayoutY;
    private DialogGravity mGravity;
    private float mAlpha;

    /**
     * 设置Dialog布局排版
     *
     * @author xiejinxiong
     */
    public enum DialogGravity {
        LEFT_TOP, RIGHT_TOP, CENTER_TOP, CENTER, LEFT_BOTTOM, RIGHT_BOTTOM, CENTER_BOTTOM
    }

    private Display mDisplay;

    /**
     * Dialog的Window
     */
    private Window mDialogWindow;

    /**
     * Dialog的布局数据
     */
    private WindowManager.LayoutParams mDialogLayoutParams;

    /**
     * 获取 Dialog 根布局
     * @return
     */
    public View getRootView() {
        return mView;
    }

    /**
     * 设置视图宽高
     */
    private void setLayoutWidthHeight() {
        if (mHeight != -1) {
            mDialogLayoutParams.height = mHeight;
        } else {
            mDialogLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }

        if (mWidth != -1) {
            mDialogLayoutParams.width = mWidth;
        } else {
            mDialogLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        }
    }

    /**
     * 设置dialog的绝对定位
     */
    private void setLayoutXY() {
        if (mLayoutX != -1) {
            mDialogLayoutParams.x = mLayoutX;
        }
        if (mLayoutY != -1) {
            mDialogLayoutParams.y = mLayoutY;
        }
    }

    /**
     * 设置dialog的相对定位
     */
    private void setLayoutGravity() {
        switch (mGravity) {
            case LEFT_TOP:
                mDialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                break;
            case RIGHT_TOP:
                mDialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
                break;
            case CENTER_TOP:
                mDialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                break;
            case CENTER:
                mDialogWindow.setGravity(Gravity.LEFT | Gravity.CENTER);
                break;
            case LEFT_BOTTOM:
                mDialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                break;
            case RIGHT_BOTTOM:
                mDialogWindow.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                break;
            case CENTER_BOTTOM:
                mDialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化dialog
     */
    private void initDialog() {
        mDialogWindow = getWindow();
        mDialogLayoutParams = mDialogWindow.getAttributes();
        // 设置背景透明度
        if (mAlpha != -1) {
            mDialogWindow.setDimAmount(mAlpha);
        }
        // 设置点击透明背景是否退出
        setCanceledOnTouchOutside(mIsClickOutSide);

        // 如果没有设置偏移量，就要给View设置一个最小宽度。否则布局会变形。
        if (mLayoutX == -1) {
            // 设置视图的最小显示宽度
            WindowManager windowManager = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            mDisplay = windowManager.getDefaultDisplay();
            Point size = new Point();
            mDisplay.getSize(size);
            int width = size.x;
            mView.setMinimumWidth(width);
        }
    }

    private DialogTool(Builder builder) {
        super(builder.mBuilderContext, builder.mBuilderThemeResId);
        mContext = builder.mBuilderContext;
        mView = builder.mBuilderView;
        mIsClickOutSide = builder.mIsClickOutSide;
        mWidth = builder.mBuilderWidth;
        mHeight = builder.mBuilderHeight;
        mLayoutX = builder.mBuilderLayoutX;
        mLayoutY = builder.mBuilderLayoutY;
        mGravity = builder.mBuilderGravity;
        mAlpha = builder.mBuilderAlpha;

        createDialog();
    }

    private void createDialog() {

        initDialog();
        // 设置Window相对定位
        setLayoutGravity();
        // 设置Window偏移量（相对于相对定位的偏移量）
        setLayoutXY();
        // 设置Window视图宽高
        setLayoutWidthHeight();
        // 设置Window属性
        mDialogWindow.setAttributes(mDialogLayoutParams);
        // 设置Dialog视图
        setContentView(mView);
    }

    public static final class Builder {
        private Context mBuilderContext;
        private int mBuilderThemeResId;
        private View mBuilderView;
        private boolean mIsClickOutSide;
        private int mBuilderWidth;
        private int mBuilderHeight;
        private int mBuilderLayoutX;
        private int mBuilderLayoutY;
        private DialogGravity mBuilderGravity;
        private float mBuilderAlpha;

        /**
         * 创建 Builder（不带 View）
         * @param context 上下文
         */
        public Builder(Context context) {
            this.mBuilderContext = context;

            getDefault();
        }

        /**
         * 创建 Builder
         * @param context 上下文
         * @param resView Dialog 的布局 id
         */
        public Builder(Context context, int resView) {
            this(context);
            this.setView(resView);
        }

        /**
         * 创建 Builder
         * @param context 上下文
         * @param resView Dialog 的布局 View
         */
        public Builder(Context context, View resView) {
            this(context);
            this.setView(resView);
        }

        /**
         * 获取默认值
         */
        private void getDefault() {
            mBuilderThemeResId = R.style.DialogToolStyle_Default;
            mBuilderView = null;
            mIsClickOutSide = true;
            mBuilderWidth = -1;
            mBuilderHeight = -1;
            mBuilderLayoutX = -1;
            mBuilderLayoutY = -1;
            mBuilderGravity = DialogGravity.CENTER;
            mBuilderAlpha = -1;
        }

        /**
         * 设置自定义主题style
         *
         * @param themeResId styleID
         * @return
         */
        public Builder setStyle(int themeResId) {
            this.mBuilderThemeResId = themeResId;
            return this;
        }

        /**
         * 设置Layout文件ID
         *
         * @param resView
         * @return
         */
        public Builder setView(int resView) {
            mInflateLayout = (LinearLayout) LayoutInflater.from(mBuilderContext).inflate(R.layout.dialog_tool_parent, null);
            this.mBuilderView = LayoutInflater.from(mBuilderContext).inflate(resView, mInflateLayout, true);
            return this;
        }

        /**
         * 设置Layout
         *
         * @return
         */
        public Builder setView(View resView) {
            this.mBuilderView = resView;
            return this;
        }

        /**
         * 设置是否允许点击dialog外部关闭Dialog
         *
         * @param isClickOutSide
         * @return
         */
        public Builder isClickOutSide(boolean isClickOutSide) {
            this.mIsClickOutSide = isClickOutSide;
            return this;
        }

        /**
         * 设置 dialog 的相对于 setGravity() 定位的偏移
         *
         * @param layoutX X坐标 不设置传-1
         * @param layoutY Y坐标 不设置传-1
         * @return
         */
        public Builder setLayoutXY(int layoutX, int layoutY) {
            this.mBuilderLayoutX = layoutX;
            this.mBuilderLayoutY = layoutY;
            return this;
        }

        /**
         * 设置dialog 视图宽度和高度
         *
         * @param width
         * @return
         */
        public Builder setWidthAndHeight(int width, int height) {
            this.mBuilderWidth = width;
            this.mBuilderHeight = height;
            return this;
        }

        /**
         * 设置dialog 视图高度
         *
         * @param height
         * @return
         */
        public Builder setHeight(int height) {
            this.mBuilderHeight = height;
            return this;
        }

        /**
         * 设置dialog 视图宽度
         *
         * @param width
         * @return
         */
        public Builder setWidth(int width) {
            this.mBuilderWidth = width;
            return this;
        }

        /**
         * 设置dialog视图外的背景透明度
         *
         * @param alpha 透明 0f ~ 1.0f 不透明
         * @return
         */
        public Builder setAlpha(float alpha) {
            this.mBuilderAlpha = alpha;
            return this;
        }

        /**
         * 设置 dialog 的相对定位如位于 Window 的上中下左右
         *
         * @param gravity
         * @return
         */
        public Builder setGravity(DialogGravity gravity) {
            this.mBuilderGravity = gravity;
            return this;
        }

        /**
         * 设置Dialog布局里控件view的监听
         *
         * @param viewRes  控件ID
         * @param listener 监听事件
         * @return
         */
        public Builder setOnClick(int viewRes, View.OnClickListener listener) {
            this.mBuilderView.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        /**
         * 设置Dialog根布局的点击事件
         *
         * @param listener 监听事件
         * @return
         */
        public Builder setOnClickRoot(View.OnClickListener listener) {
            this.mBuilderView.setOnClickListener(listener);
            return this;
        }

        /**
         * 设置Dialog子布局TextView的文字
         *
         * @param viewRes 控件ID
         * @param content 文字内容
         * @return
         */
        public Builder setText(int viewRes, String content) {
            TextView tv = this.mBuilderView.findViewById(viewRes);
            if (!TextUtils.isEmpty(content)) {
                tv.setText(content);
            }
            return this;
        }

        /**
         * 获取Dialog布局里的View
         *
         * @param viewRes 控件ID
         * @return
         */
        public Builder getChildView(int viewRes, OnGetChildViewCallBack<View> callBack) {
            View childView = this.mBuilderView.findViewById(viewRes);
            callBack.onChildView(childView);
            return this;
        }

        /**
         * 获取Dialog布局里的TextView
         *
         * @param viewRes 控件ID
         * @return
         */
        public Builder getChildTextView(int viewRes, OnGetChildViewCallBack<TextView> callBack) {
            TextView tv = this.mBuilderView.findViewById(viewRes);
            callBack.onChildView(tv);
            return this;
        }

        /**
         * 获取Dialog布局里的ImageView
         *
         * @param viewRes 控件ID
         * @return
         */
        public Builder getChildImageView(int viewRes, OnGetChildViewCallBack<ImageView> callBack) {
            ImageView iv = this.mBuilderView.findViewById(viewRes);
            callBack.onChildView(iv);
            return this;
        }


        public DialogTool build() {
            return new DialogTool(this);
        }
    }

}
