package cn.bmob.imdemo.bean;

import android.graphics.Bitmap;

/**
 * Created by Rui on 2018/2/25.
 */

public class Things {
    /*
    列表单元类
     */
    private String mName;
    private String mKind;
    private String mTime;
    private String mLocation;
    private Bitmap mPicture;

    public Things(String mName, String mKind, String mTime, String mLocation, Bitmap mPicture) {
        this.mName = mName;
        this.mKind = mKind;
        this.mTime = mTime;
        this.mLocation = mLocation;
        this.mPicture = mPicture;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmKind() {
        return mKind;
    }

    public void setmKind(String mKind) {
        this.mKind = mKind;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public Bitmap getmPicture() {
        return mPicture;
    }

    public void setmPicture(Bitmap mPicture) {
        this.mPicture = mPicture;
    }


}
