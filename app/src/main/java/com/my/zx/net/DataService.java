package com.my.zx.net;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataService {

	static ExecutorService executorService = Executors.newFixedThreadPool(5);// Executors.newCachedThreadPool();

	// 登录
	public static void login(final Handler handler, final String name, final String pas) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				ResultObject resultObject = new JsonRequest().login(name, pas);
				MessageManager.sendMessage(handler, MesType.LOGIN, resultObject);
			}
		});
	}
	
	// 获取用户首页信息
	public static void getHomeList(final Handler handler) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				ResultObject resultObject = new JsonRequest().getHomeList();
				MessageManager.sendMessage(handler, MesType.GETHOMELIST, resultObject);
			}
		});
	}

	//用户点击首页进入到子列表页
	public static void getSecondLevelList(final Handler handler, final String mUrl) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				ResultObject resultObject = new JsonRequest().getSecondLevelList(mUrl);
				MessageManager.sendMessage(handler, MesType.GETHOME_ITEMLIST, resultObject);
			}
		});
	}

	public static void checkVersion(final Handler handler, final String vname, final int vcode) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				ResultObject resultObject = new JsonRequest().checkVersion(vname, vcode);
				MessageManager.sendMessage(handler, MesType.VERSION, resultObject);
			}
		});
	}

	public static void getHomeAdPhotos(final Handler handler) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					ResultObject resultObject = new JsonRequest().getHomeAdPhotos();
					MessageManager.sendMessage(handler, MesType.Ad_Photos, resultObject);
				}
			});
	}

	public static void feedBack(final Handler handler, final String content) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					ResultObject resultObject = new JsonRequest().feedBack(content);
					MessageManager.sendMessage(handler, MesType.FEEDBACK, resultObject);
				}
			});
	}
}
