package com.yidian.carbao.databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int Version = 1;
	private static final String DB_NAME = "carbao.db";
	// 创建用户表
	private static final String sql_userinfo = "create table userinfo (uid varchar(8) not null, uname varchar(50) , pdw varchar(30) , classs varchar(8) , cname varchar(50) , mark varchar(20) , intro varchar(80)   , province varchar(20) ,address  varchar(80) ,work_time varchar(80),contact_p varchar(20), contact_phone varchar(20),longitude varchar(40),latitude varchar(40),reg_time varchar(40),login_time varchar(40) ,star varchar(20),top varchar(40),top_time varchar(40) );";

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, Version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sql_userinfo);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}