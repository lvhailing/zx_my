package com.my.zx.utils.fresco;

import com.my.zx.MyApplication;

import java.io.File;

public class FileUtil {

    private static FileUtil instance;
    private final static String TAG = FileUtil.class.getSimpleName();

    /**
     * 图片保存SD卡目录
     */
    public static final String ROOT = "zx";

    private FileUtil() {
    }

    public static FileUtil getInstance() {
        if (instance == null) {
            synchronized (FileUtil.class) {
                instance = new FileUtil();
            }
        }
        return instance;
    }


    /**
     * 创建缺省的文件夹
     *
     * @return
     */
    public String createDefaultFolder() {
        String folderPath = getDefaultFolderPath();
        if (folderPath == null) {
            return null;
        }
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdir();
        }
        return folderPath;
    }

    /**
     * 得到文件夹缺省路径 因为有的时候 可能没有sdcard
     *
     * @return 注意判断是否为null
     */
    private String getDefaultFolderPath() {
        String folderUrl = null;
        if (isSDCardWritable()) {
            // 有读写权限
            String pathFile = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
            folderUrl = pathFile + File.separator + ROOT;

        } else {
            // 没有读写能力
            folderUrl = MyApplication.instance.getFilesDir().getAbsolutePath() + File.separator + ROOT;
        }
        return folderUrl;
    }

    /**
     * 判断sd卡读写能力
     *
     * @return
     */
    private boolean isSDCardWritable() {
        return android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
    }


}
