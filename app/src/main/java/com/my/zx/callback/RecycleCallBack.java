package com.my.zx.callback;

import android.view.View;

public interface RecycleCallBack {

    void itemOnClick(int position, View view);

    void itemOnLongClick();

    void onMove(int from, int to);
}
