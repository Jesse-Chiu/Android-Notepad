package com.jessechiu.android.ontepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// 继承 SQL 接口类
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String NOTEPAD_DB = "notepad";
    public static final String COST_TITLE = "cost_title";
    public static final String COST_DATE = "cost_date";
    public static final String COST_MONEY = "cost_money";

    // 构造函数
    public DataBaseHelper(Context context){
        // 创建数据库
        super(context, NOTEPAD_DB,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表格
        // 注意字符串拼接
        db.execSQL("create table if not exists notepad(" +
                "id integer primary key," +
                COST_TITLE + " varchar," +
                COST_DATE + " varchar," +
                COST_MONEY + " varchar)");
    }

    // 插入数据方法
    public void insert(CostBean bean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COST_TITLE,bean.costTitle);
        cv.put(COST_DATE,bean.costDate);
        cv.put(COST_MONEY,bean.costMoney);

        db.insert(NOTEPAD_DB,null,cv);
    }

    // 数据查询
    public Cursor getAllCursorDate(){
        SQLiteDatabase db = getWritableDatabase();
        return db.query(NOTEPAD_DB,null,null,null,null,null, COST_DATE + " ASC");
    }

    // 删除所有数据
    public void deleteAllData(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(NOTEPAD_DB,null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
