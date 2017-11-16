package com.my.zx.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.my.zx.Constant;
import com.my.zx.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * 图片加载
 *
 * @author pangrui
 */

public class ImageLoadUtil {
    private ImageView mImageView;
    private String mUrl;

    public ImageLoadUtil(String url, ImageView mImageView) {
        this.mImageView = mImageView;
        this.mUrl = url;
        NewsAynsTaskImgView task = new NewsAynsTaskImgView();
        task.execute();
    }

    public Bitmap downloadBitmap() {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(new URI(mUrl));

            httpGet.setHeader("Cookie", Util.getCookieString());

            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();

                is = httpEntity.getContent();
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 2;
                opts.inJustDecodeBounds = false;

                bitmap = BitmapFactory.decodeStream(is, null, opts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;

//		FileOutputStream fos = null;
//		InputStream is = null;
//		try {
//			URL url = new URL(mUrl);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			is = new BufferedInputStream(conn.getInputStream());
//			is = conn.getInputStream();
//
//			Options opts = new Options();
//			opts.inSampleSize = 5;
//			opts.inJustDecodeBounds = false;
//
//			Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);
//
//			conn.disconnect();
//			return bitmap;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try { if (is != null) is.close(); } catch (IOException e) { }
//			try { if (fos != null) fos.close(); } catch (IOException e) { }
//		}
//		return null;
    }

    private class NewsAynsTaskImgView extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = downloadBitmap();
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageView.setImageBitmap(bitmap);
        }
    }

    public static int matchImage(long id) {
        int rId;
        int intId = (int) id;
        switch (intId) {
            case Constant.PHONE: // 通讯录
                rId = R.drawable.home_phone_normal4;
                break;
            case Constant.DAIBAN: // 代办
                rId = R.drawable.home_daiban_normal3;
                break;
            case Constant.DAIYUE: // 待阅 未读消息
                rId = R.drawable.home_daiyue_normal4;
                break;
            case Constant.YIBAN: // 已办
                rId = R.drawable.home_yiban_normal1;
                break;
            case Constant.YIYUE: // 已阅 已读消息
                rId = R.drawable.home_yiyue_normal1;
                break;
            case Constant.INFO: // 通知公告
                rId = R.drawable.home_tongzhi_gonggao;
                break;
            case Constant.ZICHAN_GONGSI_DONGTAI: // 公司资产动态
                rId = R.drawable.home_zichan_nomal3;
                break;
            case Constant.BOSS: // 领导动态
                rId = R.drawable.home_boss_normal3;
                break;
            case Constant.CHANGE:        //修改密码
                rId = R.drawable.home_change_normal3;
                break;
            case Constant.ZIGOGNSI_DONGTAI: // 子公司动态
                rId = R.drawable.home_zgs_normal5;
                break;
            case Constant.EMAIL: // 邮件
                rId = R.drawable.home_email_normal3;
                break;
            case Constant.FENGOGNSI_DONGTAI: // 分公司动态
                rId = R.drawable.home_fgs_normal3;
                break;
            case Constant.ZHINENG: // 职能部门动态
//                rId = R.drawable.home_zhineng_normal;
                rId = R.drawable.home_phone_normal4;
                break;
            case Constant.QIANG: // 强哥心语
                rId = R.drawable.home_qiang_normal4;
                break;
            case Constant.COFFE: // 强哥 coffee time
                rId = R.drawable.home_coffe_normal4;
                break;
            case Constant.TUPIAN_NEWS: // 图片新闻
                rId = R.drawable.home_tupian_news_normal;
                break;
            case Constant.DANGWEI_JIYAO: // 党委会纪要
                rId = R.drawable.home_dangwei_jiyao;
                break;
            case Constant.BANGONG_JIYAO: // 办公会纪要
                rId = R.drawable.home_bangong_jiyao;
                break;
            case Constant.ZHUANTI_JIYAO: // 专题会纪要
                rId = R.drawable.home_zhuanti_jiyao;
                break;
            case Constant.GONGZUO_JIANBAO: // 工作简报
                rId = R.drawable.home_gongzuo_jianbao;
                break;
            case Constant.DANGJIAN_GONGZUO: // 党建工作
                rId = R.drawable.home_dangjian_gongzuo;
                break;
            case Constant.JIJIAN_JIANCHA: // 纪检监察
                rId = R.drawable.home_jijian_jiancha;
                break;
            case Constant.GONGHUI_TUANWEI: // 工会团委
                rId = R.drawable.home_gonghui_tuanwei;
                break;
            case Constant.TUANWEI_DONGTAI: // 团委动态
                rId = R.drawable.home_tuanwei_dongtai;
                break;
            case Constant.RENSHI_XINXI: // 人事信息
                rId = R.drawable.home_renshi_xinxi;
                break;
            case Constant.QINGJIA_SHENQING: // 请假申请
                rId = R.drawable.home_qingjia_shenqing;
                break;
            case Constant.LINGDAO_QINGJIA: // 领导请假
                rId = R.drawable.home_lingdao_qingjia;
                break;
            case Constant.YINGONG_CHUCHAI: // 因公出差
                rId = R.drawable.home_yingong_chuchai;
                break;
            case Constant.ZICHAN_LINGDAO_CHUCHAI: // 资产领导出差
                rId = R.drawable.home_zichan_lingdao_chuchai;
                break;
            case Constant.YINGONG_CHURUJING: // 因公出入境
                rId = R.drawable.home_yingong_churujing;
                break;
            case Constant.YINSI_CHURUJING: // 因私出入境
                rId = R.drawable.home_yinsi_churujing;
                break;
            case Constant.IT_SHENQING: // IT申请
                rId = R.drawable.home_it_shenqing;
                break;
            case Constant.WEITUO_SHOUQUAN: // 授权委托
                rId = R.drawable.home_weituo_shouquan;
                break;
            default:
                rId = R.drawable.home_phone_normal4;
                break;
        }
        return rId;
    }
}
