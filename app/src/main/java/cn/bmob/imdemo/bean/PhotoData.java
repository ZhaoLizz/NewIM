package cn.bmob.imdemo.bean;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by a6100890 on 2018/3/1.
 */

public class PhotoData extends BmobObject implements Serializable{
    private BmobFile photo;
    private String itemName;
    private Date time;
    private String location;
    private User user;

    private static PhotoData instance = new PhotoData();

    private PhotoData() {
    }

    public static PhotoData getInstance() {
        return instance;
    }

    public static void reset() {
        instance.setPhoto(null);
        instance.setItemName(null);
        instance.setTime(null);
        instance.setLocation(null);
        instance.setUser(null);

    }

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static void setInstance(PhotoData instance) {
        PhotoData.instance = instance;
    }
}
