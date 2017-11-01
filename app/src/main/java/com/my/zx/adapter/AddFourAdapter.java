package com.my.zx.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.zx.R;
import com.my.zx.model.HomeMo;
import com.my.zx.utils.ImageLoadUtil;

import java.util.List;

public class AddFourAdapter extends RecyclerView.Adapter<AddFourAdapter.MyRecycleViewHolder> {
    private List<HomeMo> dbList;
    private List<HomeMo> initialList;

    public AddFourAdapter(List<HomeMo> dbList, List<HomeMo> initialList) {
        this.dbList = dbList;
        this.initialList = initialList;
    }

    @Override
    public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_add_item, parent, false);
        return new MyRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyRecycleViewHolder holder, final int position) {

        final HomeMo home = initialList.get(position);
//        final boolean hasMe = doDbListHasMe(home.getId());

        holder.tv_type.setText(home.getItemName());
        holder.iv_type.setImageResource(ImageLoadUtil.matchImage(home.getId()));
        holder.iv_cb.setBackgroundResource(doDbListHasMe(home.getId()) ? R.drawable.iv_add_selected : R.drawable.iv_add_normal);

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doDbListHasMe(home.getId())) {
                    holder.iv_cb.setBackgroundResource(R.drawable.iv_add_normal);
                    delMe(home.getId());
                } else {
                    holder.iv_cb.setBackgroundResource(R.drawable.iv_add_selected);
                    dbList.add(home);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return initialList.size();
    }


    public class MyRecycleViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_type;
        private ImageView iv_cb;
        private TextView tv_type;
        private LinearLayout ll_item;

        private MyRecycleViewHolder(View itemView) {
            super(itemView);

            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            iv_type = (ImageView) itemView.findViewById(R.id.iv_type);
            iv_cb = (ImageView) itemView.findViewById(R.id.iv_cb);
            iv_cb = (ImageView) itemView.findViewById(R.id.iv_cb);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    //去数据库list里寻找 看当前项在不在里面
    private boolean doDbListHasMe(long id) {
        boolean hasMe = false;
        for (HomeMo item : dbList) {
            if (id == item.getId()) {
                hasMe = true;
                break;
            }
        }
        return hasMe;
    }

    //去数据库list里删除这一项
    private void delMe(long id) {
        for (HomeMo item : dbList) {
            if (id == item.getId()) {
                dbList.remove(item);
                break;
            }
        }
    }
}
