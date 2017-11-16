package com.my.zx.utils;

import com.my.zx.Constant;
import com.my.zx.model.HomeMo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhailing on 2017/9/26.
 * 类简单说明:
 */

public class DbUtil {

    //第一次安装 默认的首页九宫格的项
    public static List<HomeMo> initializeHomes() {
        List<HomeMo> mList = new ArrayList<>();

        // long id, String itemName, boolean hasNum, int taskNum, int toWhere, String href
//        mList.add(new HomeMo(21000, "强哥心语", false, 0, 0, "/produce/mobilemng.nsf/agtGetViewData?open&&odbpath=application/QGXYPublish.nsf&oviewname=vwinfoforshow&page=1&rows=15&oviewcategory=qgxy&type=info"));
//        mList.add(new HomeMo(22000, "强哥Coffee Time", false, 0, 0, "/produce/mobilemng.nsf/agtGetViewData?open&&odbpath=application/QGXYCOFFEETIME.nsf&oviewname=vwinfoforshow&page=1&rows=15&oviewcategory=qgxy&type=info"));
//        mList.add(new HomeMo(14000, "通知公告", false, 0, 0, "/produce/mobilemng.nsf/agtGetViewData?open&&odbpath=application/infopublish.nsf&oviewname=vwinfoforshow&page=1&rows=15&oviewcategory=tzgg&type=info"));
//        mList.add(new HomeMo(12000, "待办任务", true, 9, 0, "/produce/mobilemng.nsf/agtGetViewData?open&&odbpath=todo/amcadmin.nsf&oviewname=vwtododoc&page=1&rows=15&oviewcategory=&type=todo"));
//        mList.add(new HomeMo(12100, "已办任务", false, 0, 0, "/produce/mobilemng.nsf/agtGetViewData?open&&odbpath=todo/amcadmin.nsf&oviewname=vwTodoDocDone&page=1&rows=15&oviewcategory=&type=todo"));
//        mList.add(new HomeMo(13000, "未读消息", true, 909, 0, "/produce/mobilemng.nsf/agtGetViewData?open&&odbpath=todo/amcadmin.nsf&oviewname=vwunreaddoc&page=1&rows=15&oviewcategory=&type=todo"));
//        mList.add(new HomeMo(131000, "已读消息", false, 0, 0, "/produce/mobilemng.nsf/agtGetViewData?open&&odbpath=todo/amcadmin.nsf&oviewname=vwReadDoc&page=1&rows=15&oviewcategory=&type=todo"));
//        mList.add(new HomeMo(33000, "请假申请", false, 0, 1, "/application/appVacate.nsf/MainFlowForm?openForm"));
//        mList.add(new HomeMo(35000, "因公出差", false, 0, 1, "/application/ygcc.nsf/MainFlowForm?openForm"));
//        mList.add(new HomeMo(36000, "资产领导出差", false, 0, 1, "/application/BossTrip.nsf/MainFlowForm?openForm"));
//        mList.add(new HomeMo(37000, "因公出入境", false, 0, 1, "/application/CRJSP.nsf/MainFlowForm?openForm"));
//        mList.add(new HomeMo(38000, "因私出入境", false, 0, 1, "/application/YSCRJSP.nsf/MainFlowForm?openForm"));
//        mList.add(new HomeMo(39000, "IT申请", false, 0, 1, "/application/ITdemand.nsf/MainFlowForm?openForm"));
//        mList.add(new HomeMo(40000, "授权委托", false, 0, 1, "/produce/DelegateMng.nsf/frmDelegate?openForm"));
//        mList.add(new HomeMo(16000, "公司领导动态", false, 0, 0, "/produce/mobilemng.nsf/agtGetViewData?open&&odbpath=application/infopublish.nsf&oviewname=vwinfoforshow&page=1&rows=15&oviewcategory=gslddt&type=info"));
//        mList.add(new HomeMo(15000, "资产公司动态", false, 0, 0, "/produce/mobilemng.nsf/agtGetViewData?open&&odbpath=application/infopublish.nsf&oviewname=vwinfoforshow&page=1&rows=15&oviewcategory=zcgsdt&type=info"));
//        mList.add(new HomeMo(20000, "分公司动态", false, 0, 0, "/produce/mobilemng.nsf/agtGetViewData?open&&odbpath=application/infopublish.nsf&oviewname=vwinfoforshow&page=1&rows=15&oviewcategory=fgsdt&type=info"));
        mList.add(new HomeMo(21000, "强哥心语"));
        mList.add(new HomeMo(22000, "强哥 coffee time"));
        mList.add(new HomeMo(28000, "党建工作"));
        mList.add(new HomeMo(16000, "领导动态"));
//        mList.add(new HomeMo(14000, "通知公告"));
        mList.add(new HomeMo(12000, "我的待办"));
        mList.add(new HomeMo(12100, "我的已办"));
        mList.add(new HomeMo(13000, "我的待阅"));
        mList.add(new HomeMo(13100, "我的已阅"));
        mList.add(new HomeMo(40000, "授权委托"));
        mList.add(new HomeMo(33000, "请假申请"));
        mList.add(new HomeMo(36000, "资产领导出差"));
        mList.add(new HomeMo(35000, "因公出差"));
        mList.add(new HomeMo(37000, "因公出入境"));
        mList.add(new HomeMo(38000, "因私出入境"));
        mList.add(new HomeMo(39000, "IT申请"));
//        mList.add(new HomeMo(15000, "资产公司动态"));
//        mList.add(new HomeMo(20000, "分公司动态"));
        return mList;

    }

    //默认的添加界面的项
    public static List<HomeMo> initializeAddActivityItems() {
        List<HomeMo> mList = new ArrayList<>();

        mList.add(new HomeMo(21000, "强哥心语"));
        mList.add(new HomeMo(22000, "强哥 coffee time"));

        mList.add(new HomeMo(Constant.DANGWEI_JIYAO, "党委会纪要"));
        mList.add(new HomeMo(Constant.BANGONG_JIYAO, "办公会纪要"));
        mList.add(new HomeMo(Constant.ZHUANTI_JIYAO, "专题会纪要"));
        mList.add(new HomeMo(Constant.GONGZUO_JIANBAO, "工作简报"));

        mList.add(new HomeMo(12000, "我的待办"));
        mList.add(new HomeMo(12100, "我的已办"));
        mList.add(new HomeMo(13000, "我的待阅"));
        mList.add(new HomeMo(13100, "我的已阅"));

        mList.add(new HomeMo(33000, "请假申请"));
        mList.add(new HomeMo(Constant.LINGDAO_QINGJIA, "领导请假"));
        mList.add(new HomeMo(35000, "因公出差"));
        mList.add(new HomeMo(36000, "资产领导出差"));
        mList.add(new HomeMo(37000, "因公出入境"));
        mList.add(new HomeMo(38000, "因私出入境"));
        mList.add(new HomeMo(39000, "IT申请"));
        mList.add(new HomeMo(40000, "授权委托"));

        mList.add(new HomeMo(28000, "党建工作"));
        mList.add(new HomeMo(Constant.JIJIAN_JIANCHA, "纪检监察"));
        mList.add(new HomeMo(Constant.GONGHUI_TUANWEI, "工会团委"));
        mList.add(new HomeMo(Constant.TUANWEI_DONGTAI, "团委动态"));
        mList.add(new HomeMo(Constant.ZICHAN_GONGSI_DONGTAI, "资产公司动态"));
        mList.add(new HomeMo(Constant.FENGOGNSI_DONGTAI, "分公司动态"));
        mList.add(new HomeMo(Constant.ZIGOGNSI_DONGTAI, "子公司动态"));
        mList.add(new HomeMo(Constant.RENSHI_XINXI, "人事信息"));
        mList.add(new HomeMo(14000, "通知公告"));
        mList.add(new HomeMo(16000, "领导动态"));

        return mList;
    }

}
