DialogTool
-------------

# 简介:
> 🌟适用于 Android 的快速生成 Dialog。🔥采用 Builder 设计模式链式调用。🔥传入需要显示的布局即可快速实现自定义 Dialog。

# 目录:
[1.使用方式](#1)

[2.使用示例](#2)

[3.API](#3)


# <span id = "1">**1.使用方式**</span>


### 使用方式一：Maven方式：

##### Step 1. 在 project 层级的 build.gradle 中，添加仓库地址:

```
allprojects {
    repositories {
        ...
        jcenter()
    }
}
```

##### Step 2. 在主 module 的 build.gradle 中添加依赖：

- 最新版本 

[ ![Download](https://api.bintray.com/packages/chiului/Library/dialogtool/images/download.svg?version=1.1.0) ](https://bintray.com/chiului/Library/dialogtool/1.1.0/link)

```
implementation 'com.chiului.library:dialogtool:1.1.0'
```

### 使用方式二：Module 方式

##### Step 1. 下载源码

##### Step 2. 引入 Module

> File --> New --> import Module


##### Step 3. 在主 Module 中添加 Module 依赖

```
implementation project(path: ':dialogtool')
```


# <span id = "2">**2.使用示例**</span>



### 最简单实现:

- 只需一行代码即可快速构建出 Dialog

1. 新建一个`new DialogTool.Builder()`
2. 调用`setView(R.layout.dialog_view)`传入一个你自己的布局
3. 调用`build()`即可快速构建出 dialog 实例


```

new DialogTool.Builder(this)
                .setView(R.layout.dialog_view)//传入你自己的自定义布局
                .build().show();


```


| [样式1](#2.1) | [样式2](#2.2) | [样式3](#2.3) |
| :-: | :-: | :-: |
| ![示例_0](https://github.com/ChiuLui/DialogTool/blob/master/image/dialog_0.gif) | ![示例_1](https://github.com/ChiuLui/DialogTool/blob/master/image/dialog_1.gif) | ![示例_2](https://github.com/ChiuLui/DialogTool/blob/master/image/dialog_2.gif) |


### <span id = "2.1">**构建一个居中显示、并给父布局设置点击事件的 Dialog 示例:**</span>


```
private void initDialog0() {
        mDialog0 = new DialogTool.Builder(this)
                .setView(R.layout.dialog_public_hint)//设置Dialog的布局View
                .setOnClickRoot(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //给整个Dialog的父布局设置点击事件
                        mDialog0.dismiss();
                    }
                })
                .build();
    }
```



### <span id = "2.2">**构建一个底下弹出、不响应外部点击、外部透明、响应按钮点击的 Dialog 示例:**</span>


```
private void initDialog1() {
        mDialog1 = new DialogTool.Builder(this)
                .setView(R.layout.dialog_select_item)//设置Dialog的布局View
                .setGravity(DialogTool.DialogGravity.CENTER_BOTTOM)//设置Dialog靠下弹出
                .setAlpha(0)//设置弹框外为透明
                .isClickOutSide(false)//设置不能点击外面
                .setOnClick(R.id.tv_one, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //设置某个View点击事件
                        mDialog1.dismiss();
                        Toast.makeText(MainActivity.this, "拍照", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnClick(R.id.tv_two, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog1.dismiss();
                        Toast.makeText(MainActivity.this, "从相册选择", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnClick(R.id.tv_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog1.dismiss();
                    }
                })
                .build();
    }
```



### <span id = "2.3">构建一个外部透明度百分之30、获取内部某个子View、动态设置内部标题文本的 Dialog 示例:**</span>


```
private void initDialog2() {
        mDialog2 = new DialogTool.Builder(this)
                .setView(R.layout.dialog_layout)//设置Dialog的布局View
                .isClickOutSide(false)//设置不能点击外面
                .setAlpha(0.3f)//设置弹框外为百分之30透明
                .getChildView(R.id.tv_title, new OnGetChildViewCallBack<View>() {
                    @Override
                    public void onChildView(View view) {
                        //获取Dialog的某个子View
                        TextView title = (TextView) view;
                        title.setText("温馨提醒");
                    }
                })
                .setText(R.id.tv_message, "这是一个有左右按钮有内容有标题的Dialog")//获取Dialog的某TextView文字
                .setText(R.id.tv_left, "确认")
                .setOnClick(R.id.tv_left, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //设置某个View点击事件
                        Toast.makeText(MainActivity.this, "点击确认", Toast.LENGTH_SHORT).show();
                        mDialog2.dismiss();
                    }
                })
                .setText(R.id.tv_right, "取消")
                .setOnClick(R.id.tv_right, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog2.dismiss();
                    }
                })
                .build();
    }
```



# <span id = "3">**3.API**</span>



|方法|说明|默认值|
|:-----|:-----|:----:|
| new DialogTool.Builder() | 创建构建 Dialog 的对象 |  |
| Builder.build() | 构建 Dialog 对象完成 |  |
| Builder.setStyle(int themeResId) | 设置自定义主题style | R.style.MyDialogStyle |
| Builder.setView(int resView) | 设置Dialog 布局文件ID | null |
| Builder.setView(View view) | 设置 Dialog 布局 View | null |
| Builder.isClickOutSide(boolean isClickOutSide) | 设置是否允许点击dialog外部关闭Dialog | true |
| Builder.setLayoutXY(int layoutX, int layoutY) | 设置 dialog 的相对于 `setGravity()` 定位的偏移 | -1（不偏移） |
| Builder.setGravity(DialogGravity gravity) | 设置 dialog 的相对定位如位于 Window 的上中下左右 | DialogGravity.CENTER（使用系统默认为中间） |
| Builder.setWidthAndHeight(int width, int height) | 设置 Dialog 的宽高 | -1（使用布局的宽高） |
| Builder.setHeight(int height) | 设置 Dialog 的高 | -1（使用布局的高） |
| Builder.setWidth(int width) | 设置 Dialog 的宽 | -1（使用布局的宽） |
| Builder.setAlpha(float alpha) | 设置dialog视图外的背景透明度：透明 0f ~ 1.0f 不透明 | -1（跟随系统） |
| Builder.setOnClick(int viewRes, View.OnClickListener listener) | 设置Dialog布局里控件view的监听 |  |
| Builder.setOnClickRoot(View.OnClickListener listener) | 设置Dialog根布局的点击事件 |  |
| Builder.setText(int viewRes, String content) | 设置Dialog子布局TextView的文字 |  |
| Builder.getChildView(int viewRes, OnGetChildViewCallBack<View> callBack) | 获取Dialog布局里的View |  |
| Builder.getChildTextView(int viewRes, OnGetChildViewCallBack<TextView> callBack) | 获取Dialog布局里的TextView |  |
| Builder.getChildImageView(int viewRes, OnGetChildViewCallBack<ImageView> callBack) | 获取Dialog布局里的ImageView |  |
| DialogTool.getRootView() | 获取 Dialog 根布局 View |  |




