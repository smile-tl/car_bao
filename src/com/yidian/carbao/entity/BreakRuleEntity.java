package com.yidian.carbao.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.R.string;
import android.os.Parcel;
import android.os.Parcelable;

public class BreakRuleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String money;
	private String fen;
	private String area;
	private String act;
	private String date;
	private String cartype;


	public static  Map<String,String> carType = new HashMap<String, String>();
	
	
	public BreakRuleEntity() {
		super();
		
		
	}

	public String getCartype() {
		return cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getFen() {
		return fen;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}

	public String getArea() {
		return area;
	}  

	public void setArea(String area) {
		this.area = area;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

}
