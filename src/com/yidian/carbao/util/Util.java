package com.yidian.carbao.util;

import android.content.Context;

import com.yidian.carbao.databasehelper.DatabaseService;
import com.yidian.carbao.entity.EntityUserInfo;


public class Util {

	public static String HTTP_API_REGISTER_URL="http://115.28.86.228/car_treasure/Interface.php";
	public static String HTTP_API_REGISTER ="user_reg";
	
	
	
	
	
	
	/**
	 * �ڴ��д洢�û���Ϣ����
	 */
	public static EntityUserInfo entityUserInfo;
	
	
	
	
	
	/**
	 * ���û���Ϣ���뵽���ݿ⣬ͬʱ���浽�ڴ���
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
	 * ����ڴ����û���ϢΪ�գ�������ݿ��л�ȡ
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
