package cn.bmob.imdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.bmob.imdemo.R;

/**
 * Created by Rui on 2018/2/25.
 */

public class ListDropMenuSortAdapter extends RecyclerView.Adapter <ListDropMenuSortHolder>{
    private List<String> mSortList;
    private Context mContext;

    public ListDropMenuSortAdapter(List<String> mSortsList, Context mContext) {
        this.mSortList = mSortsList;
        this.mContext = mContext;
    }

    @Override
    public ListDropMenuSortHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.item_dropmenu, parent, false);
        return new ListDropMenuSortHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(ListDropMenuSortHolder holder, int position) {
        holder.bindView(mSortList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSortList.size();
    }
}
