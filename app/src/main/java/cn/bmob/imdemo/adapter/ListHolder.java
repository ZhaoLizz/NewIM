package cn.bmob.imdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.bean.Things;

/**
 * Created by Rui on 2018/2/25.
 */

public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @Bind(R.id.item_list_image)
    ImageView imageView;
    @Bind(R.id.item_list_kind)
    TextView kindTextView;
    @Bind(R.id.item_list_location)
    TextView locationTextView;
    @Bind(R.id.item_list_name)
    TextView nameTextView;
    @Bind(R.id.item_list_time)
    TextView timeTextView;

    private Context context;

    public ListHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
    }

    public void bindHolder(Things thing) {
        imageView.setImageBitmap(thing.getmPicture());
        kindTextView.setText(thing.getmKind());
        nameTextView.setText(thing.getmName());
        timeTextView.setText(thing.getmTime());
        locationTextView.setText(thing.getmLocation());
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(context, "我被点击了", Toast.LENGTH_SHORT).show();
    }
}
