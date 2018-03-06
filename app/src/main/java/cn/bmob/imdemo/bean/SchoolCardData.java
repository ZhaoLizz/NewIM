package cn.bmob.imdemo.bean;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by a6100890 on 2018/3/1.
 */

public class SchoolCardData extends BmobObject implements Serializable{
    private BmobFile photo;
    private String name;
    private String number;
    private String college;
    private Date time;
    private String location;
    private User user;

    private SchoolCardData() {
    }

    private static SchoolCardData instance = new SchoolCardData();

    public static SchoolCardData getInstance() {
        return instance;
    }

    public static void reset() {
        instance.setPhoto(null);
        instance.setName(null);
        instance.setNumber(null);
        instance.setCollege(null);
        instance.setTime(null);
        instance.setUser(null);
        instance.setLocation(null);
    }

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
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
}
