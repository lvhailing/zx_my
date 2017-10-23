package com.my.zx.ac;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zx.Constant;
import com.my.zx.R;
import com.my.zx.model.AdPhotoMo;
import com.my.zx.model.HomeMo;
import com.my.zx.model.SecondLevelMo;
import com.my.zx.model.VersionMo;
import com.my.zx.net.DataService;
import com.my.zx.net.JsonRequest;
import com.my.zx.net.MesType;
import com.my.zx.net.ResultObject;
import com.my.zx.utils.DeviceUtil;
import com.my.zx.utils.ImageLoadUtil;
import com.my.zx.utils.PreferenceUtil;
import com.my.zx.utils.ToastUtil;
import com.my.zx.utils.UpdateUtil;
import com.my.zx.widegt.CustomerDialog;

import java.util.List;
import java.util.Map;

public class MainThirdActivity extends BaseActivity implements View.OnClickListener {
    private ImageView tvCancel;
    private ViewPager vpTop;
    private ViewPager vpBtm;
    private TextView tvMore;
    //    private MyListView lv;
    private ImageView iv_bg_c1, iv_bg_c2;

    private RelativeLayout rl_dai_ban,/* rl_yi_ban,*/
            rl_dai_yue, /*rl_yi_yue, */
            rl_phone, rl_email, rl_qiang, rl_coffe;
    private RelativeLayout rl_info, rl_change, rl_zichan, rl_boss, rl_fgs, rl_zgs;
    private TextView tv_dai_ban, tv_dai_yue;
    private ScrollView sv;

    //    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private List<AdPhotoMo> adPhotoList;
    private List<HomeMo> homeList;
    private List<SecondLevelMo> bossList;

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
                case MesType.Ad_Photos:
                    closeWaitProgress();
                    if (ro.getSuccess()) {
                        adPhotoList = (List<AdPhotoMo>) ro.getObject();
                        if (adPhotoList == null || adPhotoList.size() <= 0) {
                            return;
                        }
                        setAdPhotoListData();
                    } else {
                        Toast.makeText(getApplicationContext(), "获取图片地址失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MesType.GETHOMELIST:
                    closeWaitProgress();
                    if (ro.getSuccess()) {
                        homeList = (List<HomeMo>) ro.getObject();
                        if (homeList == null || homeList.size() <= 0) {
                            return;
                        }
                        setHomeListData();
                    } else {
                        Toast.makeText(getApplicationContext(), "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MesType.GETHOME_ITEMLIST:
                    closeWaitProgress();
                    if (ro.getSuccess()) {
                        bossList = (List<SecondLevelMo>) ro.getObject();
                        if (bossList == null || bossList.size() <= 0) {
                            return;
                        }

                        setBossListData();
                    } else {
                        Toast.makeText(getApplicationContext(), "没有最新数据了", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MesType.VERSION:
                    if (ro.getSuccess()) {
                        VersionMo v = (VersionMo) ro.getObject();
                        if (v != null) {
                            new UpdateUtil().checkVersion(MainThirdActivity.this, v);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        initView();
        initVpTop();
        initVpBtm();
        initBoss();
        initHomeList();
        checkUpdate();
    }

    private void initView() {
        tvCancel = (ImageView) findViewById(R.id.tv_cancel);
        iv_bg_c1 = (ImageView) findViewById(R.id.iv_bg_c1);
        iv_bg_c2 = (ImageView) findViewById(R.id.iv_bg_c2);
        vpTop = (ViewPager) findViewById(R.id.vp_top);
        vpBtm = (ViewPager) findViewById(R.id.vp_btm);
        tvMore = (TextView) findViewById(R.id.tv_more);
//        lv = (MyListView) findViewById(R.id.lv);
        sv = (ScrollView) findViewById(R.id.sv);

        tvCancel.setOnClickListener(this);
        tvMore.setOnClickListener(this);
    }

    private void initViewLeft(View view) {
        rl_dai_ban = (RelativeLayout) view.findViewById(R.id.rl_dai_ban);
        rl_dai_yue = (RelativeLayout) view.findViewById(R.id.rl_dai_yue);
        rl_phone = (RelativeLayout) view.findViewById(R.id.rl_phone);
        rl_info = (RelativeLayout) view.findViewById(R.id.rl_info);
//        rl_yi_ban = (RelativeLayout) view.findViewById(R.id.rl_yi_ban);
//        rl_yi_yue = (RelativeLayout) view.findViewById(R.id.rl_yi_yue);
        rl_qiang = (RelativeLayout) view.findViewById(R.id.rl_qiang);
        rl_coffe = (RelativeLayout) view.findViewById(R.id.rl_coffe);
        rl_zichan = (RelativeLayout) view.findViewById(R.id.rl_zichan);
        rl_boss = (RelativeLayout) view.findViewById(R.id.rl_boss);

        rl_dai_ban.setOnClickListener(this);
        rl_dai_yue.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_info.setOnClickListener(this);
//        rl_yi_ban.setOnClickListener(this);
//        rl_yi_yue.setOnClickListener(this);
        rl_qiang.setOnClickListener(this);
        rl_coffe.setOnClickListener(this);
        rl_zichan.setOnClickListener(this);
        rl_boss.setOnClickListener(this);

        // 小红点
        tv_dai_ban = (TextView) view.findViewById(R.id.tv_dai_ban);
        tv_dai_yue = (TextView) view.findViewById(R.id.tv_dai_yue);
    }

    private void initViewRight(View view) {
        rl_change = (RelativeLayout) view.findViewById(R.id.rl_change);
        rl_fgs = (RelativeLayout) view.findViewById(R.id.rl_fgs);
        rl_zgs = (RelativeLayout) view.findViewById(R.id.rl_zgs);
//        rl_email = (RelativeLayout) view.findViewById(R.id.rl_email);    //邮箱本期暂时不要 340版 3/31号

//        rl_email.setOnClickListener(this);   //邮箱本期暂时不要 340版 3/31号
        rl_change.setOnClickListener(this);
        rl_fgs.setOnClickListener(this);
        rl_zgs.setOnClickListener(this);
    }

    private void initVpTop() {
        //获取顶部广告图片
        DataService.getHomeAdPhotos(handler);
    }

    private void initVpBtm() {
        vpBtm.setAdapter(new MyBtmAdapter());

        vpBtm.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    iv_bg_c1.setBackgroundResource(R.drawable.viewpager_bg_circle1);
                    iv_bg_c2.setBackgroundResource(R.drawable.viewpager_bg_circle2);
                } else {
                    iv_bg_c1.setBackgroundResource(R.drawable.viewpager_bg_circle2);
                    iv_bg_c2.setBackgroundResource(R.drawable.viewpager_bg_circle1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void checkUpdate() {
        Map<String, Object> map = DeviceUtil.getVersionCodeAndName(this);
        if (map == null || map.size() == 0) return;
        String vname = (String) map.get("vname");
        int vcode = (Integer) map.get("vcode");
        DataService.checkVersion(handler, vname, vcode);
    }

    private void setAdPhotoListData() {
        vpTop.setOffscreenPageLimit(4);
        vpTop.setAdapter(new MyTopAdapter());
//        sv.scrollTo(0, 0);
//        sv.post(new Runnable() {
//            @Override
//            public void run() {
//                sv.scrollTo(0, 0);
//            }
//        });
    }

    private class MyTopAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView iv = new ImageView(MainThirdActivity.this);
            final AdPhotoMo ad = adPhotoList.get(position);
            try {
//                BitmapFactory.Options opts = new BitmapFactory.Options();
//                opts.inSampleSize = 5;
//                DisplayImageOptions.Builder options = new DisplayImageOptions.Builder().decodingOptions(opts);
//                imageLoader.displayImage(JsonRequest.baseUrl + ad.getImg(), iv);
//                imageLoader.displayImage("http://news.cjxy.edu.cn/upfiles/file/201407/20140711130737743.jpg", iv);

                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                new ImageLoadUtil(JsonRequest.baseUrl + ad.getImg(), iv);

//                iv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MainThirdActivity.this, WebviewActivity.class);
//                        intent.putExtra("mName", ad.getType());
//                        intent.putExtra("mUrlOrHref", ad.getUrl());
//                        startActivity(intent);
//                    }
//                });

            } catch (Exception e) {
            }
            container.addView(iv, position);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView image = (ImageView) vpTop.getChildAt(position);
            image.setBackgroundDrawable(null);
            container.removeViewAt(position);
        }

        @Override
        public int getCount() {
            return adPhotoList.size();
        }
    }

    private class MyBtmAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view;
            if (position == 0) {
                view = LayoutInflater.from(MainThirdActivity.this).inflate(R.layout.grid_left, null);
                initViewLeft(view);
            } else {
                view = LayoutInflater.from(MainThirdActivity.this).inflate(R.layout.grid_right, null);
                initViewRight(view);
            }
            container.addView(view, position);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeViewAt(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_phone:
                //暂时将文本和点击事件都换成我的已办 4/18
                startListActivity(Constant.YIBAN);
//                startWebViewActivity(Constant.PHONE);
                break;
            case R.id.rl_change:
                startWebViewActivity(Constant.CHANGE);
                break;
            case R.id.rl_dai_ban:
                startListActivity(Constant.DAIBAN);
                break;
            case R.id.rl_dai_yue:
                startListActivity(Constant.DAIYUE);
                break;
            case R.id.rl_info:
                //暂时将文本和点击事件都换成我的已阅 4/18
                startListActivity(Constant.YIYUE);
//                startListActivity(Constant.INFO);
                break;
            case R.id.rl_qiang:
                startListActivity(Constant.QIANG);
                break;
            case R.id.rl_coffe:
                startListActivity(Constant.COFFE);
                break;
            case R.id.rl_zichan:
                startListActivity(Constant.ZICHAN);
                break;
            case R.id.rl_boss:
                //将文本和点击事件都换成 "通知公告" 6/15
                startListActivity(Constant.INFO);
//                startListActivity(Constant.BOSS);
                break;
            case R.id.rl_fgs:
                startListActivity(Constant.FGS);
                break;
            case R.id.rl_zgs:
                startListActivity(Constant.ZGS);
                break;

            case R.id.tv_cancel:
                showDailog();
                break;
            case R.id.tv_more:
                startListActivity(Constant.ZICHAN);
                break;
            case R.id.rl_1:
                startWebViewActivity2(0);
                break;
            case R.id.rl_2:
                startWebViewActivity2(1);
                break;
            case R.id.rl_3:
                startWebViewActivity2(2);
                break;
            case R.id.rl_4:
                startWebViewActivity2(3);
                break;
            default:
                break;
        }
    }

    public void startWebViewActivity(long id) {
        for (HomeMo item : homeList) {
            long itemId = item.getId();
            if (itemId == id) {
                Intent intent = new Intent(this, WebviewActivity.class);
                Bundle bundle = new Bundle();
//				bundle.putSerializable("item", item);
                intent.putExtra("mName", item.getItemName());
                intent.putExtra("mUrlOrHref", item.getHref());
                intent.putExtras(bundle);
                this.startActivity(intent);
                break;
            }
        }
    }

    private void startListActivity(long id) {
        for (HomeMo item : homeList) {
            long itemId = item.getId();
            if (itemId == id) {
                Intent intent = new Intent(this, SecondLevelActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                intent.putExtras(bundle);
                this.startActivityForResult(intent, 0);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        initHomeList();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showDailog() {
        PreferenceUtil.showDiaDlg(this, -1, "确定要退出吗？", "", "确定", "取消", new CustomerDialog.ClickCallBack() {

            @Override
            public void onOk(CustomerDialog dlg) {
                dlg.dismissDlg();
                finish();
            }

            @Override
            public void onCancel(CustomerDialog dlg) {
                dlg.dismissDlg();
            }
        }, 1);
    }

    private void initHomeList() {
        showWaitProgress();
        DataService.getHomeList(handler);
    }

    private void setTaskNum(TextView tv, int num) {
        if (num == 0) {
            tv.setVisibility(View.INVISIBLE);
        } else if (num > 99) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("99+");
            tv.setBackgroundResource(R.drawable.num_bg_more);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(num + "");
        }
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
                case Constant.ZICHAN: // 资产动态  为底部listview 设置数据
                    initBossList(item.getHref());
                    break;
                default:
                    break;
            }
        }
    }

    //获取资产动态  为底部listview 设置数据
    private void initBossList(String mUrl) {
        showWaitProgress();
        DataService.getSecondLevelList(handler, mUrl);
    }

    private void setBossListData() {
        int size = bossList.size();
        if (size == 0) {
            return;
        } else if (size > 4) {
            while (bossList.size() > 4) {
                bossList.remove(bossList.size() - 1);
            }
        } else if (size == 3) {
            rl_4.setVisibility(View.GONE);
        } else if (size == 2) {
            rl_3.setVisibility(View.GONE);
            rl_4.setVisibility(View.GONE);
        } else if (size == 1) {
            rl_2.setVisibility(View.GONE);
            rl_3.setVisibility(View.GONE);
            rl_4.setVisibility(View.GONE);
        }

        SecondLevelMo hi1 = bossList.get(0);
        tv_title1.setText(hi1.getTitle());
        tv_content1.setText(hi1.getType());
        tv_time1.setText(hi1.getTime());
        if (hi1.isTitleRed()) {
            tv_title1.setTextColor(getResources().getColor(R.color.main_red_color));
        }

        if (size == 1) {
            return;
        }
        SecondLevelMo hi2 = bossList.get(1);
        tv_title2.setText(hi2.getTitle());
        tv_content2.setText(hi2.getType());
        tv_time2.setText(hi2.getTime());
        if (hi2.isTitleRed()) {
            tv_title2.setTextColor(getResources().getColor(R.color.main_red_color));
        }

        if (size == 2) {
            return;
        }
        SecondLevelMo hi3 = bossList.get(2);
        tv_title3.setText(hi3.getTitle());
        tv_content3.setText(hi3.getType());
        tv_time3.setText(hi3.getTime());
        if (hi3.isTitleRed()) {
            tv_title3.setTextColor(getResources().getColor(R.color.main_red_color));
        }

        if (size == 3) {
            return;
        }
        SecondLevelMo hi4 = bossList.get(3);
        tv_title4.setText(hi4.getTitle());
        tv_content4.setText(hi4.getType());
        tv_time4.setText(hi4.getTime());
        if (hi4.isTitleRed()) {
            tv_title4.setTextColor(getResources().getColor(R.color.main_red_color));
        }

//        lv.setAdapter(new SecondLevelAdapter(this, bossList));
//        sv.scrollTo(0,0);
//        sv.post(new Runnable() {
//            @Override
//            public void run() {
//                sv.scrollTo(0, 0);
//            }
//        });

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startWebViewActivity2(bossList.get(position));
//            }
//        });
    }

    private void startWebViewActivity2(int position) {
        if (bossList == null || bossList.get(0) == null) {
            return;
        }
        SecondLevelMo item = bossList.get(position);
        if (item == null || item.getType() == null || item.getUrl() == null) {
            return;
        }
        Intent intent = new Intent(this, WebviewActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("mName", item.getType());
        intent.putExtra("mUrlOrHref", item.getUrl());
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }


    private RelativeLayout rl_1, rl_2, rl_3, rl_4;
    private TextView tv_title1, tv_title2, tv_title3, tv_title4;
    private TextView tv_content1, tv_content2, tv_content3, tv_content4;
    private TextView tv_time1, tv_time2, tv_time3, tv_time4;


    private void initBoss() {
        rl_1 = (RelativeLayout) findViewById(R.id.rl_1);
        rl_2 = (RelativeLayout) findViewById(R.id.rl_2);
        rl_3 = (RelativeLayout) findViewById(R.id.rl_3);
        rl_4 = (RelativeLayout) findViewById(R.id.rl_4);

        rl_1.setOnClickListener(this);
        rl_2.setOnClickListener(this);
        rl_3.setOnClickListener(this);
        rl_4.setOnClickListener(this);

        tv_title1 = (TextView) findViewById(R.id.tv_title1);
        tv_title2 = (TextView) findViewById(R.id.tv_title2);
        tv_title3 = (TextView) findViewById(R.id.tv_title3);
        tv_title4 = (TextView) findViewById(R.id.tv_title4);

        tv_content1 = (TextView) findViewById(R.id.tv_content1);
        tv_content2 = (TextView) findViewById(R.id.tv_content2);
        tv_content3 = (TextView) findViewById(R.id.tv_content3);
        tv_content4 = (TextView) findViewById(R.id.tv_content4);

        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        tv_time3 = (TextView) findViewById(R.id.tv_time3);
        tv_time4 = (TextView) findViewById(R.id.tv_time4);
    }
}
