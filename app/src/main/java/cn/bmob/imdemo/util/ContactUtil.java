package cn.bmob.imdemo.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.ui.UserInfoActivity;

/**
 * Created by a6100890 on 2018/3/7.
 */

public class ContactUtil {
    public static void contactUser(User user, Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("u", user);
        intent.putExtra(context.getPackageName(), bundle);
        context.startActivity(intent);
    }
}
