package cn.bmob.imdemo.event;

import android.graphics.Bitmap;

/**
 * Created by a6100890 on 2018/3/1.
 */

public class TransferEvent {
    private Bitmap mBitmap;

    public TransferEvent(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}
