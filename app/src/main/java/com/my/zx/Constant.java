package com.my.zx;


public class Constant {

    public static final int PHONE = 11000;        //通讯录
    public static final int DAIBAN = 12000;        //待办
    public static final int YIBAN = 12100;        //已办
    public static final int DAIYUE = 13000;        //待阅
    public static final int YIYUE = 13100;        //已阅
    public static final int INFO = 14000;            //通知公告
    public static final int ZICHAN = 15000;        //资产动态
    public static final int BOSS = 16000;            //领导动态
    public static final int CHANGE = 17000;        //修改密码
    public static final int ZGS = 18000;            //子公司动态(原工资查询)
    public static final int EMAIL = 19000;        //邮件
    public static final int FGS = 20000;            //分公司动态
    public static final int QIANG = 21000;        //强哥心语
    public static final int COFFE = 22000;        //Coffe Time
    public static final int ZHINENG = 23000;        //职能部门动态
    public static final int TUPIAN_NEWS = 24000;        //图片新闻
    public static final int DANGWEI_JIYAO = 25100;        //党委会纪要
    public static final int BANGONG_JIYAO = 25000;        //办公会纪要
    public static final int ZHUANTI_JIYAO = 26000;        //专题会纪要
    public static final int GONGZUO_JIANBAO = 27000;        //工作简报
    public static final int DANGJIAN_GONGZUO = 28000;        //党建工作
    public static final int JIJIAN_JIANCHA = 29000;        //纪检监察
    public static final int GONGHUI_TUANWEI = 30000;        //工会团委
    public static final int TUANWEI_DONGTAI = 31000;        //团委动态
    public static final int RENSHI_XINXI = 32000;        //人事信息
    public static final int QINGJIA_SHENQING = 33000;        //请假申请
    public static final int LINGDAO_QINGJIA = 34000;        //领导请假
    public static final int YINGONG_CHUCHAI = 35000;        //因公出差
    public static final int ZICHAN_LINGDAO_CHUCHAI = 36000;        //资产领导出差
    public static final int YINGONG_CHURUJING = 37000;        //因公出入境
    public static final int YINSI_CHURUJING = 38000;        //因私出入境
    public static final int IT_SHENQING = 39000;        //IT申请
    public static final int WEITUO_SHOUQUAN = 40000;        //授权委托

    public static final int DB_VERSION = 6;    //支持商圈搜索
    public static final boolean DB_DEBUG = true;

    public static final long SESSION_TIME_DIFF = 30 * 60 * 1000;    //session过期时间设置

    public static final String NO_SERVER_DATA = "没获取到数据，请退出重试";    //没获取到数据的提示
    public static final String NO_LOGIN = "请先登录再访问";    //未登录的提示
    public static final String NO_SAVE_EDIT = "编辑未完成，请先保存您的编辑";    //编辑未完成时，即去登录提示
    public static final String NO_LOGIN_OR_SESSION = "未登陆或session已过期，请先登录";    //未登录或session过期的提示
    public static final String NO_SESSION = "session过期，请重新登录";    //session过期，请重新登录的提示

    public static final String BROADCAST_LOGIN_IN = "com.my.zx.login_in";    //登录广播
    public static final String BROADCAST_LOGIN_OUT = "com.my.zx.login_out";    //退出登陆广播

}
