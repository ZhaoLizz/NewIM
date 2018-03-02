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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseActivity;
import cn.bmob.imdemo.bean.BitmapBodyJson;
import cn.bmob.imdemo.bean.IdCardData;
import cn.bmob.imdemo.bean.PhotoData;
import cn.bmob.imdemo.bean.SchoolCardData;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.ui.fragment.HomeFragment;
import cn.bmob.imdemo.util.RecognizeUtil;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


public class PublishActivity extends BaseActivity implements RecognizeUtil.RecognizeListener {
    @Bind(R.id.publish_4st_r_tv)
    TextView tv_time;
    @Bind(R.id.publish_time_layout)
    LinearLayout mPublishTimeLayout;
    @Bind(R.id.publish_sex_nation_layout)
    LinearLayout mPublishSexNationLayout;
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
    @Bind(R.id.publish_sex_tv)
    TextView mPublishSexTv;
    @Bind(R.id.publish_nation_tv)
    TextView mPublishNationTv;


    private Bitmap mPhotoBitmap;
    private File mPhotoFile;
    private static final String TAG = "PublishActivity";
    public static final String EXTRA_BITMAP_STRING = "extra_bitmap_string";
    public static final String EXTRA_BITMAP = "extra_bitmap";
    private static int mPhotoCategory = -1;
    private static final int CATEGORY_ITEM = 1;
    private static final int CATEGORY_SCHOOL = 2;
    private static final int CATEGORY_ID = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        byte[] bitmapByte = getIntent().getByteArrayExtra(HomeFragment.EXTRA_BITMAP_BYTE);
        mPhotoBitmap = BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length);
        try {
            mPhotoFile = new File(getExternalCacheDir(), "photo_file.jpg");
            new BufferedOutputStream(new FileOutputStream(mPhotoFile)).write(bitmapByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
    }


    @Override
    protected void initView() {
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
                Date curDate = new Date(System.currentTimeMillis());
                String curTime = format.format(curDate);
                String location = "中北大学主楼";

                String bitmapStr = RecognizeUtil.bitmap2Str(bitmap, 100);
                String jsonBody = new Gson().toJson(new BitmapBodyJson(bitmapStr));
                String jsonResult = "";
                try {
                    jsonResult = RecognizeUtil.sendPost(jsonBody);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final String itemName = RecognizeUtil.parseItemJson(jsonResult);
                updataItemUI(itemName, location, curTime);

                //识别校园卡信息
                if (itemName.equals("校园卡")) {
                    String charJsonBody = " {\"image\":\" " + bitmapStr + "\", \"configure\":\"{\\\"min_size\\\" : 16,\\\"output_prob\\\" : true }\"}";
                    jsonResult = RecognizeUtil.readTextImg(charJsonBody);
                    Map<String, String> cardData = RecognizeUtil.parseSchoolJson(jsonResult);
                    if (cardData != null) {
                        updataSchoolCardUI(cardData.get("name"), cardData.get("number"), cardData.get("college"), curTime, "中北大学主楼");
                    }

                    mPhotoCategory = CATEGORY_SCHOOL;
                    SchoolCardData schoolCardData = SchoolCardData.getInstance();
                    schoolCardData.reset();
                    schoolCardData.setName(cardData.get("name"));
                    schoolCardData.setUser(BmobUser.getCurrentUser(User.class));
                    schoolCardData.setNumber(cardData.get("number"));
                    schoolCardData.setCollege(cardData.get("college"));
                    schoolCardData.setTime(curDate);
                    schoolCardData.setPhoto(new BmobFile(mPhotoFile));
                    schoolCardData.setLocation(location);
                } else if (itemName.equals("身份证")) {
                    mPhotoCategory = CATEGORY_ID;
                    jsonResult = RecognizeUtil.readIdCardImg(bitmapStr);    //返回的json
                    IdCardData idCardData = RecognizeUtil.parseIdCardJson(jsonResult);  //识别的数据
                    updataIdCardUI(idCardData.getName(), idCardData.getSex(), idCardData.getNation(), idCardData.getAddress(), idCardData.getNumber(), location, curTime);
                    idCardData.setUser(BmobUser.getCurrentUser(User.class));
                    idCardData.setTime(curDate);
                    idCardData.setLocation(location);
                    idCardData.setPhoto(new BmobFile(mPhotoFile));
                } else {
                    //通识物品
                    mPhotoCategory = CATEGORY_ITEM;
                    PhotoData photoData = PhotoData.getInstance();
                    photoData.reset();
                    photoData.setPhoto(new BmobFile(mPhotoFile));
                    photoData.setItemName(itemName);
                    photoData.setLocation(location);
                    photoData.setTime(curDate);
                    photoData.setUser(BmobUser.getCurrentUser(User.class));
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

    private void updataIdCardUI(final String name, final String sex, final String nation, final String address, final String num, final String location, final String time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_1st_r.setText(name);
                tv_1st_l.setText("姓名");

                mPublishSexNationLayout.setVisibility(View.VISIBLE);
                mPublishSexTv.setText(sex);
                mPublishNationTv.setText(nation);

                tv_2st_r.setText(num);
                tv_2st_l.setText("证件号码");

                tv_3st_r.setText(address);
                tv_3st_l.setText("住址");

                mPublishLocationLayout.setVisibility(View.VISIBLE);
                mPublishTimeLayout.setVisibility(View.VISIBLE);
                tv_time.setText(time);
                tv_location.setText(location);
            }
        });
    }

    @OnClick(R.id.publish_good_publish_btn)
    public void onPublishClick() {
        Toast.makeText(this, "正在上传至服务器...", Toast.LENGTH_SHORT).show();
        switch (mPhotoCategory) {
            case CATEGORY_ITEM:
                PhotoData.getInstance().getPhoto().uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            PhotoData.getInstance().save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(PublishActivity.this, "招领启事发布成功!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(PublishActivity.this, "上传失败!请检查网络状态", Toast.LENGTH_SHORT).show();
                                        Logger.e(e.getMessage());
                                    }
                                }
                            });
                        } else {
                            Logger.e(e.getMessage());
                        }

                    }
                });
                break;

            case CATEGORY_SCHOOL:
                SchoolCardData.getInstance().getPhoto().uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            SchoolCardData.getInstance().save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(PublishActivity.this, "招领启事发布成功!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(PublishActivity.this, "上传失败!请检查网络状态", Toast.LENGTH_SHORT).show();
                                        Logger.e(e.getMessage());
                                    }
                                }
                            });
                        } else {
                            Logger.e(e.getMessage());
                        }
                    }
                });
                break;
            case CATEGORY_ID:
                IdCardData.getInstance().getPhoto().uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            IdCardData.getInstance().save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(PublishActivity.this, "招领启事发布成功!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(PublishActivity.this, "上传失败!请检查网络状态", Toast.LENGTH_SHORT).show();
                                        Logger.e(e.getMessage());
                                    }
                                }
                            });
                        } else {
                            Logger.e(e.getMessage());
                        }
                    }
                });
                break;
        }
    }


}
