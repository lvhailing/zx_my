package com.my.zx.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zx.Constant;
import com.my.zx.R;
import com.my.zx.ac.SecondLevelActivity;
import com.my.zx.ac.WebviewActivity;
import com.my.zx.model.HomeMo;
import com.my.zx.model.VersionMo;
import com.my.zx.net.DataService;
import com.my.zx.net.MesType;
import com.my.zx.net.ResultObject;
import com.my.zx.utils.DeviceUtil;
import com.my.zx.utils.PreferenceUtil;
import com.my.zx.utils.ToastUtil;
import com.my.zx.utils.UpdateUtil;
import com.my.zx.widegt.CustomerDialog;

import java.util.List;
import java.util.Map;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment implements OnClickListener {

    private ProgressDialog progress;
    private List<HomeMo> homeList;
    private RelativeLayout rl_phone, rl_change, rl_email;
    private RelativeLayout rl_dai_ban, rl_dai_yue, rl_info, rl_qiang, rl_coffe, rl_zichan, rl_boss, rl_fgs, rl_zgs;

    private TextView tv_dai_ban, tv_dai_yue, tv_info, tv_qiang, tv_coffe, tv_zichan, tv_boss, tv_zgs, tv_fgs; // 小红点
    private TextView tv_cancel; // 注销

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
                        homeList = (List<HomeMo>) ro.getObject();
                        if (homeList == null || homeList.size() <= 0) {
                            return;
                        }
                        setHomeListData();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "获取首页信息失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MesType.VERSION:
                    if (ro.getSuccess()) {
                        VersionMo v = (VersionMo) ro.getObject();
                        if (v != null) new UpdateUtil().checkVersion(getActivity(), v);
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_main, container, false);

        initView(v);
        initData();
        checkUpdate();
        return v;
    }

    private void initView(View v) {
        rl_phone = (RelativeLayout) v.findViewById(R.id.rl_phone);
        rl_change = (RelativeLayout) v.findViewById(R.id.rl_change);
        rl_email = (RelativeLayout) v.findViewById(R.id.rl_email);

        rl_dai_ban = (RelativeLayout) v.findViewById(R.id.rl_dai_ban);
        rl_dai_yue = (RelativeLayout) v.findViewById(R.id.rl_dai_yue);
        rl_info = (RelativeLayout) v.findViewById(R.id.rl_info);
        rl_qiang = (RelativeLayout) v.findViewById(R.id.rl_qiang);
        rl_coffe = (RelativeLayout) v.findViewById(R.id.rl_coffe);
        rl_zichan = (RelativeLayout) v.findViewById(R.id.rl_zichan);
        rl_boss = (RelativeLayout) v.findViewById(R.id.rl_boss);
        rl_fgs = (RelativeLayout) v.findViewById(R.id.rl_fgs);
        rl_zgs = (RelativeLayout) v.findViewById(R.id.rl_zgs);

        rl_phone.setOnClickListener(this);
        rl_change.setOnClickListener(this);
        rl_email.setOnClickListener(this);

        rl_dai_ban.setOnClickListener(this);
        rl_dai_yue.setOnClickListener(this);
        rl_info.setOnClickListener(this);
        rl_qiang.setOnClickListener(this);
        rl_coffe.setOnClickListener(this);
        rl_zichan.setOnClickListener(this);
        rl_boss.setOnClickListener(this);
        rl_fgs.setOnClickListener(this);
        rl_zgs.setOnClickListener(this);

        // 小红点
        tv_dai_ban = (TextView) v.findViewById(R.id.tv_dai_ban);
        tv_dai_yue = (TextView) v.findViewById(R.id.tv_dai_yue);
        tv_info = (TextView) v.findViewById(R.id.tv_info);
        tv_qiang = (TextView) v.findViewById(R.id.tv_qiang);
        tv_coffe = (TextView) v.findViewById(R.id.tv_coffe);
        tv_zichan = (TextView) v.findViewById(R.id.tv_zichan);
        tv_boss = (TextView) v.findViewById(R.id.tv_boss);
        tv_zgs = (TextView) v.findViewById(R.id.tv_zgs);
        tv_fgs = (TextView) v.findViewById(R.id.tv_fgs);
        // 注销
        tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
    }

    private void checkUpdate() {
        Map<String, Object> map = DeviceUtil.getVersionCodeAndName(getActivity());
        if (map == null || map.size() == 0) return;
        String vname = (String) map.get("vname");
        int vcode = (Integer) map.get("vcode");
        DataService.checkVersion(handler, vname, vcode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_phone:
                startWebViewActivity(Constant.PHONE);
                break;
            case R.id.rl_change:
                startWebViewActivity(Constant.CHANGE);
                break;
            case R.id.rl_email:
                startWebViewActivity(Constant.EMAIL);
                break;

            case R.id.rl_dai_ban:
                startListActivity(Constant.DAIBAN);
                break;
            case R.id.rl_dai_yue:
                startListActivity(Constant.DAIYUE);
                break;
            case R.id.rl_info:
                startListActivity(Constant.INFO);
                break;
            case R.id.rl_qiang:
                startListActivity(Constant.QIANG);
                break;
            case R.id.rl_coffe:
                startListActivity(Constant.COFFE);
                break;
            case R.id.rl_zichan:
                startListActivity(Constant.ZICHAN_GONGSI_DONGTAI);
                break;
            case R.id.rl_boss:
                startListActivity(Constant.BOSS);
                break;
            case R.id.rl_fgs:
                startListActivity(Constant.FENGOGNSI_DONGTAI);
                break;
            case R.id.rl_zgs:
                startListActivity(Constant.ZIGOGNSI_DONGTAI);
                break;

            case R.id.tv_cancel:
                showDailog();
                break;
            default:
                break;
        }
    }

    private void startWebViewActivity(long id) {
        for (HomeMo item : homeList) {
            long itemId = item.getId();
            if (itemId == id) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                Bundle bundle = new Bundle();
//				bundle.putSerializable("item", item);
                intent.putExtra("mName", item.getItemName());
                intent.putExtra("mUrlOrHref", item.getHref());
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                break;
            }
        }
    }

    private void startListActivity(long id) {
        for (HomeMo item : homeList) {
            long itemId = item.getId();
            if (itemId == id) {
                Intent intent = new Intent(getActivity(), SecondLevelActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, 0);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        initData();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showDailog() {
        PreferenceUtil.showDiaDlg(getActivity(), -1, "确定要退出吗？", "", "确定", "取消", new CustomerDialog.ClickCallBack() {

            @Override
            public void onOk(CustomerDialog dlg) {
                dlg.dismissDlg();
                getActivity().finish();
            }

            @Override
            public void onCancel(CustomerDialog dlg) {
                dlg.dismissDlg();
            }
        }, 1);
    }

    private void initData() {
        showWaitProgress();
        DataService.getHomeList(handler);
    }

    private void setHomeListData() {
        for (HomeMo item : homeList) {
            int id = (int) item.getId();
            switch (id) {
                case Constant.DAIBAN: // 代办
                    if (item.isHasNum()) setTaskNum(tv_dai_ban, item.getTaskNum());
                    break;
                case Constant.DAIYUE: // 待阅
                    if (item.isHasNum()) setTaskNum(tv_dai_yue, item.getTaskNum());
                    break;
                case Constant.INFO: // 通知公告
                    if (item.isHasNum()) setTaskNum(tv_info, item.getTaskNum());
                    break;
                case Constant.QIANG: // 强哥心语
                    if (item.isHasNum()) setTaskNum(tv_qiang, item.getTaskNum());
                    break;
                case Constant.COFFE: // 咖啡时间
                    if (item.isHasNum()) setTaskNum(tv_coffe, item.getTaskNum());
                    break;
                case Constant.ZICHAN_GONGSI_DONGTAI: // 资产动态
                    if (item.isHasNum()) setTaskNum(tv_zichan, item.getTaskNum());
                    break;
                case Constant.BOSS: // 领导动态
                    if (item.isHasNum()) setTaskNum(tv_boss, item.getTaskNum());
                    break;
                case Constant.ZIGOGNSI_DONGTAI: // 子公司动态
                    if (item.isHasNum()) setTaskNum(tv_zgs, item.getTaskNum());
                    break;
                case Constant.FENGOGNSI_DONGTAI: // 分公司动态
                    if (item.isHasNum()) setTaskNum(tv_fgs, item.getTaskNum());
                    break;
                default:
                    break;
            }
        }
    }

    public void showWaitProgress() {
        progress = new ProgressDialog(getActivity());
        progress.setMessage("正在加载...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
    }

    public void closeWaitProgress() {
        if (null != progress && progress.isShowing()) {
            progress.dismiss();
            progress = null;
        }
    }

    private void setTaskNum(TextView tv, int num) {
        if (num == 0) {
            tv.setVisibility(View.INVISIBLE);
        } else if (num > 99) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("99+");
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(num + "");
        }
    }
//	@Override
//	public void onResume() {
//		super.onResume();
//		initData();
//	}

}
