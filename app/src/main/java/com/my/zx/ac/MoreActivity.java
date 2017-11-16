package com.my.zx.ac;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.my.zx.Constant;
import com.my.zx.R;
import com.my.zx.model.HomeMo;
import com.my.zx.net.DataService;
import com.my.zx.net.MesType;
import com.my.zx.net.ResultObject;
import com.my.zx.utils.ToastUtil;
import com.my.zx.utils.Util;

import java.util.List;
import java.util.Map;

/**
 * 更多页面，可以点击进二级页面
 */

public class MoreActivity extends BaseActivity implements View.OnClickListener {
    private List<HomeMo> jsonList;    //后台集合

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ResultObject ro = (ResultObject) msg.obj;
            if (ro.getCode() == 100) {
                //session过期，则吐司返回
                ToastUtil.showToast(ro.getMessage());
                return;
            }
            switch (msg.what) {
                case MesType.GETHOMELIST:
                    closeWaitProgress();
                    if (ro.getSuccess()) {
                        Map<String, Object> resultMap = (Map<String, Object>) ro.getObject();
                        jsonList = (List<HomeMo>) resultMap.get("list");
                    } else {
                        Toast.makeText(MoreActivity.this, "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();
        initHomeList();
    }

    private void initHomeList() {
        if (Util.checkLogin()) {
            //只有登录了，才去后台取数据
            showWaitProgress();
            DataService.getHomeList(handler);
        }
    }

    private void initView() {
        RelativeLayout rl_qiang_xinyu = (RelativeLayout) findViewById(R.id.rl_qiang_xinyu);
        RelativeLayout rl_qiang_coffee = (RelativeLayout) findViewById(R.id.rl_qiang_coffee);
        RelativeLayout rl_dangwei_jiyao = (RelativeLayout) findViewById(R.id.rl_dangwei_jiyao);
        RelativeLayout rl_bangong_jiyao = (RelativeLayout) findViewById(R.id.rl_bangong_jiyao);
        RelativeLayout rl_zhuanti_jiyao = (RelativeLayout) findViewById(R.id.rl_zhuanti_jiyao);
        RelativeLayout rl_gongzuo_jianbao = (RelativeLayout) findViewById(R.id.rl_gongzuo_jianbao);
        RelativeLayout rl_daiban = (RelativeLayout) findViewById(R.id.rl_daiban);
        RelativeLayout rl_yiban = (RelativeLayout) findViewById(R.id.rl_yiban);
        RelativeLayout rl_daiyue = (RelativeLayout) findViewById(R.id.rl_daiyue);
        RelativeLayout rl_yiyue = (RelativeLayout) findViewById(R.id.rl_yiyue);
        RelativeLayout rl_qingjia_shenqing = (RelativeLayout) findViewById(R.id.rl_qingjia_shenqing);
        RelativeLayout rl_lingdao_qingjia = (RelativeLayout) findViewById(R.id.rl_lingdao_qingjia);
        RelativeLayout rl_yingong_chuchai = (RelativeLayout) findViewById(R.id.rl_yingong_chuchai);
        RelativeLayout rl_lingdao_chuchai = (RelativeLayout) findViewById(R.id.rl_zichan_lingdao_chuchai);
        RelativeLayout rl_yingong_churujing = (RelativeLayout) findViewById(R.id.rl_yingong_churujing);
        RelativeLayout rl_yinsi_churujing = (RelativeLayout) findViewById(R.id.rl_yinsi_churujing);
        RelativeLayout rl_it_shenqing = (RelativeLayout) findViewById(R.id.rl_it_shenqing);
        RelativeLayout rl_shouquan_weituo = (RelativeLayout) findViewById(R.id.rl_shouquan_weituo);
        RelativeLayout rl_dangjian_gongzuo = (RelativeLayout) findViewById(R.id.rl_dangjian_gongzuo);
        RelativeLayout rl_jijian_jiancha = (RelativeLayout) findViewById(R.id.rl_jijian_jiancha);
        RelativeLayout rl_gonghui_tuanwei = (RelativeLayout) findViewById(R.id.rl_gonghui_tuanwei);
        RelativeLayout rl_tuanwei_dongtai = (RelativeLayout) findViewById(R.id.rl_tuanwei_dongtai);
        RelativeLayout rl_zichan_dongtai = (RelativeLayout) findViewById(R.id.rl_zichan_dongtai);
        RelativeLayout rl_fengongsi_dongtai = (RelativeLayout) findViewById(R.id.rl_fengongsi_dongtai);
        RelativeLayout rl_zigongsi_dongtai = (RelativeLayout) findViewById(R.id.rl_zigongsi_dongtai);
        RelativeLayout rl_renshi_xinxi = (RelativeLayout) findViewById(R.id.rl_renshi_xinxi);
        RelativeLayout rl_tongzhi_gonggao = (RelativeLayout) findViewById(R.id.rl_tongzhi_gonggao);
        RelativeLayout rl_lingdao_dongtai = (RelativeLayout) findViewById(R.id.rl_lingdao_dongtai);

        RelativeLayout rl_add_qiang_coffee = (RelativeLayout) findViewById(R.id.rl_add_qiang_coffee);
        RelativeLayout rl_add_qiang_xinyu = (RelativeLayout) findViewById(R.id.rl_add_qiang_xinyu);
        RelativeLayout rl_add_dangwei_jiyao = (RelativeLayout) findViewById(R.id.rl_add_dangwei_jiyao);
        RelativeLayout rl_add_bangong_jiyao = (RelativeLayout) findViewById(R.id.rl_add_bangong_jiyao);
        RelativeLayout rl_add_zhuanti_jiyao = (RelativeLayout) findViewById(R.id.rl_add_zhuanti_jiyao);
        RelativeLayout rl_add_gongzuo_jianbao = (RelativeLayout) findViewById(R.id.rl_add_gongzuo_jianbao);
        RelativeLayout rl_add_daiban = (RelativeLayout) findViewById(R.id.rl_add_daiban);
        RelativeLayout rl_add_yiban = (RelativeLayout) findViewById(R.id.rl_add_yiban);
        RelativeLayout rl_add_daiyue = (RelativeLayout) findViewById(R.id.rl_add_daiyue);
        RelativeLayout rl_add_yiyue = (RelativeLayout) findViewById(R.id.rl_add_yiyue);
        RelativeLayout rl_add_qingjia_shenqing = (RelativeLayout) findViewById(R.id.rl_add_qingjia_shenqing);
        RelativeLayout rl_add_lingdao_qingjia = (RelativeLayout) findViewById(R.id.rl_add_lingdao_qingjia);
        RelativeLayout rl_add_yingong_chuchai = (RelativeLayout) findViewById(R.id.rl_add_yingong_chuchai);
        RelativeLayout rl_add_lingdao_chuchai = (RelativeLayout) findViewById(R.id.rl_add_zichan_lingdao_chuchai);
        RelativeLayout rl_add_yingong_churujing = (RelativeLayout) findViewById(R.id.rl_add_yingong_churujing);
        RelativeLayout rl_add_yinsi_churujing = (RelativeLayout) findViewById(R.id.rl_add_yinsi_churujing);
        RelativeLayout rl_add_it_shenqing = (RelativeLayout) findViewById(R.id.rl_add_it_shenqing);
        RelativeLayout rl_add_shouquan_weituo = (RelativeLayout) findViewById(R.id.rl_add_shouquan_weituo);
        RelativeLayout rl_add_dangjian_gongzuo = (RelativeLayout) findViewById(R.id.rl_add_dangjian_gongzuo);
        RelativeLayout rl_add_jijian_jiancha = (RelativeLayout) findViewById(R.id.rl_add_jijian_jiancha);
        RelativeLayout rl_add_gonghui_tuanwei = (RelativeLayout) findViewById(R.id.rl_add_gonghui_tuanwei);
        RelativeLayout rl_add_tuanwei_dongtai = (RelativeLayout) findViewById(R.id.rl_add_tuanwei_dongtai);
        RelativeLayout rl_add_zican_dongtai = (RelativeLayout) findViewById(R.id.rl_add_zichan_dongtai);
        RelativeLayout rl_add_fengognsi_dogntai = (RelativeLayout) findViewById(R.id.rl_add_fengongsi_dongtai);
        RelativeLayout rl_add_zigognsi_dongtai = (RelativeLayout) findViewById(R.id.rl_add_zigongsi_dongtai);
        RelativeLayout rl_add_renshi_xinxi = (RelativeLayout) findViewById(R.id.rl_add_renshi_xinxi);
        RelativeLayout rl_add_tongzhi_gonggao = (RelativeLayout) findViewById(R.id.rl_add_tongzhi_gonggao);
        RelativeLayout rl_add_lingdao_dongtai = (RelativeLayout) findViewById(R.id.rl_add_lingdao_dongtai);
        ImageView iv_back=(ImageView) findViewById(R.id.iv_back);

        rl_add_qiang_coffee.setVisibility(View.GONE);
        rl_add_qiang_xinyu.setVisibility(View.GONE);
        rl_add_dangwei_jiyao.setVisibility(View.GONE);
        rl_add_bangong_jiyao.setVisibility(View.GONE);
        rl_add_zhuanti_jiyao.setVisibility(View.GONE);
        rl_add_gongzuo_jianbao.setVisibility(View.GONE);
        rl_add_daiban.setVisibility(View.GONE);
        rl_add_yiban.setVisibility(View.GONE);
        rl_add_daiyue.setVisibility(View.GONE);
        rl_add_yiyue.setVisibility(View.GONE);
        rl_add_qingjia_shenqing.setVisibility(View.GONE);
        rl_add_lingdao_qingjia.setVisibility(View.GONE);
        rl_add_yingong_chuchai.setVisibility(View.GONE);
        rl_add_lingdao_chuchai.setVisibility(View.GONE);
        rl_add_yingong_churujing.setVisibility(View.GONE);
        rl_add_yinsi_churujing.setVisibility(View.GONE);
        rl_add_it_shenqing.setVisibility(View.GONE);
        rl_add_shouquan_weituo.setVisibility(View.GONE);
        rl_add_dangjian_gongzuo.setVisibility(View.GONE);
        rl_add_jijian_jiancha.setVisibility(View.GONE);
        rl_add_gonghui_tuanwei.setVisibility(View.GONE);
        rl_add_tuanwei_dongtai.setVisibility(View.GONE);
        rl_add_zican_dongtai.setVisibility(View.GONE);
        rl_add_fengognsi_dogntai.setVisibility(View.GONE);
        rl_add_zigognsi_dongtai.setVisibility(View.GONE);
        rl_add_renshi_xinxi.setVisibility(View.GONE);
        rl_add_tongzhi_gonggao.setVisibility(View.GONE);
        rl_add_lingdao_dongtai.setVisibility(View.GONE);

        rl_qiang_xinyu.setOnClickListener(this);
        rl_qiang_coffee.setOnClickListener(this);
        rl_dangwei_jiyao.setOnClickListener(this);
        rl_bangong_jiyao.setOnClickListener(this);
        rl_zhuanti_jiyao.setOnClickListener(this);
        rl_gongzuo_jianbao.setOnClickListener(this);
        rl_daiban.setOnClickListener(this);
        rl_yiban.setOnClickListener(this);
        rl_daiyue.setOnClickListener(this);
        rl_yiyue.setOnClickListener(this);
        rl_qingjia_shenqing.setOnClickListener(this);
        rl_lingdao_qingjia.setOnClickListener(this);
        rl_yingong_chuchai.setOnClickListener(this);
        rl_lingdao_chuchai.setOnClickListener(this);
        rl_yingong_churujing.setOnClickListener(this);
        rl_yinsi_churujing.setOnClickListener(this);
        rl_it_shenqing.setOnClickListener(this);
        rl_shouquan_weituo.setOnClickListener(this);
        rl_dangjian_gongzuo.setOnClickListener(this);
        rl_jijian_jiancha.setOnClickListener(this);
        rl_gonghui_tuanwei.setOnClickListener(this);
        rl_tuanwei_dongtai.setOnClickListener(this);
        rl_zichan_dongtai.setOnClickListener(this);
        rl_fengongsi_dongtai.setOnClickListener(this);
        rl_zigongsi_dongtai.setOnClickListener(this);
        rl_renshi_xinxi.setOnClickListener(this);
        rl_tongzhi_gonggao.setOnClickListener(this);
        rl_lingdao_dongtai.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:   //返回
                finish();
                break;
            case R.id.rl_qiang_xinyu:   //强哥心语
                goToActivity(Constant.QIANG);
                break;
            case R.id.rl_qiang_coffee:   //强哥 coffee time
                goToActivity(Constant.COFFE);
                break;
            case R.id.rl_dangwei_jiyao:   //党委会纪要
                goToActivity(Constant.DANGWEI_JIYAO);
                break;
            case R.id.rl_bangong_jiyao:   //办公会纪要
                goToActivity(Constant.BANGONG_JIYAO);
                break;
            case R.id.rl_zhuanti_jiyao:   //专题会纪要
                goToActivity(Constant.ZHUANTI_JIYAO);
                break;
            case R.id.rl_gongzuo_jianbao:   //工作简报
                goToActivity(Constant.GONGZUO_JIANBAO);
                break;
            case R.id.rl_daiban:   //我的代办
                goToActivity(Constant.DAIBAN);
                break;
            case R.id.rl_yiban:   //我的已办
                goToActivity(Constant.YIBAN);
                break;
            case R.id.rl_daiyue:   //我的待阅
                goToActivity(Constant.DAIYUE);
                break;
            case R.id.rl_yiyue:   //我的已阅
                goToActivity(Constant.YIYUE);
                break;
            case R.id.rl_qingjia_shenqing:   //请假申请
                goToActivity(Constant.QINGJIA_SHENQING);
                break;
            case R.id.rl_lingdao_qingjia:   //领导请假
                goToActivity(Constant.LINGDAO_QINGJIA);
                break;
            case R.id.rl_yingong_chuchai:   //因公出差
                goToActivity(Constant.YINGONG_CHUCHAI);
                break;
            case R.id.rl_zichan_lingdao_chuchai:   //资产领导出差
                goToActivity(Constant.ZICHAN_LINGDAO_CHUCHAI);
                break;
            case R.id.rl_yingong_churujing:   //因公出入境
                goToActivity(Constant.YINGONG_CHURUJING);
                break;
            case R.id.rl_yinsi_churujing:   //因私出入境
                goToActivity(Constant.YINSI_CHURUJING);
                break;
            case R.id.rl_it_shenqing:   //IT申请
                goToActivity(Constant.IT_SHENQING);
                break;
            case R.id.rl_shouquan_weituo:   //授权委托
                goToActivity(Constant.WEITUO_SHOUQUAN);
                break;
            case R.id.rl_dangjian_gongzuo:   // 党建工作
                goToActivity(Constant.DANGJIAN_GONGZUO);
                break;
            case R.id.rl_jijian_jiancha:   //纪检监察
                goToActivity(Constant.JIJIAN_JIANCHA);
                break;
            case R.id.rl_gonghui_tuanwei:   // 工会团委
                goToActivity(Constant.GONGHUI_TUANWEI);
                break;
            case R.id.rl_tuanwei_dongtai:   // 团委动态
                goToActivity(Constant.TUANWEI_DONGTAI);
                break;
            case R.id.rl_zichan_dongtai:   // 资产公司动态
                goToActivity(Constant.ZICHAN_GONGSI_DONGTAI);
                break;
            case R.id.rl_fengongsi_dongtai:   // 分公司动态
                goToActivity(Constant.FENGOGNSI_DONGTAI);
                break;
            case R.id.rl_zigongsi_dongtai:   // 子公司动态
                goToActivity(Constant.ZIGOGNSI_DONGTAI);
                break;
            case R.id.rl_renshi_xinxi:   // 人事信息
                goToActivity(Constant.RENSHI_XINXI);
                break;
            case R.id.rl_tongzhi_gonggao:   // 通知公告
                goToActivity(Constant.INFO);
                break;
            case R.id.rl_lingdao_dongtai:   // 领导动态
                goToActivity(Constant.BOSS);
                break;
        }
    }

    private void goToActivity(long id) {
        if (jsonList == null || jsonList.size() <= 0) {
            ToastUtil.showToast("获取信息失败，请返回重试");
            return;
        }
        for (HomeMo item : jsonList) {
            //寻找对应的项
            if (item.getId() == id) {
                if (item.getToWhere() == 0) {
                    //去list页面
                    Intent intent = new Intent(this, SecondLevelActivity.class);
                    intent.putExtra("item", item);
                    startActivityForResult(intent, 0);
                } else {
                    //去h5页面
                    Intent intent = new Intent(this, WebviewActivity.class);
                    intent.putExtra("mName", item.getItemName());
                    intent.putExtra("mUrlOrHref", item.getHref());
                    startActivity(intent);
                }
                break;
            }
        }
    }

}
