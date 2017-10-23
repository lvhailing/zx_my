package com.my.zx.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
	public static final String FILE_NAME = "ziruuuuu";

	/**
	 * 初始化文件
	 */
	public static void init(Context context) {
		// 创建应用根目录
		File basedir = new File(getBaseDir(context));
		if (!basedir.exists()) {
			basedir.mkdirs();
		}
	}

	/**
	 * 获取应用的路径
	 * 
	 * @return
	 */
	public static String getBaseDir(Context context) {
		String baseDir = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			baseDir = Environment.getExternalStorageDirectory() + "/" + FILE_NAME + "/";
		} else {
			baseDir = context.getCacheDir().getAbsolutePath() + "/" + FILE_NAME + "/";
		}
		File basedir = new File(baseDir);
		if (!basedir.exists()) {
			basedir.mkdirs();
		}
		return baseDir;
	}

	/**
	 * 获取二级目录 caiman/
	 * 
	 * @param context
	 * @return
	 */
	public static String getSecondDir(Context context, String dirname) {
		String basedir = getBaseDir(context);
		String dir = basedir + dirname;
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		return dir;
	}


	/**
	 * 将文件移动到目标位置
	 * 
	 * @param context
	 * @param source
	 * @param dest
	 */
	public static void moveFileToDir(Context context, Uri source, Uri dest) {
		InputStream is = null;
		OutputStream fos = null;
		try {
			is = context.getContentResolver().openInputStream(source);
			if (is != null) {
				fos = context.getContentResolver().openOutputStream(dest);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((is.read(buffer)) != -1) {
					fos.write(buffer, 0, buffer.length);
				}

				fos.flush();
			}
		} catch (Exception e) {
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}

	}

	public static String getDownloadCacheFile(Context context) {
		String dir = getSecondDir(context, "cache");
		return dir + "/" + System.currentTimeMillis() + ".tmp";
	}

	// 获取存储图片的本地路径
	public static String getCommonLocalFile(Context context, String imageUrl, String filepath) {
		try {
			String dir = getSecondDir(context, filepath);
			String urlfilename = null;
			if (imageUrl != null && imageUrl.trim().length() > 0) {
				urlfilename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
				urlfilename = urlfilename.substring(0, urlfilename.lastIndexOf("."));
			}
			StringBuilder sb = new StringBuilder();
			sb.append(dir).append("/").append(urlfilename).append(".jpg");
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
				return;
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
		}
	}

}
