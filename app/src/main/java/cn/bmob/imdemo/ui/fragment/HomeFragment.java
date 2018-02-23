package cn.bmob.imdemo.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.util.PermissionUtil;

/**
 * Created by a6100890 on 2018/2/21.
 */

public class HomeFragment extends ParentWithNaviFragment {
    private Uri photoUri;
    private static final int REQUEST_CAMERA = 1;

    @Bind(R.id.btn_home_camera_publish)
    TextView btn_home_camera_publish;
    @Bind(R.id.btn_home_search)
    TextView btn_home_search;


    @Override
    protected String title() {
        return "首页";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        PermissionUtil.checkPerssion(getContext(), Manifest.permission.CAMERA, REQUEST_CAMERA);


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == getActivity().RESULT_OK) {
                    Logger.wtf(photoUri.getPath() );
                }
                break;
        }
    }

    @Override
    public void onStart() {
//        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
//        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @OnClick(R.id.btn_home_camera_publish)
    public void onCameraClick() {
        File photo = new File(getContext().getExternalCacheDir(), "photo.jpg");
        try {
            if (photo.exists()) {
                photo.delete();
            } else {
                photo.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            photoUri = FileProvider.getUriForFile(getContext(), "cn.bmob.imdemo.fileprovider", photo);
        } else {
            photoUri = Uri.fromFile(photo);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

}
