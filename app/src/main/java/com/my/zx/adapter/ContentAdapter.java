package com.my.zx.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.zx.R;
import com.my.zx.callback.DragHolderCallBack;
import com.my.zx.callback.RecycleCallBack;
import com.my.zx.model.HomeMo;
import com.my.zx.utils.ImageLoadUtil;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyRecycleViewHolder> {
    private List<HomeMo> list;
    private RecycleCallBack mRecycleClick;
    private boolean isEditMode;

    public ContentAdapter(RecycleCallBack click, List<HomeMo> data) {
        this.list = data;
        this.mRecycleClick = click;
    }

    public void setData(List<HomeMo> data) {
        this.list = data;
    }

    //关闭编辑模式
    public void closeEditMode() {
        isEditMode = false;
        notifyDataSetChanged();
    }

    @Override
    public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_home_item, parent, false);
        return new MyRecycleViewHolder(view, mRecycleClick);
    }

    @Override
    public void onBindViewHolder(final MyRecycleViewHolder holder, final int position) {

        //非编辑模式
        if (!isEditMode) {
            //隐藏删除按钮
            holder.iv_del_item.setVisibility(View.GONE);
            //在编辑状态下，防止删除某项view复用，导致该项空白
            holder.rl.setVisibility(View.VISIBLE);
            if (position != list.size()) {
                //不是最后一项 正常
                HomeMo home = list.get(position);
                holder.tv_type.setText(home.getItemName());
                holder.iv_type.setImageResource(ImageLoadUtil.matchImage(home.getId()));
                if (home.isHasNum()) {
                    holder.tv_red.setVisibility(View.VISIBLE);
                    setTaskNum(holder.tv_red, home.getTaskNum());
                } else {
                    holder.tv_red.setVisibility(View.GONE);
                }
            } else {
                //是最后一项 显示添加更多
                holder.tv_type.setText("添加更多");
                holder.iv_type.setImageResource(R.drawable.home_add);
            }
            return;
        }

        //编辑模式
        //显示删除按钮
        holder.iv_del_item.setVisibility(View.VISIBLE);
        if (position != list.size()) {
            //不是最后一项 隐藏添加更多
            holder.rl.setVisibility(View.VISIBLE);
            holder.tv_type.setText(list.get(position).getItemName());
            holder.iv_type.setImageResource(ImageLoadUtil.matchImage(list.get(position).getId()));
        } else {
            holder.rl.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }


    public class MyRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DragHolderCallBack {

        private final TextView tv_red;
        private ImageView iv_type;
        private ImageView iv_del_item;
        private TextView tv_type;
        private RelativeLayout rl;
        private RecycleCallBack callBack;

        private MyRecycleViewHolder(View itemView, RecycleCallBack callBack) {
            super(itemView);
            this.callBack = callBack;

            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            iv_type = (ImageView) itemView.findViewById(R.id.iv_type);
            iv_del_item = (ImageView) itemView.findViewById(R.id.iv_del_item);
            tv_red = (TextView) itemView.findViewById(R.id.tv_red);

            iv_del_item.setOnClickListener(this);
            rl.setOnClickListener(this);
        }

        @Override
        public void onSelect() {
            //长按某项的回调
            itemView.setBackgroundColor(Color.LTGRAY);
            if (!isEditMode) {
                //开始编辑
                isEditMode = true;
                notifyDataSetChanged();
                callBack.itemOnLongClick();
            }
            Log.i("aaa", "onSelect...");
        }

        @Override
        public void onClear() {
            //长按
            itemView.setBackgroundResource(R.drawable.right_bottom_view);
            notifyDataSetChanged();
            Log.i("aaa", "onClear...");
        }

        @Override
        public void onClick(View v) {
            //点击某项的回调
            if (null != callBack) {
                callBack.itemOnClick(getAdapterPosition(), v);
            }
        }
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
}
