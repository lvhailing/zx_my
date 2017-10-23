package com.my.zx.model;

import java.io.Serializable;


public class SecondLevelMo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String title;	 
	private String type; 		 
	private String url; 		 
	private String time; 
	private boolean isTitleRed; 	//false		 
	
	public SecondLevelMo() {
		super();
	}

	
	public String getTitle() {
		return title;
	}

	
	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	
	public String getUrl() {
		return url;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}

	
	public String getTime() {
		return time;
	}

	
	public void setTime(String time) {
		this.time = time;
	}


	
	public boolean isTitleRed() {
		return isTitleRed;
	}


	
	public void setTitleRed(boolean isTitleRed) {
		this.isTitleRed = isTitleRed;
	}


}
