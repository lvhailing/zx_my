package com.my.zx.ac;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.zx.R;
import com.my.zx.adapter.AddFourAdapter;
import com.my.zx.db.DBManager;
import com.my.zx.model.HomeMo;
import com.my.zx.utils.ToastUtil;

import java.util.List;

/**
 * 编辑页面 只做加减
 */

public class AddFourActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_done;

    private List<HomeMo> dbList;    //数据库集合
    private List<HomeMo> initialList;    //默认的集合

    private RecyclerView rv;
    private AddFourAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_four);

        initDatas();
    }

    private void initDatas() {
        dbList = (List<HomeMo>) getIntent().getSerializableExtra("dbList");

        rv = (RecyclerView) findViewById(R.id.rv);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_done = (TextView) findViewById(R.id.tv_done);

        iv_back.setOnClickListener(this);
        tv_done.setOnClickListener(this);

        rv.setLayoutManager(new LinearLayoutManager(this));

        //从数据库取出集合，如果是第一次安装则生成默认集合
        initialList = com.my.zx.utils.DbUtil.initializeAddActivityItems();
        mAdapter = new AddFourAdapter(dbList, initialList);
        rv.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:   //返回
                finish();
                break;
            case R.id.tv_done:   //完成
                DBManager.saveHomes(this, dbList);
                ToastUtil.showToastPersonality(AddFourActivity.this, "保存完成");
//                showWaitProgress();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        closeWaitProgress();
//                    }
//                }, 1500);
                break;
        }
    }
}
