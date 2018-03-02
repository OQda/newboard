package com.myboard.hiber;

import java.sql.Timestamp;

public class OneData {
	private int num, count, rep;
	private String title, context, id, pdate;
	private Timestamp wdate;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Timestamp getWdate() {
		return wdate;
	}

	public void setWdate(Timestamp wdate) {
		this.wdate = wdate;
	}

	public int getRep() {
		return rep;
	}

	public void setRep(int rep) {
		this.rep = rep;
	}

	public String getPdate() {		
		return wdate.toString().substring(0,19);
	}

	public void setPdate() {
		this.pdate = wdate.toString().substring(0,19);
	}

	

}
