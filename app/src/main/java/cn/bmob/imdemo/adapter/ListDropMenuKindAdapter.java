package cn.bmob.imdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.bmob.imdemo.R;

/**
 * Created by Rui on 2018/2/25.
 */

public class ListDropMenuKindAdapter extends RecyclerView.Adapter<ListDropMenuKindHolder> {

    private List<String> mKindsList;
    private Context mContext;

    public ListDropMenuKindAdapter(List<String> mKindsList, Context mContext) {
        this.mKindsList = mKindsList;
        this.mContext = mContext;
    }

    @Override
    public ListDropMenuKindHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.item_dropmenu, parent, false);
        return new ListDropMenuKindHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(ListDropMenuKindHolder holder, int position) {
        holder.bindView(mKindsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mKindsList.size();
    }
}
