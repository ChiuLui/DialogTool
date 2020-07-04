package com.chiului.dialogtool;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnShowDialog_0;
    private Button btnShowDialog_1;
    private Button btnShowDialog_2;
    private DialogTool mDialog0;
    private DialogTool mDialog1;
    private DialogTool mDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowDialog_0 = findViewById(R.id.button);
        btnShowDialog_1 = findViewById(R.id.button2);
        btnShowDialog_2 = findViewById(R.id.button3);

        btnShowDialog_0.setOnClickListener(this);
        btnShowDialog_1.setOnClickListener(this);
        btnShowDialog_2.setOnClickListener(this);

        initDialog0();
        initDialog1();
        initDialog2();

    }

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

    private void initDialog1() {
        mDialog1 = new DialogTool.Builder(this)
                .setView(R.layout.dialog_select_item)//设置Dialog的布局View
                .setGravity(DialogTool.DialogGravity.CENTER_BOTTOM)//设置Dialog靠下弹出
                .setStyle(R.style.DialogToolStyle_Bottom)
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                mDialog0.show();
                break;
            case R.id.button2:
                mDialog1.show();
                break;
            case R.id.button3:
                mDialog2.show();
                break;
            default:
        }
    }
}
