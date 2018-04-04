package com.jessechiu.android.ontepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 继承 AppCompatActivity 类，
public class MainActivity extends AppCompatActivity {

    // 存储信息列表
    private List<CostBean> mCostBeanList;
    // 显示信息适配层
    private CostListAdapter costListAdapter;
    // 数据库操作句柄
    private DataBaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置主样式
        setContentView(R.layout.activity_main);

        // 初始化顶部工具条
        initToolbar();
        // 初始化数据
        initCostData();
        // 初始化浮动按键
        initFloatBtn();
    }

    // 初始顶部工具条
    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // 初始化浮动按键事件
    private void initFloatBtn(){
        // 设置浮动按键事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 注意这里实例化 builder this 作用域
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

                // 设置 view 样式布局
                View viewDialog = inflater.inflate(R.layout.new_cost_data,null);
                // 获取布局元素
                final EditText title = (EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText money = (EditText) viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.dp_cost_date);

                // 设置标题
                builder.setTitle("New Cost");
                // 显示弹框
                builder.setView(viewDialog);

                // 绑定点击 OK 按键事件
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        CostBean costBean = new CostBean();
                        costBean.costTitle = title.getText().toString();
                        costBean.costMoney = money.getText().toString();
                        // 注意这里的日期拼接
                        costBean.costDate = date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDayOfMonth();
                        // 插入数据库
                        mDataBaseHelper.insert(costBean);
                        // 显示列表中添加
                        mCostBeanList.add(costBean);
                        // 通知 adapter 更新数据
                        costListAdapter.notifyDataSetChanged();
                    }
                });

                // 取消事件
                builder.setNegativeButton("Cancel",null);
                // 显示对话框
                builder.create().show();
            }
        });
    }

    // mock 数据
    private void mockData(){
        // ctrl+alt+t: 选择语句
        // shift+f6: 统一修改选中部分
        for (int i=0;i<10;i++) {
            CostBean costBean = new CostBean();
            costBean.costTitle = ""+i;
            costBean.costDate = "2018-04-0" + i ;
            costBean.costMoney = "100" + i;
            mCostBeanList.add(costBean);
        }
    }

    // 初始化数据
    private void initCostData() {
        mCostBeanList = new ArrayList<>();
        mDataBaseHelper = new DataBaseHelper(this);

        // 从数据库中取出数据
        Cursor cursor = mDataBaseHelper.getAllCursorDate();
        if(cursor != null){
            while(cursor.moveToNext()){
                CostBean costBean = new CostBean();
                costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costBean);
            }
            cursor.close();
        }

        ListView costList = findViewById(R.id.lv_main);
        costListAdapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(costListAdapter);
    }

    // 顶部菜单项设置
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // 设置工具条右边菜单项
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 顶部菜单事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_chart) {
            // 调用另一个 active
            Intent intent = new Intent(this,ChartsActivity.class);
            // 序列化数据，方可通过 intent 传递
            intent.putExtra("cost_list",(Serializable) mCostBeanList);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
