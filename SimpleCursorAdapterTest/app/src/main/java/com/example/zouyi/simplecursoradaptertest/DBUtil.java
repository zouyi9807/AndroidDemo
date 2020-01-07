package com.example.zouyi.simplecursoradaptertest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static android.content.ContentValues.TAG;


public class DBUtil {

    public static void createTable() {                // 创建学生信息表
        SQLiteDatabase studentData = null;
        studentData = SQLiteDatabase.openDatabase(
                "/data/data/com.example.zouyi.simplecursoradaptertest/studentdata.db", null,
                SQLiteDatabase.OPEN_READWRITE
                        | SQLiteDatabase.CREATE_IF_NECESSARY);
        String sqlstudent = "create table if not exists stuinformation(";
        sqlstudent += "_id integer PRIMARY KEY,name varchar(20),age integer)";
        studentData.execSQL(sqlstudent);
    }

    public static void insertdata()              //插入20条学生信息数据
    {
        SQLiteDatabase studentData = null;
        studentData = SQLiteDatabase.openDatabase(
                "/data/data/com.example.zouyi.simplecursoradaptertest/studentdata.db", null,
                SQLiteDatabase.OPEN_READWRITE
                        | SQLiteDatabase.CREATE_IF_NECESSARY);
        //用循环的方式来插入20条数据
        for (int i = 1; i <= 20; i++) {
            String insertSql = "insert into stuinformation (name,age) values('zouyi'," + i + ")";
            Log.e(TAG, "insertdata: " + insertSql);
            studentData.execSQL(insertSql);
        }
    }

    public static Cursor getCursor()             //查询学生信息，获取Cursor
    {
        SQLiteDatabase studentData = null;
        studentData = SQLiteDatabase.openDatabase(
                "/data/data/com.example.zouyi.simplecursoradaptertest/studentdata.db", null,
                SQLiteDatabase.OPEN_READWRITE
                        | SQLiteDatabase.CREATE_IF_NECESSARY);
        String Sql = "select * from stuinformation;";
        Cursor cur = studentData.rawQuery(Sql, null);
        return cur;
    }

    public static void deletedata()              //插入20条学生信息数据
    {
        SQLiteDatabase studentData = null;
        studentData = SQLiteDatabase.openDatabase(
                "/data/data/com.example.zouyi.simplecursoradaptertest/studentdata.db", null,
                SQLiteDatabase.OPEN_READWRITE
                        | SQLiteDatabase.CREATE_IF_NECESSARY);
        String deleteSql = "delete from stuinformation where name = 'zouyi'";
        //用循环的方式来插入20条数据
        for (int i = 0; i < 20; i++) {
            studentData.execSQL(deleteSql);
        }
    }
}
