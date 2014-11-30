package com.yidian.carbao.util;

import android.content.Context;

import com.yidian.carbao.databasehelper.DatabaseService;
import com.yidian.carbao.entity.EntityUserInfo;


public class Util {

	public static String HTTP_API_REGISTER_URL="http://115.28.86.228/car_treasure/Interface.php";
	public static String HTTP_API_REGISTER ="user_reg";
	
	
	
	
	
	
	/**
	 * 内存中存储用户信息对象
	 */
	public static EntityUserInfo entityUserInfo;
	
	
	
	
	
	/**
	 * 将用户信息插入到数据库，同时保存到内存中
	 * 
	 * @param context
	 * @param temp
	 */
	public static void InsertUserInfo(Context context, EntityUserInfo temp) {
		entityUserInfo = temp;
		new DatabaseService(context).InsertUserInfo(temp);
	}
	
	
	
	public static void UpdateUserInfo(Context context, EntityUserInfo temp){
		entityUserInfo = temp;
		new DatabaseService(context).UpdataUserInfo(temp);;
	}
	
	
	/**
	 * 如果内存中用户信息为空，则从数据库中获取
	 * 
	 * @param context
	 * @return
	 */
	public static EntityUserInfo GetUserInfo(Context context) {
		if (null == entityUserInfo) {
			entityUserInfo = new DatabaseService(context).QueryUserInfo();
		}
		return entityUserInfo;
	}

	
	
}
