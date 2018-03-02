package cn.bmob.imdemo.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by a6100890 on 2018/3/2.
 */

public class IdCardData extends BmobObject {
    private BmobFile mPhoto;
    private String name;
    private String sex;
    private String nation;
    private String address;
    private String number;
    private User mUser;
    private String location;
    private Date time;

    private IdCardData() {
    }

    public static IdCardData getINSTANCE() {
        return INSTANCE;
    }

    private static final IdCardData INSTANCE = new IdCardData();

    public static IdCardData getInstance() {
        return INSTANCE;
    }

    public static void reset() {
        INSTANCE.name = null;
        INSTANCE.sex = null;
        INSTANCE.nation = null;
        INSTANCE.address = null;
        INSTANCE.number = null;
        INSTANCE.mUser = null;
        INSTANCE.location = null;
        INSTANCE.time = null;
        INSTANCE.mPhoto = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BmobFile getPhoto() {
        return mPhoto;
    }

    public void setPhoto(BmobFile photo) {
        mPhoto = photo;
    }
}
