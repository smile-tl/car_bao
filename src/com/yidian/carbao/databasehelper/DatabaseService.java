package com.yidian.carbao.databasehelper;

import com.yidian.carbao.entity.EntityUserInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseService {
	private SQLiteDatabase sqLiteDatabase;
	private static DatabaseHelper databaseHelper;
	private static final String tableNameUserInfo = "userinfo";

	/**
	 * 主构造，第一次调用的时候就实例化databasehelper对象
	 * 
	 * @param context
	 */
	public DatabaseService(Context context) {
		if (databaseHelper == null) {
			databaseHelper = new DatabaseHelper(context);
		}
	}

	/**
	 * 插入用户信息
	 * 
	 * @param params
	 */
	public void InsertUserInfo(EntityUserInfo entityUserInfo) {
		sqLiteDatabase = databaseHelper.getWritableDatabase();
		sqLiteDatabase.insert(tableNameUserInfo, null,
				getContentUserInfo(entityUserInfo));
		sqLiteDatabase.close();
	}

	/**
	 * 删除用户信息
	 * 
	 * @param userId
	 */
	public void DeleteUserInfo() {
		sqLiteDatabase = databaseHelper.getWritableDatabase();
		if (sqLiteDatabase.isOpen()) {
			sqLiteDatabase.delete(tableNameUserInfo, null, null);
			sqLiteDatabase.close();
		}
	}

	/**
	 * 通过用户id修改用户信息
	 * 
	 * @param params
	 */
	public void UpdataUserInfo(EntityUserInfo entityUserInfo) {
		sqLiteDatabase = databaseHelper.getWritableDatabase();
		sqLiteDatabase.update(tableNameUserInfo,
				getContentUserInfo(entityUserInfo), "uid=?",
				new String[] { entityUserInfo.getId() });
		sqLiteDatabase.close();
	}

	/**
	 * 查询用户信息并封装成对象返回
	 */
	public EntityUserInfo QueryUserInfo() {
		EntityUserInfo entityUserInfo = null;
		sqLiteDatabase = databaseHelper.getReadableDatabase();
		Cursor cursor = sqLiteDatabase.query(tableNameUserInfo, null, null,
				null, null, null, null);
		if (cursor.moveToFirst()) {
			entityUserInfo =  new EntityUserInfo();
			String uid = cursor.getString(cursor.getColumnIndex("uid"));
			String uname = cursor.getString(cursor.getColumnIndex("uname"));
			String pdw = cursor.getString(cursor.getColumnIndex("pdw"));
			entityUserInfo.setUid(uid);
			entityUserInfo.setUname(uname);
			entityUserInfo.setPdw(pdw);
			entityUserInfo.setClasss(cursor.getString(cursor.getColumnIndex("classs")));
			entityUserInfo.setCname(cursor.getString(cursor.getColumnIndex("cname")));
			entityUserInfo.setMark(cursor.getString(cursor.getColumnIndex("mark")));
			entityUserInfo.setIntro(cursor.getString(cursor.getColumnIndex("intro")));
			entityUserInfo.setProvince(cursor.getString(cursor.getColumnIndex("province")));
			entityUserInfo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
			entityUserInfo.setWork_time(cursor.getString(cursor.getColumnIndex("work_time")));
			entityUserInfo.setContact_p(cursor.getString(cursor.getColumnIndex("contact_p")));
			entityUserInfo.setContact_phone(cursor.getString(cursor.getColumnIndex("contact_phone")));
			entityUserInfo.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
			entityUserInfo.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
			entityUserInfo.setReg_time(cursor.getString(cursor.getColumnIndex("reg_time")));
			entityUserInfo.setLogin_time(cursor.getString(cursor.getColumnIndex("login_time")));
			entityUserInfo.setStar(cursor.getString(cursor.getColumnIndex("star")));
			entityUserInfo.setTop(cursor.getString(cursor.getColumnIndex("top")));
			entityUserInfo.setTop_time(cursor.getString(cursor.getColumnIndex("top_time")));
		}
		sqLiteDatabase.close();
		return entityUserInfo;
	}

	/**
	 * 得到用户封装对象
	 * 
	 * @param params
	 *            String数组
	 * @return ContentValues
	 */
	private ContentValues getContentUserInfo(EntityUserInfo entityUserInfo) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("uid", entityUserInfo.getUid());
		contentValues.put("uname", entityUserInfo.getUname());
		contentValues.put("pdw", entityUserInfo.getPdw());
		contentValues.put("classs", entityUserInfo.getClasss());
		contentValues.put("cname", entityUserInfo.getCname());
		contentValues.put("mark", entityUserInfo.getMark());
		contentValues.put("intro", entityUserInfo.getIntro());
		contentValues.put("province", entityUserInfo.getProvince());
		contentValues.put("address", entityUserInfo.getAddress());
		contentValues.put("work_time", entityUserInfo.getWork_time());
		contentValues.put("contact_p", entityUserInfo.getContact_p());
		contentValues.put("contact_phone", entityUserInfo.getContact_phone());
		contentValues.put("longitude", entityUserInfo.getLongitude());
		contentValues.put("latitude", entityUserInfo.getLatitude());
		contentValues.put("reg_time", entityUserInfo.getReg_time());
		contentValues.put("login_time", entityUserInfo.getLogin_time());
		contentValues.put("star", entityUserInfo.getStar());
		contentValues.put("top", entityUserInfo.getTop());
		contentValues.put("top_time", entityUserInfo.getTop_time());
		return contentValues;
	}
}