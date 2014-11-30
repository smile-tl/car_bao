package com.yidian.carbao.entity;

import java.io.Serializable;

public class EntityBase implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private int s = 0;
	private String e = "";
	private String id = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}
}