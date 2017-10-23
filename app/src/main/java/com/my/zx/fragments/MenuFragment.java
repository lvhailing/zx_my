package com.my.zx.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.zx.Constant;
import com.my.zx.R;
import com.my.zx.ac.FeedbackActivity;
import com.my.zx.ac.LoginActivity;
import com.my.zx.ac.MainFourActivity;
import com.my.zx.ac.MoreActivity;
import com.my.zx.ac.MySettingActivity;
import com.my.zx.utils.ToastUtil;
import com.my.zx.utils.Util;

/**
 * 侧滑菜单页
 */
public class MenuFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private FragmentActivity context;
    private ImageView iv_user_photo; // 头像
    private TextView tv_login_or_name; //
    private RelativeLayout rl_more; // 更多功能
    private RelativeLayout rl_setting; // 设置
    private RelativeLayout rl_feedback; // 意见反馈
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_menu, container, false);
            initView();
        } else {
            if (mView.getParent() != null) {
                ((ViewGroup) mView.getParent()).removeView(mView);
            }
        }
        return mView;
    }

    private void initView() {
        context = getActivity();
        iv_user_photo = (ImageView) mView.findViewById(R.id.iv_user_photo);
        tv_login_or_name = (TextView) mView.findViewById(R.id.tv_login_or_name);
        rl_more = (RelativeLayout) mView.findViewById(R.id.rl_more);
        rl_setting = (RelativeLayout) mView.findViewById(R.id.rl_setting);
        rl_feedback = (RelativeLayout) mView.findViewById(R.id.rl_feedback);

        tv_login_or_name.setOnClickListener(this);
        iv_user_photo.setOnClickListener(this);
        rl_more.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);

    }

    public void setUserName(String name) {
        //未登录 、name为空、name为Anonymous（访客状态下）；都显示登录
        if (!Util.checkLogin() || TextUtils.isEmpty(name) || name.equals("Anonymous") || name.equals("登录")) {
            //未登录
            tv_login_or_name.setText("登录");
        } else {
            //已登录
            tv_login_or_name.setText(name);
        }
    }

    @Override
    public void onClick(View v) {
        //先获取是否登录
        boolean isLogin = Util.checkLogin();
        switch (v.getId()) {
            case R.id.iv_user_photo: //用户头像
            case R.id.tv_login_or_name: //登录或姓名，按钮
                //获取更多按钮的当前文案
                String moreBtnText = ((MainFourActivity) getActivity()).getMoreBtnText();
                if (moreBtnText.equals("完成")) {
                    //编辑未完成时，即去登录提示
                    ToastUtil.showToast(Constant.NO_SAVE_EDIT);
                    return;
                }
                String str = tv_login_or_name.getText().toString();
                if (!isLogin || str.equals("登录") || str.equals("Anonymous")) {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_more: // 更多
                if (!isLogin) {
                    //去登录
                    Util.goToLogin(context);
                    return;
                }
                intent = new Intent(context, MoreActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting: // 设置
                if (!isLogin) {
                    //去登录
                    Util.goToLogin(context);
                    return;
                }
                intent = new Intent(context, MySettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_feedback: // 反馈信息
                if (!isLogin) {
                    //去登录
                    Util.goToLogin(context);
                    return;
                }
                intent = new Intent(context, FeedbackActivity.class);
                startActivity(intent);
                break;
        }
        //关闭侧滑栏
        ((MainFourActivity) context).toggle();
    }

}
