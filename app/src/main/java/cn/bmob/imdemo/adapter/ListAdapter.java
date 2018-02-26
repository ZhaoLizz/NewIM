package cn.bmob.imdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.base.BaseRecyclerAdapter;
import cn.bmob.imdemo.adapter.base.BaseRecyclerHolder;
import cn.bmob.imdemo.adapter.base.IMutlipleItem;
import cn.bmob.imdemo.bean.Things;

/**
 * Created by Rui on 2018/2/25.
 */

public class ListAdapter extends RecyclerView.Adapter<ListHolder> {
    /**
     * 支持一种或多种Item布局
     *
     * @param context
     * @param items
     * @param datas
     */
    private List<Things> mList;
    private Context mContext;
    private ListHolder holder;

    public ListAdapter(List<Things> mList, Context context) {
        this.mList = mList;
        this.mContext = context;
    }


    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.item_list, parent, false);
        holder = new ListHolder(v,mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.bindHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
