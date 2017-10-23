package com.my.zx.ac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.my.zx.R;


public class SplashActivity extends Activity {
    protected static final int MSG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        messageDelay();
    }

    protected void messageDelay() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Message msg = mHandler.obtainMessage();
                msg.what = MSG;
                msg.sendToTarget();
            }
        }, 3000L);
    }

    @SuppressWarnings("unchecked")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG: {
                    Intent intent = new Intent(SplashActivity.this, MainFourActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                default:
                    break;
            }
        }

    };

}
