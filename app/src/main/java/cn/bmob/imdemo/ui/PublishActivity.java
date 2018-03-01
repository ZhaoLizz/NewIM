package cn.bmob.imdemo.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import butterknife.Bind;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseActivity;
import cn.bmob.imdemo.bean.BitmapBodyJson;
import cn.bmob.imdemo.ui.fragment.HomeFragment;
import cn.bmob.imdemo.util.RecognizeUtil;


public class PublishActivity extends BaseActivity implements RecognizeUtil.RecognizeListener {
    private Bitmap mPhotoBitmap;
    private static final String TAG = "PublishActivity";
    public static final String EXTRA_BITMAP_STRING = "extra_bitmap_string";
    public static final String EXTRA_BITMAP = "extra_bitmap";


    @Bind(R.id.publish_good_img)
    ImageView img_publish_good;
    @Bind(R.id.publish_good_name_tv)
    TextView tv_publish_good_name;
    @Bind(R.id.publish_good_time_tv)
    TextView tv_publish_good_time;
    @Bind(R.id.publish_good_location_tv)
    TextView tv_publish_good_location;
    @Bind(R.id.publish_good_publish_btn)
    Button btn_publish_good_publish;
    @Bind(R.id.publish_1st_tv)
    TextView tv_1st;
    @Bind(R.id.publish_2st_tv)
    TextView tv_2st;
    @Bind(R.id.publish_3st_tv)
    TextView tv_3st;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initView();

    }

    @Override
    protected void initView() {
        mPhotoBitmap = getIntent().getParcelableExtra(HomeFragment.EXTRA_BITMAP);
        if (mPhotoBitmap != null) {
            img_publish_good.setImageBitmap(mPhotoBitmap);
            recognizeImage(this, mPhotoBitmap);
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
                        updataSchoolCardUI(cardData.get("name"), cardData.get("number"), cardData.get("college"));
                    }
                }
            }
        }).start();
    }

    private void updataItemUI(final String name, final String location, final String time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_publish_good_name.setText(name);
                tv_publish_good_location.setText(location);
                tv_publish_good_time.setText(time);
            }
        });
    }

    private void updataSchoolCardUI(final String name, final String number, final String college) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_publish_good_name.setText(name);
                tv_publish_good_location.setText(number);
                tv_publish_good_time.setText(college);
                tv_1st.setText("姓名");
                tv_2st.setText("院系");
                tv_3st.setText("学号");
            }
        });
    }


}
