package com.my.zx.model;

public class AdPhotoMo {

	private String title;
	private String type;
	private String time;
	private boolean isTitleRed; 	//false
	private String img; 	//图片地址
	private String url;		//跳转的h5地址

	public AdPhotoMo() {
		super();
	}


	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setIsTitleRed(boolean isTitleRed) {
		this.isTitleRed = isTitleRed;
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
