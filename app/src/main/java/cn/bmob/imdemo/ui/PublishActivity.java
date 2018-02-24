package cn.bmob.imdemo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseActivity;
import cn.bmob.imdemo.util.ViewUtil;


public class PublishActivity extends BaseActivity {
    Uri mPhotoUri;

    @Bind(R.id.publish_good_img)
    ImageView img_publish_good;
    @Bind(R.id.publish_good_name_tv)
    TextView tv_publish_good_name;
    @Bind(R.id.publish_good_category_tv)
    TextView tv_publish_good_category;
    @Bind(R.id.publish_good_time_tv)
    TextView tv_publish_good_time;
    @Bind(R.id.publish_good_location_tv)
    TextView tv_publish_good_location;
    @Bind(R.id.publish_good_publish_btn)
    Button btn_publish_good_publish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initView();
    }


    @Override
    protected void initView() {
        mPhotoUri = getIntent().getParcelableExtra(Intent.EXTRA_ORIGINATING_URI);
        Logger.d(mPhotoUri.toString());
        ViewUtil.setPicture(mPhotoUri, R.mipmap.default_head, img_publish_good, null);

    }
}
