package cn.bmob.imdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;

/**
 * Created by Rui on 2018/2/25.
 */

public class ListDropMenuKindHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Context mContext;
    @Bind(R.id.item_drop_menu)
    TextView mTextView;
    String text;


    public ListDropMenuKindHolder(View itemView, Context mContext) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mContext = mContext;
    }

    public void bindView(String s) {
        mTextView.setText(s);
        text = s;
        mTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
