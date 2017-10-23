package com.my.zx.ac;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.my.zx.R;
import com.my.zx.net.DataService;
import com.my.zx.net.MesType;
import com.my.zx.net.ResultObject;
import com.my.zx.utils.PreferenceUtil;
import com.my.zx.utils.ToastUtil;
import com.my.zx.widegt.CustomerDialog;


/**
 * 意见反馈
 */
public class FeedbackActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back; //  返回
    private EditText et_feedback; // 建议
    private Button btn_submit; // 提交
    private boolean isSaved;    //是否已保存

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            ResultObject ro = (ResultObject) msg.obj;
            switch (msg.what) {
                case MesType.FEEDBACK:
                    if (ro.getCode() == 0) {
                        //保存成功
                        et_feedback.setText("");
                        et_feedback.setHint("再来一条？");
                    }
                    //无论成功失败，都吐司后台返回的msg
                    ToastUtil.showToast(ro.getMessage());
                    isSaved = true;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initView();
    }

    public void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_feedback = (EditText) findViewById(R.id.et_feedback);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        iv_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        et_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    btn_submit.setClickable(true);
                    btn_submit.setBackgroundResource(R.drawable.login_circle_red);
                } else {
                    btn_submit.setClickable(false);
                    btn_submit.setBackgroundResource(R.drawable.login_circle_gray);
                }
            }
        });
    }

    private void submit() {
        String content = et_feedback.getText().toString();
        DataService.feedBack(handler, content);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back: // 返回
                String content = et_feedback.getText().toString();
                if (TextUtils.isEmpty(content) || isSaved) {
                    //直接返回
                    finish();
                } else {
                    //弹框提示
                    showDialog();
                }
                break;
            case R.id.btn_submit: // 提交
                submit();
                break;
        }
    }

    private void showDialog() {
        PreferenceUtil.showDiaDlg(this, 0, "确定不保存当前数据吗", "", "确定", "取消", new CustomerDialog.ClickCallBack() {
            @Override
            public void onOk(CustomerDialog dlg) {
                dlg.dismissDlg();
                finish();
            }

            @Override
            public void onCancel(CustomerDialog dlg) {
                dlg.dismissDlg();
            }
        }, 2);

    }
}
