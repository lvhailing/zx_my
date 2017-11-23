package com.my.zx.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.my.zx.Constant;
import com.my.zx.R;
import com.my.zx.ac.AddFourActivity;
import com.my.zx.ac.MainFourActivity;
import com.my.zx.ac.MoreActivity;
import com.my.zx.ac.SecondLevelActivity;
import com.my.zx.ac.WebviewActivity;
import com.my.zx.adapter.ContentAdapter;
import com.my.zx.callback.DragItemCallBack;
import com.my.zx.callback.RecycleCallBack;
import com.my.zx.db.DBManager;
import com.my.zx.model.AdPhotoMo;
import com.my.zx.model.HomeMo;
import com.my.zx.net.DataService;
import com.my.zx.net.JsonRequest;
import com.my.zx.net.MesType;
import com.my.zx.net.ResultObject;
import com.my.zx.utils.DeviceUtil;
import com.my.zx.utils.PreferenceUtil;
import com.my.zx.utils.ToastUtil;
import com.my.zx.utils.Util;
import com.my.zx.utils.fresco.ImageLoader;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 首页
 */
public class HomeFourFragment extends BaseFragment implements RecycleCallBack, View.OnClickListener {

    private FragmentActivity context;
    private ViewPager vp_top;
    private LinearLayout vp_point;
    private List<AdPhotoMo> adPhotoList;
    private TextView tv_more;
    private ImageView iv_menu;
    private RecyclerView home_rv;
    private ContentAdapter mAdapter;
    private int lastPosition;//记录上次的位置
    private int dpHeng;  //ViewPager底部小圆点之间的间距

    private List<HomeMo> dbList;   //本地数据库集合

    private List<HomeMo> jsonList;    //后台集合

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ResultObject ro = (ResultObject) msg.obj;
            switch (msg.what) {
                case MesType.Ad_Photos: // 轮播图
                    if (ro.getSuccess()) {
                        adPhotoList = (List<AdPhotoMo>) ro.getObject();
                        if (adPhotoList == null || adPhotoList.size() <= 0) {
                            return;
                        }
                        refreshAdPhotos();
                        initPointGroup(adPhotoList.size());
                    } else {
                        Toast.makeText(context, "获取图片地址失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MesType.GETHOMELIST:
                    if (ro.getSuccess()) {
                        Map<String, Object> resultMap = (Map<String, Object>) ro.getObject();

                        String userName = (String) resultMap.get("userName");
                        String userId = (String) resultMap.get("userId");
                        //修改侧滑栏的用户名
                        ((MainFourActivity) context).setMenuText(userName);
                        //保存到sp
                        PreferenceUtil.putString(context, "userName", userName);
                        PreferenceUtil.putString(context, "userId", userId);
                        //刷新更多文案
                        refreshMoreBtn();
                        //刷新退出登陆按钮状态
                        refreshLogoutBtn();

                        jsonList = (List<HomeMo>) resultMap.get("list");
                        if (jsonList != null && jsonList.size() > 0) {
                            refreshGridView();
                        }
                    } else if (ro.getCode() == 100) {
                        //session过期，则吐司返回
                        ToastUtil.showToast(ro.getMessage());
                    } else {
                        Toast.makeText(context, "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                    closeWaitProgress();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        initView(view);
        initHomeData();
        initSwipRefresh(view);
        initVpTop();
        refreshMoreBtn();
        return view;
    }

    private void initView(View view) {
        context = getActivity();
        dpHeng = DeviceUtil.dp2px(context, 5);

        vp_top = (ViewPager) view.findViewById(R.id.vp_top);
        vp_point = (LinearLayout) view.findViewById(R.id.vp_point);
        home_rv = (RecyclerView) view.findViewById(R.id.home_rv);
        iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
        tv_more = (TextView) view.findViewById(R.id.tv_more);

        tv_more.setOnClickListener(this);
        iv_menu.setOnClickListener(this);

        home_rv.setLayoutManager(new GridLayoutManager(context, 4));

        //从数据库取出集合，如果是第一次安装则生成默认集合
        dbList = DBManager.initializeItems(context);
        mAdapter = new ContentAdapter(this, dbList);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new DragItemCallBack(this));
        mItemTouchHelper.attachToRecyclerView(home_rv);
        home_rv.setAdapter(mAdapter);
    }

    private void initSwipRefresh(View view) {
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe);
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Util.checkLogin()) {
                    //已登录
                    String text = tv_more.getText().toString();
                    if (text.equals("更多")) {
                        //非编辑状态，刷新数据
                        initHomeData();
                    } else {
                        //编辑状态
                        ToastUtil.showToast("请先保存");
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
                //10秒后关掉动画
            }
        });
    }

    public void initHomeData() {
        if (getActivity() == null || context == null) {
            //推送到来后，可能系统已经长时间处于后台，防止崩溃
            return;
        }
        if (Util.checkLogin()) {
            //只有登录了，才去后台取数据
            showWaitProgress(context);
            DataService.getHomeList(handler);
        }
    }

    private void initVpTop() {
        //获取顶部广告图片
        DataService.getHomeAdPhotos(handler);
    }

    // 初始化顶部轮播图小圆点
    private void initPointGroup(int size) {
        vp_point.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.leftMargin = dpHeng;
            params.rightMargin = dpHeng;
            params.bottomMargin = 0;
            iv.setLayoutParams(params);
            if (i == 0) {
                iv.setBackgroundResource(R.drawable.vp_bg_gray);
            } else {
                iv.setBackgroundResource(R.drawable.vp_bg_white);
            }
            vp_point.addView(iv);
        }
    }

    //刷新更多按钮状态
    public void refreshMoreBtn() {
        //未登录 、请求不到userName、userName为Anonymous；都提示登录
        if (!Util.checkLogin() || TextUtils.isEmpty(PreferenceUtil.getString(context, "userName")) || PreferenceUtil.getString(context, "userName").equals("Anonymous")) {
            //未登录
            tv_more.setText("登录");
        } else {
            //已登录
            tv_more.setText("更多");
        }
    }

    //刷新退出登录按钮状态
    public void refreshLogoutBtn() {
        //未登录 、请求不到userName、userName为Anonymous；都隐藏退出登录按钮
        if (!Util.checkLogin() || TextUtils.isEmpty(PreferenceUtil.getString(context, "userName")) || PreferenceUtil.getString(context, "userName").equals("Anonymous")) {
            //未登录
            ((MainFourActivity) context).refreshLogoutBtn(false);
        } else {
            //已登录
            ((MainFourActivity) context).refreshLogoutBtn(true);
        }
    }

    //获取更多按钮文案，防止编辑未完成时，即去登录提示
    public String getMoreBtnText() {
        return tv_more.getText().toString();
    }

    private void refreshAdPhotos() {
        vp_top.setOffscreenPageLimit(4);
        vp_top.setAdapter(new MyTopAdapter());
        vp_top.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    // 设置小红点（接收到数据时，将后台给的数据映射到从dbList）
    private void refreshGridView() {
        for (HomeMo dbItem : dbList) {
            long dbId = dbItem.getId();
            for (HomeMo jsonItem : jsonList) {
                if (dbId == jsonItem.getId()) {
                    //是同一个对象
//                    dbItem.setItemName(jsonItem.getItemName());
                    dbItem.setHasNum(jsonItem.isHasNum());
                    dbItem.setHref(jsonItem.getHref());
                    dbItem.setTaskNum(jsonItem.getTaskNum());
                    dbItem.setToWhere(jsonItem.getToWhere());
                    break;
                }
            }
        }
        //同步完数据库的数据后 刷新界面
        mAdapter.notifyDataSetChanged();
    }

    // 去除小红点（退出登录时）
    public void refreshGridView_WhenLoginOut() {
        showWaitProgress(context);
        for (HomeMo item : dbList) {
            //是同一个对象
            item.setHasNum(false);
            item.setHref("");
            item.setTaskNum(0);
            item.setToWhere(0);
        }
        //同步完数据库的数据后 刷新界面
        mAdapter.notifyDataSetChanged();
        closeWaitProgress();
    }

    @Override
    public void itemOnClick(int position, View view) {
        if (position == dbList.size()) {
            //点击最后添加按钮，可以去添加更多
            Intent intent = new Intent(context, AddFourActivity.class);
            intent.putExtra("dbList", (Serializable) dbList);
            startActivityForResult(intent, 100);
            return;
        }
        //点击删除按钮
        if (view.getId() == R.id.iv_del_item) {
            dbList.remove(position);
            mAdapter.notifyItemRemoved(position);
            return;
        }
        //点击该项，如果未登录、请求不到userName、userName为Anonymous；都提示登录
        if (!Util.checkLogin() || TextUtils.isEmpty(PreferenceUtil.getString(context, "userName")) || PreferenceUtil.getString(context, "userName").equals("Anonymous")) {
            //未登录，去登录
            Util.goToLogin(context);
            return;
        }
        //已登录，去二级页面
        goToActivity(dbList.get(position));
    }

    private void goToActivity(HomeMo item) {
        if (TextUtils.isEmpty(item.getHref())) {
            ToastUtil.showToast(Constant.NO_SERVER_DATA);
            return;
        }
        if (item.getToWhere() == 0) {
            //去list页面
            Intent intent = new Intent(context, SecondLevelActivity.class);
            intent.putExtra("item", item);
            startActivityForResult(intent, 0);
        } else {
            //去h5页面
            Intent intent = new Intent(context, WebviewActivity.class);
            intent.putExtra("mName", item.getItemName());
            intent.putExtra("mUrlOrHref", item.getHref());
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<HomeMo> addedList = DBManager.initializeItems(context);
        dbList.clear();
        dbList.addAll(addedList);

        //如果没登录 直接刷新界面后返回
        if (!Util.checkLogin()) {
            mAdapter.notifyDataSetChanged();
            return;
        }

        //获取后台数据 1同步当前数据库数据  2设置小红点
        initHomeData();
    }

    @Override
    public void itemOnLongClick() {
        //长按某项
        ToastUtil.showToast("开始编辑");
        tv_more.setText("完成");
    }


    @Override
    public void onMove(int from, int to) {
        if (to > dbList.size() - 1) {
            //防止交换时越界
            return;
        }
        //移动某项
        synchronized (this) {
            if (from > to) {
                int count = from - to;
                for (int i = 0; i < count; i++) {
                    Collections.swap(dbList, from - i, from - i - 1);
                }
            } else if (from < to) {
                int count = to - from;
                for (int i = 0; i < count; i++) {
                    Collections.swap(dbList, from + i, from + i + 1);
                }
            }
            mAdapter.notifyItemMoved(from, to);
        }
    }

    private class MyTopAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            //自己实现下载图片，也可以
//            ImageView iv = new ImageView(context);
//            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            new ImageLoadUtil(JsonRequest.baseUrl + ad.getImg(), iv);

            //fresco实现下载图片
            final AdPhotoMo ad = adPhotoList.get(position);
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) LayoutInflater.from(context).inflate(R.layout.simple_drawee_view_item, null);
            String picUrlSuffix = "";
            if (!TextUtils.isEmpty(ad.getImg()) && ad.getImg().startsWith("/")) {
                picUrlSuffix = ad.getImg().substring(1);
            }
            String picUrl = JsonRequest.baseUrl + picUrlSuffix;
            ImageLoader.getInstance().loadImageLocalOrNet(simpleDraweeView, picUrl);
            container.addView(simpleDraweeView);

            return simpleDraweeView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //回收超出范围的iv
            container.removeView((SimpleDraweeView) object);
        }

        @Override
        public int getCount() {
            return adPhotoList.size();
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            //改变点位置和颜色
            vp_point.getChildAt(position).setBackgroundResource(R.drawable.vp_bg_gray);
            vp_point.getChildAt(lastPosition).setBackgroundResource(R.drawable.vp_bg_white);
            lastPosition = position;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more:
                String text = tv_more.getText().toString();
                if (text.equals("更多")) {
                    //去更多界面
                    Intent intent = new Intent(context, MoreActivity.class);
                    startActivity(intent);
                } else if (text.equals("完成")) {
                    //完成
                    tv_more.setEnabled(false);//锁定按钮 防止重复点击
                    //保存当前list  afinaldb是按顺序保存的
                    DBManager.saveHomes(context, dbList);
                    //取消编辑模式
                    mAdapter.closeEditMode();
                    ToastUtil.showToast("已保存");
                    refreshMoreBtn();
                    tv_more.setEnabled(true);   //耗时操作完成 保存完毕后 是按钮可点
                } else if (text.equals("登录")) {
                    //去登录
                    Util.goToLogin(context);
                }
                break;
            case R.id.iv_menu:
                //开或关侧滑栏
                ((MainFourActivity) context).toggle();
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (vp_top != null) {
            vp_top.removeAllViews();
            vp_top = null;
        }
        super.onDestroy();
    }


}
