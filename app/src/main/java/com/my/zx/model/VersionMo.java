package com.my.zx.model;

public class VersionMo {

	private String upgradeTitle; //版本名称
	private String upgradeContent; //版本描述
	private String upgradeUrl; //版本下载地址
	private String version; //1.0 ，清单文件的：android:versionName="1.0"
	private long versionInt; //版本号 清单文件的：android:versionCode="1"
	private int isMust; //是否强制安装
	
	private String publishTime; //发布时间
	private String size; //文件大小
	private boolean isLastest; //是否最新版本

	public VersionMo() {
		super();
	}

	public String getUpgradeTitle() {
		return upgradeTitle;
	}

	public void setUpgradeTitle(String upgradeTitle) {
		this.upgradeTitle = upgradeTitle;
	}

	public String getUpgradeContent() {
		return upgradeContent;
	}

	public void setUpgradeContent(String upgradeContent) {
		this.upgradeContent = upgradeContent;
	}

	public String getUpgradeUrl() {
		return upgradeUrl;
	}

	public void setUpgradeUrl(String upgradeUrl) {
		this.upgradeUrl = upgradeUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getVersionInt() {
		return versionInt;
	}

	public void setVersionInt(long versionInt) {
		this.versionInt = versionInt;
	}

	public int isMust() {
		return isMust;
	}

	public void setMust(int isMust) {
		this.isMust = isMust;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public boolean isLastest() {
		return isLastest;
	}

	public void setLastest(boolean isLastest) {
		this.isLastest = isLastest;
	}

}
