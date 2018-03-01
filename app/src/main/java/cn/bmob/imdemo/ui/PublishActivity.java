package cn.bmob.imdemo.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseActivity;
import cn.bmob.imdemo.bean.BitmapBodyJson;
import cn.bmob.imdemo.ui.fragment.HomeFragment;
import cn.bmob.imdemo.util.RecognizeUtil;


public class PublishActivity extends BaseActivity implements RecognizeUtil.RecognizeListener {
    @Bind(R.id.publish_4st_r_tv)
    TextView tv_time;
    @Bind(R.id.publish_time_layout)
    LinearLayout mPublishTimeLayout;
    @Bind(R.id.publish_5st_r_tv)
    TextView tv_location;
    @Bind(R.id.publish_location_layout)
    LinearLayout mPublishLocationLayout;
    @Bind(R.id.publish_good_img)
    ImageView img_publish_good;
    @Bind(R.id.publish_good_name_tv)
    TextView tv_1st_r;
    @Bind(R.id.publish_good_time_tv)
    TextView tv_2st_r;
    @Bind(R.id.publish_good_location_tv)
    TextView tv_3st_r;
    @Bind(R.id.publish_good_publish_btn)
    Button btn_publish_good_publish;
    @Bind(R.id.publish_1st_tv)
    TextView tv_1st_l;
    @Bind(R.id.publish_2st_tv)
    TextView tv_2st_l;
    @Bind(R.id.publish_3st_tv)
    TextView tv_3st_l;

    private Bitmap mPhotoBitmap;
    private static final String TAG = "PublishActivity";
    public static final String EXTRA_BITMAP_STRING = "extra_bitmap_string";
    public static final String EXTRA_BITMAP = "extra_bitmap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
    }


    @Override
    protected void initView() {
        byte[] bitmapByte = getIntent().getByteArrayExtra(HomeFragment.EXTRA_BITMAP_BYTE);
        mPhotoBitmap = BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length);
        if (mPhotoBitmap != null) {
            img_publish_good.setImageBitmap(mPhotoBitmap);
            Toast.makeText(this, "正在识别图片信息", Toast.LENGTH_LONG).show();
            recognizeImage(this, mPhotoBitmap);
        } else {
            Logger.d("mPhotoBitmap is null");
        }
    }

    private void recognizeImage(RecognizeUtil.RecognizeListener listener, Bitmap bitmap) {
        listener.onRecognize(bitmap);
    }

    @Override
    public void onRecognize(final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
                final String curTime = format.format(new Date(System.currentTimeMillis()));

                String bitmapStr = RecognizeUtil.Bitmap2Str(bitmap, 100);
                String jsonBody = new Gson().toJson(new BitmapBodyJson(bitmapStr));
                String jsonResult = "";
                try {
                    jsonResult = RecognizeUtil.sendPost(jsonBody);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final String itemName = RecognizeUtil.parseItemJson(jsonResult);
                updataItemUI(itemName, "中北大学主楼", curTime);

                //识别校园卡信息
                if (itemName.equals("校园卡")) {
                    String charJsonBody = " {\"image\":\" " + bitmapStr + "\", \"configure\":\"{\\\"min_size\\\" : 16,\\\"output_prob\\\" : true }\"}";
                    jsonResult = RecognizeUtil.sendCharPost(charJsonBody);
                    Map<String, String> cardData = RecognizeUtil.parseCharJson(jsonResult);
                    if (cardData != null) {
                        updataSchoolCardUI(cardData.get("name"), cardData.get("number"), cardData.get("college"), curTime, "中北大学主楼");
                    }
                }
            }
        }).start();
    }

    private void updataItemUI(final String name, final String location, final String time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPublishLocationLayout.setVisibility(View.GONE);
                mPublishTimeLayout.setVisibility(View.GONE);
                tv_1st_r.setText(name);
                tv_3st_r.setText(location);
                tv_2st_r.setText(time);
            }
        });
    }

    private void updataSchoolCardUI(final String name, final String number, final String college, final String time, final String location) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_1st_r.setText(name);
                tv_1st_l.setText("姓名");

                tv_2st_r.setText(college);
                tv_2st_l.setText("院系");

                tv_3st_r.setText(number);
                tv_3st_l.setText("学号");

                mPublishLocationLayout.setVisibility(View.VISIBLE);
                mPublishTimeLayout.setVisibility(View.VISIBLE);
                tv_time.setText(time);
                tv_location.setText(location);
            }
        });
    }


}
