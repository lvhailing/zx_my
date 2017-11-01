package com.my.zx.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.zx.Constant;
import com.my.zx.R;
import com.my.zx.db.DBManager;
import com.my.zx.model.HomeMo;
import com.my.zx.utils.ToastUtil;

import java.util.List;

/**
 * 编辑页面 只做加减
 */

public class AddActivity extends BaseActivity implements View.OnClickListener {

    private List<HomeMo> dbList;    //数据库集合
    private ImageView iv_back;
    private ImageView iv_add_qiang_xinyu;
    private ImageView iv_add_qiang_coffee;
    private ImageView iv_add_dangwei_jiyao;
    private ImageView iv_add_bangong_jiyao;
    private ImageView iv_add_zhuanti_jiyao;
    private ImageView iv_add_daiban;
    private ImageView iv_add_yiban;
    private ImageView iv_add_daiyue;
    private ImageView iv_add_gongzuo_jianbao;
    private ImageView iv_add_yiyue;
    private ImageView iv_add_lingdao_chuchai;
    private ImageView iv_add_yingong_chuchai;
    private ImageView iv_add_qingjia_shenqing;
    private ImageView iv_add_it_shenqing;
    private ImageView iv_add_shouquan_weituo;
    private ImageView iv_add_fengongsi_dongtai;
    private ImageView iv_add_jijian_jiancha;
    private ImageView iv_add_tuanwei_dongtai;
    private ImageView iv_add_yingong_churujing;
    private ImageView iv_add_gonghui_tuanwei;
    private ImageView iv_add_dangjian_gongzuo;
    private ImageView iv_add_zichan_dongtai;
    private ImageView iv_add_lingdao_qingjia;
    private ImageView iv_add_yinsi_churujing;
    private ImageView iv_add_lingdao_dongtai;
    private ImageView iv_add_tongzhi_gonggao;
    private ImageView iv_add_renshi_xinxi;
    private ImageView iv_add_zigongsi_dongtai;
    private TextView tv_done;

    private RelativeLayout rl_add_qiang_xinyu;
    private RelativeLayout rl_add_qiang_coffee;
    private RelativeLayout rl_add_dangwei_jiyao;
    private RelativeLayout rl_add_bangong_jiyao;
    private RelativeLayout rl_add_zhuanti_jiyao;
    private RelativeLayout rl_add_daiban;
    private RelativeLayout rl_add_yiban;
    private RelativeLayout rl_add_daiyue;
    private RelativeLayout rl_add_gongzuo_jianbao;
    private RelativeLayout rl_add_yiyue;
    private RelativeLayout rl_add_lingdao_chuchai;
    private RelativeLayout rl_add_yingong_chuchai;
    private RelativeLayout rl_add_qingjia_shenqing;
    private RelativeLayout rl_add_it_shenqing;
    private RelativeLayout rl_add_shouquan_weituo;
    private RelativeLayout rl_add_fengongsi_dongtai;
    private RelativeLayout rl_add_jijian_jiancha;
    private RelativeLayout rl_add_tuanwei_dongtai;
    private RelativeLayout rl_add_yingong_churujing;
    private RelativeLayout rl_add_gonghui_tuanwei;
    private RelativeLayout rl_add_dangjian_gongzuo;
    private RelativeLayout rl_add_zichan_dongtai;
    private RelativeLayout rl_add_lingdao_qingjia;
    private RelativeLayout rl_add_yinsi_churujing;
    private RelativeLayout rl_add_lingdao_dongtai;
    private RelativeLayout rl_add_tongzhi_gonggao;
    private RelativeLayout rl_add_renshi_xinxi;
    private RelativeLayout rl_add_zigongsi_dongtai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();
        initDatas();
    }

    private void initDatas() {
        dbList = (List<HomeMo>) getIntent().getSerializableExtra("dbList");

        tv_done.setVisibility(View.VISIBLE);

        //初始化所以加减号
        iv_add_qiang_xinyu.setBackgroundResource(!doDbHasMe(Constant.QIANG) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_qiang_coffee.setBackgroundResource(!doDbHasMe(Constant.COFFE) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_dangwei_jiyao.setBackgroundResource(!doDbHasMe(Constant.DANGWEI_JIYAO) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_bangong_jiyao.setBackgroundResource(!doDbHasMe(Constant.BANGONG_JIYAO) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_zhuanti_jiyao.setBackgroundResource(!doDbHasMe(Constant.ZHUANTI_JIYAO) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_gongzuo_jianbao.setBackgroundResource(!doDbHasMe(Constant.GONGZUO_JIANBAO) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_daiban.setBackgroundResource(!doDbHasMe(Constant.DAIBAN) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_yiban.setBackgroundResource(!doDbHasMe(Constant.YIBAN) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_daiyue.setBackgroundResource(!doDbHasMe(Constant.DAIYUE) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_yiyue.setBackgroundResource(!doDbHasMe(Constant.YIYUE) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_qingjia_shenqing.setBackgroundResource(!doDbHasMe(Constant.QINGJIA_SHENQING) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_lingdao_qingjia.setBackgroundResource(!doDbHasMe(Constant.LINGDAO_QINGJIA) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_yingong_chuchai.setBackgroundResource(!doDbHasMe(Constant.YINGONG_CHUCHAI) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_lingdao_chuchai.setBackgroundResource(!doDbHasMe(Constant.ZICHAN_LINGDAO_CHUCHAI) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_yingong_churujing.setBackgroundResource(!doDbHasMe(Constant.YINGONG_CHURUJING) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_yinsi_churujing.setBackgroundResource(!doDbHasMe(Constant.YINSI_CHURUJING) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_it_shenqing.setBackgroundResource(!doDbHasMe(Constant.IT_SHENQING) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_shouquan_weituo.setBackgroundResource(!doDbHasMe(Constant.WEITUO_SHOUQUAN) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_dangjian_gongzuo.setBackgroundResource(!doDbHasMe(Constant.DANGJIAN_GONGZUO) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_jijian_jiancha.setBackgroundResource(!doDbHasMe(Constant.JIJIAN_JIANCHA) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_gonghui_tuanwei.setBackgroundResource(!doDbHasMe(Constant.GONGHUI_TUANWEI) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_tuanwei_dongtai.setBackgroundResource(!doDbHasMe(Constant.TUANWEI_DONGTAI) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_zichan_dongtai.setBackgroundResource(!doDbHasMe(Constant.ZICHAN_GONGSI_DONGTAI) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_fengongsi_dongtai.setBackgroundResource(!doDbHasMe(Constant.FENGOGNSI_DONGTAI) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_zigongsi_dongtai.setBackgroundResource(!doDbHasMe(Constant.ZIGOGNSI_DONGTAI) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_renshi_xinxi.setBackgroundResource(!doDbHasMe(Constant.RENSHI_XINXI) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_tongzhi_gonggao.setBackgroundResource(!doDbHasMe(Constant.INFO) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
        iv_add_lingdao_dongtai.setBackgroundResource(!doDbHasMe(Constant.BOSS) ? R.drawable.home_jiahao : R.drawable.home_jianhao);
    }

    private void initView() {
        iv_add_shouquan_weituo = (ImageView) findViewById(R.id.iv_add_shouquan_weituo);
        iv_add_dangjian_gongzuo = (ImageView) findViewById(R.id.iv_add_dangjian_gongzuo);
        iv_add_jijian_jiancha = (ImageView) findViewById(R.id.iv_add_jijian_jiancha);
        iv_add_gonghui_tuanwei = (ImageView) findViewById(R.id.iv_add_gonghui_tuanwei);
        iv_add_tuanwei_dongtai = (ImageView) findViewById(R.id.iv_add_tuanwei_dongtai);
        iv_add_zichan_dongtai = (ImageView) findViewById(R.id.iv_add_zichan_dongtai);
        iv_add_fengongsi_dongtai = (ImageView) findViewById(R.id.iv_add_fengongsi_dongtai);
        iv_add_zigongsi_dongtai = (ImageView) findViewById(R.id.iv_add_zigongsi_dongtai);
        iv_add_renshi_xinxi = (ImageView) findViewById(R.id.iv_add_renshi_xinxi);
        iv_add_tongzhi_gonggao = (ImageView) findViewById(R.id.iv_add_tongzhi_gonggao);
        iv_add_lingdao_dongtai = (ImageView) findViewById(R.id.iv_add_lingdao_dongtai);
        iv_add_qiang_xinyu = (ImageView) findViewById(R.id.iv_add_qiang_xinyu);
        iv_add_qiang_coffee = (ImageView) findViewById(R.id.iv_add_qiang_coffee);
        iv_add_dangwei_jiyao = (ImageView) findViewById(R.id.iv_add_dangwei_jiyao);
        iv_add_bangong_jiyao = (ImageView) findViewById(R.id.iv_add_bangong_jiyao);
        iv_add_zhuanti_jiyao = (ImageView) findViewById(R.id.iv_add_zhuanti_jiyao);
        iv_add_gongzuo_jianbao = (ImageView) findViewById(R.id.iv_add_gongzuo_jianbao);
        iv_add_daiban = (ImageView) findViewById(R.id.iv_add_daiban);
        iv_add_yiban = (ImageView) findViewById(R.id.iv_add_yiban);
        iv_add_daiyue = (ImageView) findViewById(R.id.iv_add_daiyue);
        iv_add_yiyue = (ImageView) findViewById(R.id.iv_add_yiyue);
        iv_add_qingjia_shenqing = (ImageView) findViewById(R.id.iv_add_qingjia_shenqing);
        iv_add_lingdao_qingjia = (ImageView) findViewById(R.id.iv_add_lingdao_qingjia);
        iv_add_yingong_chuchai = (ImageView) findViewById(R.id.iv_add_yingong_chuchai);
        iv_add_lingdao_chuchai = (ImageView) findViewById(R.id.iv_add_lingdao_chuchai);
        iv_add_yingong_churujing = (ImageView) findViewById(R.id.iv_add_yingong_churujing);
        iv_add_yinsi_churujing = (ImageView) findViewById(R.id.iv_add_yinsi_churujing);
        iv_add_it_shenqing = (ImageView) findViewById(R.id.iv_add_it_shenqing);

        rl_add_shouquan_weituo = (RelativeLayout) findViewById(R.id.rl_add_shouquan_weituo);
        rl_add_dangjian_gongzuo = (RelativeLayout) findViewById(R.id.rl_add_dangjian_gongzuo);
        rl_add_jijian_jiancha = (RelativeLayout) findViewById(R.id.rl_add_jijian_jiancha);
        rl_add_gonghui_tuanwei = (RelativeLayout) findViewById(R.id.rl_add_gonghui_tuanwei);
        rl_add_tuanwei_dongtai = (RelativeLayout) findViewById(R.id.rl_add_tuanwei_dongtai);
        rl_add_zichan_dongtai = (RelativeLayout) findViewById(R.id.rl_add_zichan_dongtai);
        rl_add_fengongsi_dongtai = (RelativeLayout) findViewById(R.id.rl_add_fengongsi_dongtai);
        rl_add_zigongsi_dongtai = (RelativeLayout) findViewById(R.id.rl_add_zigongsi_dongtai);
        rl_add_renshi_xinxi = (RelativeLayout) findViewById(R.id.rl_add_renshi_xinxi);
        rl_add_tongzhi_gonggao = (RelativeLayout) findViewById(R.id.rl_add_tongzhi_gonggao);
        rl_add_lingdao_dongtai = (RelativeLayout) findViewById(R.id.rl_add_lingdao_dongtai);
        rl_add_qiang_xinyu = (RelativeLayout) findViewById(R.id.rl_add_qiang_xinyu);
        rl_add_qiang_coffee = (RelativeLayout) findViewById(R.id.rl_add_qiang_coffee);
        rl_add_dangwei_jiyao = (RelativeLayout) findViewById(R.id.rl_add_dangwei_jiyao);
        rl_add_bangong_jiyao = (RelativeLayout) findViewById(R.id.rl_add_bangong_jiyao);
        rl_add_zhuanti_jiyao = (RelativeLayout) findViewById(R.id.rl_add_zhuanti_jiyao);
        rl_add_gongzuo_jianbao = (RelativeLayout) findViewById(R.id.rl_add_gongzuo_jianbao);
        rl_add_daiban = (RelativeLayout) findViewById(R.id.rl_add_daiban);
        rl_add_yiban = (RelativeLayout) findViewById(R.id.rl_add_yiban);
        rl_add_daiyue = (RelativeLayout) findViewById(R.id.rl_add_daiyue);
        rl_add_yiyue = (RelativeLayout) findViewById(R.id.rl_add_yiyue);
        rl_add_qingjia_shenqing = (RelativeLayout) findViewById(R.id.rl_add_qingjia_shenqing);
        rl_add_lingdao_qingjia = (RelativeLayout) findViewById(R.id.rl_add_lingdao_qingjia);
        rl_add_yingong_chuchai = (RelativeLayout) findViewById(R.id.rl_add_yingong_chuchai);
        rl_add_lingdao_chuchai = (RelativeLayout) findViewById(R.id.rl_add_zichan_lingdao_chuchai);
        rl_add_yingong_churujing = (RelativeLayout) findViewById(R.id.rl_add_yingong_churujing);
        rl_add_yinsi_churujing = (RelativeLayout) findViewById(R.id.rl_add_yinsi_churujing);
        rl_add_it_shenqing = (RelativeLayout) findViewById(R.id.rl_add_it_shenqing);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_done = (TextView) findViewById(R.id.tv_done);

        iv_back.setOnClickListener(this);
        tv_done.setOnClickListener(this);

        rl_add_qiang_xinyu.setOnClickListener(this);
        rl_add_qiang_coffee.setOnClickListener(this);
        rl_add_dangwei_jiyao.setOnClickListener(this);
        rl_add_bangong_jiyao.setOnClickListener(this);
        rl_add_zhuanti_jiyao.setOnClickListener(this);
        rl_add_gongzuo_jianbao.setOnClickListener(this);
        rl_add_daiban.setOnClickListener(this);
        rl_add_yiban.setOnClickListener(this);
        rl_add_daiyue.setOnClickListener(this);
        rl_add_yiyue.setOnClickListener(this);
        rl_add_qingjia_shenqing.setOnClickListener(this);
        rl_add_lingdao_qingjia.setOnClickListener(this);
        rl_add_yingong_chuchai.setOnClickListener(this);
        rl_add_lingdao_chuchai.setOnClickListener(this);
        rl_add_yingong_churujing.setOnClickListener(this);
        rl_add_yinsi_churujing.setOnClickListener(this);
        rl_add_it_shenqing.setOnClickListener(this);
        rl_add_shouquan_weituo.setOnClickListener(this);
        rl_add_dangjian_gongzuo.setOnClickListener(this);
        rl_add_jijian_jiancha.setOnClickListener(this);
        rl_add_gonghui_tuanwei.setOnClickListener(this);
        rl_add_tuanwei_dongtai.setOnClickListener(this);
        rl_add_zichan_dongtai.setOnClickListener(this);
        rl_add_fengongsi_dongtai.setOnClickListener(this);
        rl_add_zigongsi_dongtai.setOnClickListener(this);
        rl_add_renshi_xinxi.setOnClickListener(this);
        rl_add_tongzhi_gonggao.setOnClickListener(this);
        rl_add_lingdao_dongtai.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:   //返回
                finish();
                break;
            case R.id.tv_done:   //完成
                showWaitProgress();
                DBManager.saveHomes(this, dbList);
                ToastUtil.showToast("保存完成");
                closeWaitProgress();
                break;
            case R.id.rl_add_qiang_xinyu:   //强哥心语
                if (!doDbHasMe(Constant.QIANG)) {
                    iv_add_qiang_xinyu.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.QIANG, "强哥心语"));
                } else {
                    iv_add_qiang_xinyu.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.QIANG);
                }
                break;
            case R.id.rl_add_qiang_coffee:   //强哥coffee time
                if (!doDbHasMe(Constant.COFFE)) {
                    iv_add_qiang_coffee.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.COFFE, "强哥coffee time"));
                } else {
                    iv_add_qiang_coffee.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.COFFE);
                }
                break;
            case R.id.rl_add_dangwei_jiyao:   //党委会纪要
                if (!doDbHasMe(Constant.DANGWEI_JIYAO)) {
                    iv_add_dangwei_jiyao.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.DANGWEI_JIYAO, "党委会纪要"));
                } else {
                    iv_add_dangwei_jiyao.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.DANGWEI_JIYAO);
                }
                break;
            case R.id.rl_add_bangong_jiyao:   //办公会纪要
                if (!doDbHasMe(Constant.BANGONG_JIYAO)) {
                    iv_add_bangong_jiyao.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.BANGONG_JIYAO, "办公会纪要"));
                } else {
                    iv_add_bangong_jiyao.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.BANGONG_JIYAO);
                }
                break;
            case R.id.rl_add_zhuanti_jiyao:   //专题会纪要
                if (!doDbHasMe(Constant.ZHUANTI_JIYAO)) {
                    iv_add_zhuanti_jiyao.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.ZHUANTI_JIYAO, "专题会纪要"));
                } else {
                    iv_add_zhuanti_jiyao.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.ZHUANTI_JIYAO);
                }
                break;
            case R.id.rl_add_gongzuo_jianbao:   //工作简报
                if (!doDbHasMe(Constant.GONGZUO_JIANBAO)) {
                    iv_add_gongzuo_jianbao.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.GONGZUO_JIANBAO, "工作简报"));
                } else {
                    iv_add_gongzuo_jianbao.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.GONGZUO_JIANBAO);
                }
                break;
            case R.id.rl_add_daiban:   //我的代办
                if (!doDbHasMe(Constant.DAIBAN)) {
                    iv_add_daiban.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.DAIBAN, "我的代办"));
                } else {
                    iv_add_daiban.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.DAIBAN);
                }
                break;
            case R.id.rl_add_yiban:   //我的已办
                if (!doDbHasMe(Constant.YIBAN)) {
                    iv_add_yiban.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.YIBAN, "我的已办"));
                } else {
                    iv_add_yiban.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.YIBAN);
                }
                break;
            case R.id.rl_add_daiyue:   //我的待阅
                if (!doDbHasMe(Constant.DAIYUE)) {
                    iv_add_daiyue.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.DAIYUE, "我的待阅"));
                } else {
                    iv_add_daiyue.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.DAIYUE);
                }
                break;
            case R.id.rl_add_yiyue:   //我的已阅
                if (!doDbHasMe(Constant.YIYUE)) {
                    iv_add_yiyue.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.YIYUE, "我的已阅"));
                } else {
                    iv_add_yiyue.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.YIYUE);
                }
                break;
            case R.id.rl_add_qingjia_shenqing:   //请假申请
                if (!doDbHasMe(Constant.QINGJIA_SHENQING)) {
                    iv_add_qingjia_shenqing.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.QINGJIA_SHENQING, "请假申请"));
                } else {
                    iv_add_qingjia_shenqing.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.QINGJIA_SHENQING);
                }
                break;
            case R.id.rl_add_lingdao_qingjia:   //领导请假
                if (!doDbHasMe(Constant.LINGDAO_QINGJIA)) {
                    iv_add_lingdao_qingjia.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.LINGDAO_QINGJIA, "领导请假"));
                } else {
                    iv_add_lingdao_qingjia.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.LINGDAO_QINGJIA);
                }
                break;
            case R.id.rl_add_yingong_chuchai:   //因公出差
                if (!doDbHasMe(Constant.YINGONG_CHUCHAI)) {
                    iv_add_yingong_chuchai.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.YINGONG_CHUCHAI, "因公出差"));
                } else {
                    iv_add_yingong_chuchai.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.YINGONG_CHUCHAI);
                }
                break;
            case R.id.rl_add_zichan_lingdao_chuchai:   //资产领导出差
                if (!doDbHasMe(Constant.ZICHAN_LINGDAO_CHUCHAI)) {
                    iv_add_lingdao_chuchai.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.ZICHAN_LINGDAO_CHUCHAI, "资产领导出差"));
                } else {
                    iv_add_lingdao_chuchai.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.ZICHAN_LINGDAO_CHUCHAI);
                }
                break;
            case R.id.rl_add_yingong_churujing:   //因公出入境
                if (!doDbHasMe(Constant.YINGONG_CHURUJING)) {
                    iv_add_yingong_churujing.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.YINGONG_CHURUJING, "因公出入境"));
                } else {
                    iv_add_yingong_churujing.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.YINGONG_CHURUJING);
                }
                break;
            case R.id.rl_add_yinsi_churujing:   //因私出入境
                if (!doDbHasMe(Constant.YINSI_CHURUJING)) {
                    iv_add_yinsi_churujing.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.YINSI_CHURUJING, "因私出入境"));
                } else {
                    iv_add_yinsi_churujing.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.YINSI_CHURUJING);
                }
                break;
            case R.id.rl_add_it_shenqing:   //IT申请
                if (!doDbHasMe(Constant.IT_SHENQING)) {
                    iv_add_it_shenqing.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.IT_SHENQING, "IT申请"));
                } else {
                    iv_add_it_shenqing.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.IT_SHENQING);
                }
                break;
            case R.id.rl_add_shouquan_weituo:   //授权委托
                if (!doDbHasMe(Constant.WEITUO_SHOUQUAN)) {
                    iv_add_shouquan_weituo.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.WEITUO_SHOUQUAN, "授权委托"));
                } else {
                    iv_add_shouquan_weituo.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.WEITUO_SHOUQUAN);
                }
                break;
            case R.id.rl_add_dangjian_gongzuo:   // 党建工作
                if (!doDbHasMe(Constant.DANGJIAN_GONGZUO)) {
                    iv_add_dangjian_gongzuo.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.DANGJIAN_GONGZUO, "党建工作"));
                } else {
                    iv_add_dangjian_gongzuo.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.DANGJIAN_GONGZUO);
                }
                break;
            case R.id.rl_add_jijian_jiancha:   //纪检监察
                if (!doDbHasMe(Constant.JIJIAN_JIANCHA)) {
                    iv_add_jijian_jiancha.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.JIJIAN_JIANCHA, "纪检监察"));
                } else {
                    iv_add_jijian_jiancha.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.JIJIAN_JIANCHA);
                }
                break;
            case R.id.rl_add_gonghui_tuanwei:   // 工会团委
                if (!doDbHasMe(Constant.GONGHUI_TUANWEI)) {
                    iv_add_gonghui_tuanwei.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.GONGHUI_TUANWEI, "工会团委"));
                } else {
                    iv_add_gonghui_tuanwei.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.GONGHUI_TUANWEI);
                }
                break;
            case R.id.rl_add_tuanwei_dongtai:   // 团委动态
                if (!doDbHasMe(Constant.TUANWEI_DONGTAI)) {
                    iv_add_tuanwei_dongtai.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.TUANWEI_DONGTAI, "团委动态"));
                } else {
                    iv_add_tuanwei_dongtai.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.TUANWEI_DONGTAI);
                }
                break;
            case R.id.rl_add_zichan_dongtai:   // 资产公司动态
                if (!doDbHasMe(Constant.ZICHAN_GONGSI_DONGTAI)) {
                    iv_add_zichan_dongtai.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.ZICHAN_GONGSI_DONGTAI, "资产公司动态"));
                } else {
                    iv_add_zichan_dongtai.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.ZICHAN_GONGSI_DONGTAI);
                }
                break;
            case R.id.rl_add_fengongsi_dongtai:   // 分公司动态
                if (!doDbHasMe(Constant.FENGOGNSI_DONGTAI)) {
                    iv_add_fengongsi_dongtai.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.FENGOGNSI_DONGTAI, "分公司动态"));
                } else {
                    iv_add_fengongsi_dongtai.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.FENGOGNSI_DONGTAI);
                }
                break;
            case R.id.rl_add_zigongsi_dongtai:   // 子公司动态
                if (!doDbHasMe(Constant.ZIGOGNSI_DONGTAI)) {
                    iv_add_zigongsi_dongtai.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.ZIGOGNSI_DONGTAI, "子公司动态"));
                } else {
                    iv_add_zigongsi_dongtai.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.ZIGOGNSI_DONGTAI);
                }
                break;
            case R.id.rl_add_renshi_xinxi:   // 人事信息
                if (!doDbHasMe(Constant.RENSHI_XINXI)) {
                    iv_add_renshi_xinxi.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.RENSHI_XINXI, "人事信息"));
                } else {
                    iv_add_renshi_xinxi.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.RENSHI_XINXI);
                }
                break;
            case R.id.rl_add_tongzhi_gonggao:   // 通知公告
                if (!doDbHasMe(Constant.INFO)) {
                    iv_add_tongzhi_gonggao.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.INFO, "通知公告"));
                } else {
                    iv_add_tongzhi_gonggao.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.INFO);
                }
                break;
            case R.id.rl_add_lingdao_dongtai:   // 领导动态
                if (!doDbHasMe(Constant.BOSS)) {
                    iv_add_lingdao_dongtai.setBackgroundResource(R.drawable.home_jianhao);
                    dbList.add(new HomeMo(Constant.BOSS, "领导动态"));
                } else {
                    iv_add_lingdao_dongtai.setBackgroundResource(R.drawable.home_jiahao);
                    delMe(Constant.BOSS);
                }
                break;
        }
    }

    //去数据库list里寻找 看当前项在不在里面
    private boolean doDbHasMe(long id) {
        boolean hasMe = false;
        for (HomeMo item : dbList) {
            if (id == item.getId()) {
                hasMe = true;
                break;
            }
        }
        return hasMe;
    }

    //去数据库list里删除这一项
    private void delMe(long id) {
        for (HomeMo item : dbList) {
            if (id == item.getId()) {
                dbList.remove(item);
                break;
            }
        }
    }
}
