package cn.bmob.imdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviFragment;

/**
 * Created by a6100890 on 2018/2/23.
 */

public class ListFragment extends ParentWithNaviFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);

        return rootView;

    }

    @Override
    protected String title() {
        return "失物列表";
    }
}
