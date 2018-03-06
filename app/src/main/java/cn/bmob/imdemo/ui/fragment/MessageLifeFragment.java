package cn.bmob.imdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseFragment;
import cn.bmob.imdemo.bean.PhotoData;
import cn.bmob.imdemo.constant.Constants;
import cn.bmob.imdemo.util.ContactUtil;
import cn.bmob.imdemo.util.TimeUtil;

/**
 * Created by a6100890 on 2018/3/7.
 */

public class MessageLifeFragment extends BaseFragment {
    private PhotoData mPhotoData;
    @Bind(R.id.message_life_photo)
    ImageView mMessageLifePhoto;
    @Bind(R.id.search_item_name)
    TextView mSearchItemName;
    @Bind(R.id.search_item_time)
    TextView mSearchItemTime;
    @Bind(R.id.search_item_location)
    TextView mSearchItemLocation;
    @Bind(R.id.search_item_btn)
    Button mSearchItemBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_life, container, false);
        ButterKnife.bind(this, view);
        mPhotoData = (PhotoData) getActivity().getIntent().getSerializableExtra(Constants.EXTRA_PHOTO_DATA);

        Glide.with(getContext()).load(mPhotoData.getPhoto().getUrl()).into(mMessageLifePhoto);
        mSearchItemName.setText(mPhotoData.getItemName());
        mSearchItemTime.setText(TimeUtil.dateToString(mPhotoData.getTime(), TimeUtil.FORMAT_DATE_TIME));
        mSearchItemLocation.setText(mPhotoData.getLocation());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.search_item_btn)
    public void onContactClicked() {
//        Toast.makeText(getContext(), "已向用户发送会话请求", Toast.LENGTH_SHORT).show();
        ContactUtil.contactUser(mPhotoData.getUser(), getContext());
    }
}
